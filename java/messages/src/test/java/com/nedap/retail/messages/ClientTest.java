package com.nedap.retail.messages;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.Annotations;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.Articles;
import com.nedap.retail.messages.article.Barcode;
import com.nedap.retail.messages.article.Price;
import com.nedap.retail.messages.article.Size;
import com.nedap.retail.messages.epc.v2.approved_difference_list.ApprovedDifferenceListSummary;
import com.nedap.retail.messages.epc.v2.approved_difference_list.request.ApprovedDifferenceListCaptureRequest;
import com.nedap.retail.messages.epc.v2.approved_difference_list.response.ApprovedDifferenceListExportResponse;
import com.nedap.retail.messages.epc.v2.approved_difference_list.response.ApprovedDifferenceListResponse;
import com.nedap.retail.messages.epc.v2.difference_list.DifferenceListResponse;
import com.nedap.retail.messages.epc.v2.stock.StockResponse;
import com.nedap.retail.messages.epcis.v1_1.EpcisEvent;
import com.nedap.retail.messages.epcis.v1_1.EpcisEventContainer;
import com.nedap.retail.messages.epcis.v1_1.ObjectEvent;
import com.nedap.retail.messages.epcis.v1_1.cbv.Action;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;
import com.nedap.retail.messages.stock.GtinQuantity;
import com.nedap.retail.messages.stock.Stock;
import com.nedap.retail.messages.stock.StockSummary;
import com.nedap.retail.messages.stock.StockSummaryListRequest;
import com.nedap.retail.messages.system.SystemListPayload;
import com.nedap.retail.messages.system.SystemStatusPayload;
import com.nedap.retail.messages.workflow.WorkflowEvent;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.header.InBoundHeaders;
import com.sun.jersey.spi.MessageBodyWorkers;

public class ClientTest {

    private static final String URL = "http://api.url.com";
    private static final String ID = "123";
    private static final String LOCATION = "http://nedapretail.com/loc/test";
    private static final String GTIN_1 = "02011200000019";
    private static final String GTIN_2 = "02011200000064";
    private static final String GTIN_3 = "02011200000163";
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final DateTime TIME = DateTime.now();

    private static com.sun.jersey.api.client.Client mockHttpClient;
    private static Client client;
    private static JacksonJsonProvider jacksonJsonProvider;

    @BeforeClass
    public static void init() {
        mockHttpClient = mock(com.sun.jersey.api.client.Client.class);

        initTestClient();
        initJacksonJsonProvider();
    }

    /**
     * Fake http client added here so we can mock configuration and test request and response
     */
    private static void initTestClient() {
        client = new Client(URL, "clientid", "secret") {
            @Override
            protected com.sun.jersey.api.client.Client initHttpClient() {
                // get is creating properties and if this is not called we get NPE
                when(mockHttpClient.getProperties()).thenCallRealMethod();
                mockHttpClient.getProperties();

                return mockHttpClient;
            }
        };
    }

    private static void initJacksonJsonProvider() {
        final ObjectMapper mapper = new ObjectMapper().configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
                false);
        // We use this reader on client so use same for deserialization
        jacksonJsonProvider = new JacksonJsonProvider(Annotations.JACKSON);
        jacksonJsonProvider.setMapper(mapper);
    }

    @Before
    public void setUp() {
        reset(mockHttpClient);
    }

    @Test
    public void test_article_quantity() throws IOException {
        final String url = URL + "/article/v2/quantity";

        try (final InputStream is = ClientTest.class.getResourceAsStream("articles_quantity.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final long numberOfArticles = client.articleQuantity();

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals(8124, numberOfArticles);
        }
    }

    @Test
    public void test_retrieve_articles_by_gtin() throws IOException {
        final String url = URL + "/article/v2/retrieve";

        try (final InputStream is = ClientTest.class.getResourceAsStream("articles.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final List<String> gtins = Arrays.asList("03327009483366");
            final List<String> fields = Arrays.asList("code", "name");
            final List<Article> articles = client.articleDetailsByGtins(gtins, fields);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?gtins%5B%5D=03327009483366&fields%5B%5D=code&fields%5B%5D=name"),
                    clientRequest.getURI());

            // RESPONSE TEST
            assertEquals(1, articles.size());
            final Article article = articles.get(0);
            assertArticle(article);
        }
    }

    private void assertArticle(final Article article) {
        assertEquals("03327009483366", article.getGtin());
        assertEquals("74478-94565", article.getCode());
        assertEquals("Nedap", article.getBrand());
        assertEquals("Summer 2014", article.getSeason());
    }

    @Test
    public void test_retrieve_articles_by_barcode() throws IOException {
        final String url = URL + "/article/v2/retrieve";

        try (final InputStream is = ClientTest.class.getResourceAsStream("articles.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final List<String> barcodes = Arrays.asList("3327009483366");
            final List<String> fields = Arrays.asList("code", "name");
            final List<Article> articles = client.articleDetailsByBarcodes(barcodes, fields);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?barcodes%5B%5D=3327009483366&fields%5B%5D=code&fields%5B%5D=name"),
                    clientRequest.getURI());

            // RESPONSE TEST
            assertEquals(1, articles.size());
            final Article article = articles.get(0);
            assertArticle(article);
        }
    }

    @Test
    public void test_retrieve_articles_by_skip_count() throws IOException {
        final String url = URL + "/article/v2/retrieve";

        try (final InputStream is = ClientTest.class.getResourceAsStream("articles.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final List<Article> articles = client.retrieveArticles(null, 0, 5, null);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?skip=0&count=5"), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals(1, articles.size());
            final Article article = articles.get(0);
            assertArticle(article);
        }
    }

    @Test
    public void test_capture_article() throws IOException {
        final String url = URL + "/article/v2/create_or_replace";

        try (final InputStream is = new ByteArrayInputStream(new byte[] {})) {
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
            final ClientResponse clientResponse = new ClientResponse(204, null, is, null);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final List<Article> articlesList = new ArrayList<>();
            articlesList.add(createExampleArticle());
            final Articles articles = new Articles(articlesList);
            client.captureArticles(articlesList);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            final Articles requestPackage = (Articles) clientRequest.getEntity();
            assertEquals(articles.getArticles().get(0).getGtin(), requestPackage.getArticles().get(0).getGtin());
            assertEquals(POST, clientRequest.getMethod());
            assertEquals(URI.create(url), clientRequest.getURI());
        }
    }

    private Article createExampleArticle() {
        final Article article = new Article();

        article.setGtin(GTIN_1);
        article.setName("Test article");
        article.setColor("blue");

        // at least one linear barcode is required
        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode("EAN13", "8701231234562"));
        article.setBarcodes(barcodes);

        final List<Size> sizes = new ArrayList<>();
        sizes.add(new Size("42", "EU"));
        sizes.add(new Size("40", "US"));
        article.setSizes(sizes);

        // the rest is optional
        article.setCode("some retailer specific article code");
        article.setBrand("example brand");
        article.setSeason("Summer 2014");
        article.setOption("Test article blue");
        article.setStyle("example style");
        article.setSupplier("example supplier");
        article.setCategory("Sports");
        article.setMarkdown(true);
        article.setPrices(setArticlePrices());

        return article;
    }

    private List<Price> setArticlePrices() {
        final List<Price> prices = new ArrayList<>();
        prices.add(new Price("EUR", "NL", 19.95));
        prices.add(new Price("EUR", "BE", 18.95));
        prices.add(new Price("EUR", "DE", 19.95));
        prices.add(new Price("EUR", "FR", 20.95));
        prices.add(new Price("NOK", "NO", 160.0));
        prices.add(new Price("USD", "US", 19.95));
        return prices;
    }

    @Test
    public void test_capture_rfid_stock() throws IOException {
        final String url = URL + "/epc/v2/stock.capture";

        try (final InputStream is = ClientTest.class.getResourceAsStream("capture_response_id.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final Stock stock = makeStock();
            final String rfidStockId = client.captureRfidStock(stock);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            final Stock requestPackage = (Stock) clientRequest.getEntity();
            assertEquals(stock.location, requestPackage.location);
            assertEquals(POST, clientRequest.getMethod());
            assertEquals(URI.create(url), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals(ID, rfidStockId);
        }
    }

    @Test
    public void test_capture_approved_difference_list() throws IOException {
        final String url = URL + "/epc/v2/approved_difference_list.capture";

        try (final InputStream is = ClientTest.class.getResourceAsStream("capture_response_id.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final ApprovedDifferenceListCaptureRequest request = makeApprovedDifferenceListCaptureRequest();
            final String approvedDifferenceListId = client.captureApprovedDifferenceList(request);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            final ApprovedDifferenceListCaptureRequest requestPackage = (ApprovedDifferenceListCaptureRequest) clientRequest
                    .getEntity();
            assertEquals(request.location, requestPackage.location);
            assertEquals(POST, clientRequest.getMethod());
            assertEquals(URI.create(url), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals(ID, approvedDifferenceListId);
        }
    }

    private ApprovedDifferenceListCaptureRequest makeApprovedDifferenceListCaptureRequest() {
        final ApprovedDifferenceListCaptureRequest request = new ApprovedDifferenceListCaptureRequest();
        request.rfidTime = TIME;
        request.erpStockId = ID;
        request.location = LOCATION;
        request.approvedGtins = Arrays.asList(GTIN_1, GTIN_2, GTIN_3);
        return request;
    }

    @Test
    public void test_get_approved_difference_list() throws IOException {
        final String url = URL + "/epc/v2/approved_difference_list.retrieve";

        try (final InputStream is = ClientTest.class.getResourceAsStream("approved_difference_list.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final String request = ID;
            final ApprovedDifferenceListResponse response = client.getApprovedDifferenceList(request);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?id=" + ID), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals("2015-04-09T12:00:00.000Z", response.approvedOn.toString());
            assertEquals(LOCATION, response.location);
            assertEquals(2, response.absoluteDifference);
            assertEquals("EXPORTED", response.exportStatus.toString());
        }
    }

    @Test
    public void test_approved_difference_list_export() throws IOException {
        final String url = URL + "/epc/v2/approved_difference_list.export";

        try (final InputStream is = ClientTest.class.getResourceAsStream("approved_difference_list_export.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final String request = ID;
            final ApprovedDifferenceListExportResponse response = client.approvedDifferenceListExport(request);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?id=" + ID), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals("2015-04-09T12:00:00.000Z", response.approvedOn.toString());
            assertEquals(LOCATION, response.location);
            assertEquals(2, response.absoluteDifference);
            assertEquals(4, response.approvedQuantity.get(0).intValue());
        }
    }

    @Test
    public void test_get_approved_difference_list_summaries() throws IOException {
        final String url = URL + "/epc/v2/approved_difference_list.list";

        try (final InputStream is = ClientTest.class.getResourceAsStream("approved_difference_list_summaries.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final String locationId = LOCATION;
            final List<ApprovedDifferenceListSummary> response = client.getApprovedDifferenceListSummaries(locationId,
                    null, null);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?location=" + LOCATION), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals("2015-04-08T12:00:00.000Z", response.get(1).approvedOn.toString());
            assertEquals(LOCATION, response.get(0).location);
            assertEquals(2, response.get(0).absoluteDifference);
            assertEquals(3, response.get(1).absoluteDifference);
        }
    }

    @Test
    public void test_get_approved_difference_list_status() throws IOException {
        final String url = URL + "/epc/v2/approved_difference_list.status";

        try (final InputStream is = ClientTest.class.getResourceAsStream("approved_difference_list_summary.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final String locationId = LOCATION;
            final ApprovedDifferenceListSummary response = client.getApprovedDifferenceListStatus(locationId,
                    TIME.toString());

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?location=" + LOCATION + "&rfid_time=" + TIME.toString()),
                    clientRequest.getURI());

            // RESPONSE TEST
            assertEquals("2015-04-09T12:00:00.000Z", response.approvedOn.toString());
            assertEquals(LOCATION, response.location);
            assertEquals(2, response.absoluteDifference);
            assertEquals(ID, response.erpStockId);
        }
    }

    @Test
    public void test_get_difference_list() throws IOException {
        final String url = URL + "/epc/v2/difference_list";

        try (final InputStream is = ClientTest.class.getResourceAsStream("difference_list.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final DifferenceListResponse differenceList = client.differenceList(ID, null, null, null);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?erp_stock_id=123"), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals("03327009483366", differenceList.getGtins().get(0));
            assertEquals("2014-07-25T17:46:00.000Z", differenceList.getRfidStockTime().toString());
            assertEquals(2, differenceList.getRfidGtinQuantity().intValue());
            assertEquals(2, differenceList.getArticles().size());
        }
    }

    @Test
    public void test_get_stock_gtin() throws IOException {
        final String url = URL + "/epc/v3/stock.gtin14";

        try (final InputStream is = ClientTest.class.getResourceAsStream("stock_gtin14.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final StockResponse stockResponse = client.stockGtin(LOCATION, null, null, null, null);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?location=" + LOCATION), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals("03327009483366", stockResponse.gtins.get(0));
            assertEquals("Novi Sad:A", stockResponse.locations.get(0));
            assertEquals("2011-03-02T11:13:00.000Z", stockResponse.time.toString());
            assertEquals("03327009483366", stockResponse.articles.get(0).getGtin());
        }
    }

    @Test
    public void test_capture_epcis_event() throws IOException {
        final String url = URL + "/epcis/v2/capture";

        try (final InputStream is = new ByteArrayInputStream(new byte[] {})) {
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
            final ClientResponse clientResponse = new ClientResponse(204, null, is, null);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final EpcisEventContainer epcisEventsList = new EpcisEventContainer();
            epcisEventsList.events = createEvents();
            client.captureEpcisEvents(epcisEventsList);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            final EpcisEventContainer requestPackage = (EpcisEventContainer) clientRequest.getEntity();
            assertEquals(epcisEventsList.events.get(0).id, requestPackage.events.get(0).id);
            assertEquals(POST, clientRequest.getMethod());
            assertEquals(URI.create(url), clientRequest.getURI());
        }
    }

    private List<EpcisEvent> createEvents() {
        final List<EpcisEvent> events = new ArrayList<>();
        events.add(createEpcisEvent1());
        return events;
    }

    private EpcisEvent createEpcisEvent1() {
        final String id = "12f03260-c56f-11e3-9c1a-0800200c9a66";
        final DateTime eventTime = DateTime.parse("2014-02-01T12:00:00.000+01:00");
        final DateTime recordTime = DateTime.now();
        final String eventTimeZoneOffset = "+01:00";
        final Action action = Action.OBSERVE;
        final String bizLocation = "urn:epc:id:sgln:012345.67890.001";
        final String readPoint = "urn:epc:id:sgln:012345.67890.001";
        final Disposition disposition = Disposition.SELLABLE_ACCESSIBLE;

        final List<String> epcList = new ArrayList<>();
        epcList.add("urn:epc:id:sgtin:061414.12346.0001");
        epcList.add("urn:epc:id:sgtin:061414.12346.0002");
        epcList.add("urn:epc:id:sgtin:061414.12346.0003");
        epcList.add("urn:epc:id:sgtin:061414.12346.0004");

        final EpcisEvent event1 = new ObjectEvent(id, eventTime, recordTime, eventTimeZoneOffset, action, bizLocation,
                readPoint, disposition, epcList, null);

        return event1;
    }

    @Test
    public void test_capture_erp_stock() throws IOException {
        final String url = URL + "/erp/v1/stock.capture";

        try (final InputStream is = ClientTest.class.getResourceAsStream("capture_response_id.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final Stock stock = makeStock();
            final String erpStockId = client.captureErpStock(stock);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            final Stock requestPackage = (Stock) clientRequest.getEntity();
            assertEquals(stock.location, requestPackage.location);
            assertEquals(POST, clientRequest.getMethod());
            assertEquals(URI.create(url), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals(ID, erpStockId);
        }
    }

    private Stock makeStock() {
        final String location = LOCATION;
        final List<GtinQuantity> exampleStock = new ArrayList<>();
        exampleStock.add(new GtinQuantity(GTIN_1, 23));
        exampleStock.add(new GtinQuantity(GTIN_2, 3));
        exampleStock.add(new GtinQuantity(GTIN_3, -3));
        final String myReference = "testing";
        final DateTime timeOfStock = new DateTime();

        return new Stock(location, timeOfStock, myReference, exampleStock);
    }

    @Test
    public void test_get_erp_stock_status() throws IOException {
        final String url = URL + "/erp/v1/stock.status";

        try (final InputStream is = ClientTest.class.getResourceAsStream("erp_stock_summary.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final String erpStockId = ID;
            final StockSummary stockSummary = client.getErpStockStatus(erpStockId);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?id=123"), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals("2011-03-02T11:13:00.000Z", stockSummary.eventTime.toString());
            assertEquals("loc:A", stockSummary.location);
            assertEquals("ACCEPTED", stockSummary.status.toString());
            assertEquals(true, stockSummary.inUse);
        }
    }

    @Test
    public void test_retrieve_erp_stock() throws IOException {
        final String url = URL + "/erp/v1/stock.retrieve";

        try (final InputStream is = ClientTest.class.getResourceAsStream("erp_stock_retrieve.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final String erpStockId = ID;
            final Stock stock = client.retrieveErpStock(erpStockId);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?id=123"), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals("2015-02-25T07:50:44.295Z", stock.eventTime.toString());
            assertEquals(LOCATION, stock.location);
            assertEquals(false, stock.inUse);
            assertEquals("12345678901231", stock.quantityList.get(0).gtin);
        }
    }

    @Test
    public void test_get_erp_stock_list() throws IOException {
        final String url = URL + "/erp/v1/stock.list";

        try (final InputStream is = ClientTest.class.getResourceAsStream("erp_stock_list.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final StockSummaryListRequest request = new StockSummaryListRequest();
            request.location = LOCATION;
            final List<StockSummary> erpList = client.getErpStockList(request);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url + "?location=" + LOCATION), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals("2015-02-26T07:36:20.576Z", erpList.get(0).eventTime.toString());
            assertEquals(LOCATION, erpList.get(0).location);
            assertEquals(false, erpList.get(0).inUse);
            assertEquals("ACCEPTED", erpList.get(0).status.toString());
        }
    }

    @Test
    public void test_get_system_list() throws IOException {
        final String url = URL + "/system/v1/list";

        try (final InputStream is = ClientTest.class.getResourceAsStream("system_list.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final List<SystemListPayload> systemList = client.getSystemList();

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals("uuid-1", systemList.get(0).getSystemId());
            assertEquals("EAS main entrance", systemList.get(0).getName());
            assertEquals("Customer > Country > Store", systemList.get(0).getLocation());
        }
    }

    @Test
    public void test_get_system_status() throws IOException {
        final String url = URL + "/system/v1/status";

        try (final InputStream is = ClientTest.class.getResourceAsStream("system_status.json")) {
            final ClientResponse clientResponse = createClientResponse(200, is);
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final List<SystemStatusPayload> systemStatus = client.getSystemStatus();

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            assertEquals(GET, clientRequest.getMethod());
            assertEquals(URI.create(url), clientRequest.getURI());

            // RESPONSE TEST
            assertEquals("uuid-1", systemStatus.get(0).getSystemId());
            assertEquals("12.37", systemStatus.get(0).getFirmwareVersion());
            assertEquals("CRITICAL", systemStatus.get(0).getStatus().toString());
            assertEquals("OK", systemStatus.get(0).getDetailedStatus().get(0).getStatus().toString());
        }
    }

    @Test
    public void test_capture_workflow() throws IOException {
        final String url = URL + "/workflow/v2/capture";

        try (final InputStream is = new ByteArrayInputStream(new byte[] {})) {
            final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
            final ClientResponse clientResponse = new ClientResponse(204, null, is, null);

            when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
            when(mockHttpClient.resource(url)).thenCallRealMethod();
            when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();

            final WorkflowEvent workflowEvent = makeWorkflowEvent();
            client.captureWorkflow(workflowEvent);

            // REQUEST TEST
            final ClientRequest clientRequest = clientRequestCaptor.getValue();
            final WorkflowEvent requestPackage = (WorkflowEvent) clientRequest.getEntity();
            assertEquals(workflowEvent.getLocation(), requestPackage.getLocation());
            assertEquals(POST, clientRequest.getMethod());
            assertEquals(URI.create(url), clientRequest.getURI());
        }
    }

    private WorkflowEvent makeWorkflowEvent() {
        final WorkflowEvent workflow = new WorkflowEvent();
        workflow.setType("cycle_count_started");
        workflow.setEventTime(DateTime.now());
        workflow.setLocation("Store:Sales floor");
        workflow.setEpcCount((long) 10);
        workflow.setMessageIds(Arrays.asList("abc-123", "def-456", "ghi-789"));
        return workflow;
    }

    @SuppressWarnings("unchecked")
    private ClientResponse createClientResponse(final int status, final InputStream is) {
        final InBoundHeaders inHeaders = new InBoundHeaders();
        inHeaders.add("Content-Type", "application/json");
        final MessageBodyWorkers messageBodyWorkers = mock(MessageBodyWorkers.class);

        when(
                messageBodyWorkers.getMessageBodyReader(any(Class.class), any(Type.class), any(Annotation[].class),
                        eq(MediaType.APPLICATION_JSON_TYPE))).thenReturn(jacksonJsonProvider);

        return new ClientResponse(status, inHeaders, is, messageBodyWorkers);
    }
}
