package com.nedap.retail.messages;


/**
 * Left to be fixed, we need either to use embedded server and test against it or mock again client and return response
 * which we expect
 * 
 */
public class ClientTest {

    // private static final String URL = "http://api.url.com";
    //
    // private static com.sun.jersey.api.client.Client mockHttpClient;
    // private static Client client;
    // private static JacksonJsonProvider jacksonJsonProvider;
    //
    // @BeforeClass
    // public static void init() {
    // mockHttpClient = mock(com.sun.jersey.api.client.Client.class);
    //
    // initTestClient();
    // initJacksonJsonProvider();
    // }
    //
    // /**
    // * Fake http client added here so we can mock configuration and test request and response
    // */
    // private static void initTestClient() {
    // client = new Client(URL, "clientid", "secret") {
    // @Override
    // protected com.sun.jersey.api.client.Client initHttpClient() {
    // // get is creating properties and if this is not called we get NPE
    // when(mockHttpClient.getProperties()).thenCallRealMethod();
    // mockHttpClient.getProperties();
    //
    // return mockHttpClient;
    // }
    // };
    // }
    //
    // private static void initJacksonJsonProvider() {
    // final ObjectMapper mapper = new ObjectMapper().configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,
    // false);
    // // We use this reader on client so use same for deserialization
    // jacksonJsonProvider = new JacksonJsonProvider(Annotations.JACKSON);
    // jacksonJsonProvider.setMapper(mapper);
    // }
    //
    // @Before
    // public void setUp() {
    // reset(mockHttpClient);
    // }
    //
    // @Test
    // public void test_article_quantity() throws IOException {
    // final String url = URL + "/article/v2/quantity";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("articles_quantity.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final long numberOfArticles = client.articleQuantity();
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // assertEquals("GET", clientRequest.getMethod());
    // assertEquals(URI.create(url), clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals(8124, numberOfArticles);
    // }
    // }
    //
    // @Test
    // public void test_retrieve_articles_by_gtin() throws IOException {
    // final String url = URL + "/article/v2/retrieve";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("articles.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final List<String> gtins = Arrays.asList("03327009483366");
    // final List<String> fields = Arrays.asList("code", "name");
    // final List<Article> articles = client.articleDetailsByGtins(gtins, fields);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // assertEquals("GET", clientRequest.getMethod());
    // assertEquals(URI.create(url + "?gtins%5B%5D=03327009483366&fields%5B%5D=code&fields%5B%5D=name"),
    // clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals(1, articles.size());
    // final Article article = articles.get(0);
    // assertArticle(article);
    // }
    // }
    //
    // private void assertArticle(final Article article) {
    // assertEquals("03327009483366", article.getGtin());
    // assertEquals("74478-94565", article.getCode());
    // assertEquals("Nedap", article.getBrand());
    // assertEquals("Summer 2014", article.getSeason());
    // }
    //
    // @Test
    // public void test_retrieve_articles_by_barcode() throws IOException {
    // final String url = URL + "/article/v2/retrieve";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("articles.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final List<String> barcodes = Arrays.asList("3327009483366");
    // final List<String> fields = Arrays.asList("code", "name");
    // final List<Article> articles = client.articleDetailsByBarcodes(barcodes, fields);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // assertEquals("GET", clientRequest.getMethod());
    // assertEquals(URI.create(url + "?barcodes%5B%5D=3327009483366&fields%5B%5D=code&fields%5B%5D=name"),
    // clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals(1, articles.size());
    // final Article article = articles.get(0);
    // assertArticle(article);
    // }
    // }
    //
    // @Test
    // public void test_retrieve_articles_by_skip_count() throws IOException {
    // final String url = URL + "/article/v2/retrieve";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("articles.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final List<Article> articles = client.retrieveArticles(null, 0, 5, null);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // assertEquals("GET", clientRequest.getMethod());
    // assertEquals(URI.create(url + "?skip=0&count=5"), clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals(1, articles.size());
    // final Article article = articles.get(0);
    // assertArticle(article);
    // }
    // }
    //
    // @Test
    // public void test_capture_article() throws IOException {
    // final String url = URL + "/article/v2/create_or_replace";
    //
    // try (final InputStream is = new ByteArrayInputStream(new byte[] {})) {
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    // final ClientResponse clientResponse = new ClientResponse(204, null, is, null);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final List<Article> articlesList = new ArrayList<>();
    // articlesList.add(createExampleArticle());
    // final Articles articles = new Articles(articlesList);
    // client.captureArticles(articlesList);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // final Articles requestPackage = (Articles) clientRequest.getEntity();
    // assertEquals(articles.getArticles().get(0).getGtin(), requestPackage.getArticles().get(0).getGtin());
    // assertEquals("POST", clientRequest.getMethod());
    // assertEquals(URI.create(url), clientRequest.getURI());
    // }
    // }
    //
    // private static Article createExampleArticle() {
    // final Article article = new Article();
    //
    // // GTIN is the identifier of a product. Most barcodes can be translated to GTIN.
    // article.setGtin("08701231234562");
    //
    // // at least one linear barcode is required
    // final List<Barcode> barcodes = new ArrayList<>();
    // barcodes.add(new Barcode("EAN13", "8701231234562"));
    // article.setBarcodes(barcodes);
    //
    // // name is required
    // article.setName("Test article");
    //
    // // color is required
    // article.setColor("blue");
    //
    // // size is required
    // final List<Size> sizes = new ArrayList<>();
    // sizes.add(new Size("42", "EU"));
    // sizes.add(new Size("40", "US"));
    // article.setSizes(sizes);
    //
    // // the rest is optional
    // article.setCode("some retailer specific article code");
    // article.setBrand("example brand");
    // article.setSeason("Summer 2014");
    // article.setOption("Test article blue");
    // article.setStyle("example style");
    // article.setSupplier("example supplier");
    // article.setCategory("Sports");
    // article.setMarkdown(true);
    // article.setPrices(setArticlePrices());
    //
    // return article;
    // }
    //
    // private static List<Price> setArticlePrices() {
    // final List<Price> prices = new ArrayList<>();
    // prices.add(new Price("EUR", "NL", 19.95));
    // prices.add(new Price("EUR", "BE", 18.95));
    // prices.add(new Price("EUR", "DE", 19.95));
    // prices.add(new Price("EUR", "FR", 20.95));
    // prices.add(new Price("NOK", "NO", 160.0));
    // prices.add(new Price("USD", "US", 19.95));
    // return prices;
    // }
    //
    // @Test
    // public void test_get_difference_list() throws IOException {
    // final String url = URL + "/epc/v2/difference_list";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("difference_list.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final DifferenceListResponse differenceList = client.differenceList("123", null, null, null);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // assertEquals("GET", clientRequest.getMethod());
    // assertEquals(URI.create(url + "?erp_stock_id=123"), clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals("03327009483366", differenceList.getGtins().get(0));
    // assertEquals("2014-07-25T17:46:00.000Z", differenceList.getRfidStockTime().toString());
    // assertEquals(2, differenceList.getRfidGtinQuantity().intValue());
    // assertEquals(2, differenceList.getArticles().size());
    // }
    // }
    //
    // @Test
    // public void test_get_stock_gtin() throws IOException {
    // final String url = URL + "/epc/v3/stock.gtin14";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("stock_gtin14.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final StockResponse stockResponse = client.stockGtin("http://retailer.com/loc/123", null, null, null, null);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // assertEquals("GET", clientRequest.getMethod());
    // assertEquals(URI.create(url + "?location=http://retailer.com/loc/123"), clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals("03327009483366", stockResponse.gtins.get(0));
    // assertEquals("Novi Sad:A", stockResponse.locations.get(0));
    // assertEquals("2011-03-02T11:13:00.000Z", stockResponse.time.toString());
    // assertEquals("03327009483366", stockResponse.articles.get(0).getGtin());
    // }
    // }
    //
    // @Test
    // public void test_capture_epcis_event() throws IOException {
    // final String url = URL + "/epcis/v2/capture";
    //
    // try (final InputStream is = new ByteArrayInputStream(new byte[] {})) {
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    // final ClientResponse clientResponse = new ClientResponse(204, null, is, null);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final EpcisEventContainer epcisEventsList = new EpcisEventContainer();
    // epcisEventsList.events = createEvents();
    // client.captureEpcisEvents(epcisEventsList);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // final EpcisEventContainer requestPackage = (EpcisEventContainer) clientRequest.getEntity();
    // assertEquals(epcisEventsList.events.get(0).id, requestPackage.events.get(0).id);
    // assertEquals("POST", clientRequest.getMethod());
    // assertEquals(URI.create(url), clientRequest.getURI());
    // }
    // }
    //
    // private static List<EpcisEvent> createEvents() {
    // final List<EpcisEvent> events = new ArrayList<>();
    // events.add(createEpcisEvent1());
    // return events;
    // }
    //
    // private static EpcisEvent createEpcisEvent1() {
    // final String id = "12f03260-c56f-11e3-9c1a-0800200c9a66";
    // final DateTime eventTime = DateTime.parse("2014-02-01T12:00:00.000+01:00");
    // final DateTime recordTime = DateTime.now();
    // final String eventTimeZoneOffset = "+01:00";
    // final Action action = Action.OBSERVE;
    // final String bizLocation = "urn:epc:id:sgln:012345.67890.001";
    // final String readPoint = "urn:epc:id:sgln:012345.67890.001";
    // final Disposition disposition = Disposition.SELLABLE_ACCESSIBLE;
    //
    // final List<String> epcList = new ArrayList<>();
    // epcList.add("urn:epc:id:sgtin:061414.12346.0001");
    // epcList.add("urn:epc:id:sgtin:061414.12346.0002");
    // epcList.add("urn:epc:id:sgtin:061414.12346.0003");
    // epcList.add("urn:epc:id:sgtin:061414.12346.0004");
    //
    // final EpcisEvent event1 = new ObjectEvent(id, eventTime, recordTime, eventTimeZoneOffset, action, bizLocation,
    // readPoint, disposition, epcList, null);
    //
    // return event1;
    // }
    //
    // @Test
    // public void test_capture_erp_stock() throws IOException {
    // final String url = URL + "/erp/v1/stock.capture";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("erp_stock.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final Stock stock = makeErpStock();
    // final String erpStockId = client.captureErpStock(stock);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // final Stock requestPackage = (Stock) clientRequest.getEntity();
    // assertEquals(stock.location, requestPackage.location);
    // assertEquals("POST", clientRequest.getMethod());
    // assertEquals(URI.create(url), clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals("123", erpStockId);
    // }
    // }
    //
    // private Stock makeErpStock() {
    // final String location = "http://nedapretail.com/loc/test";
    // final List<GtinQuantity> exampleStock = new ArrayList<>();
    // exampleStock.add(new GtinQuantity("12345678901231", 23));
    // exampleStock.add(new GtinQuantity("12345678901248", 3));
    // exampleStock.add(new GtinQuantity("12345678901255", -3));
    // exampleStock.add(new GtinQuantity("12345678901262", 17));
    // final String myReference = "testing";
    // final DateTime timeOfStock = new DateTime();
    //
    // return new Stock(location, timeOfStock, myReference, exampleStock);
    // }
    //
    // @Test
    // public void test_get_erp_stock_status() throws IOException {
    // final String url = URL + "/erp/v1/stock.status";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("erp_stock_summary.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final String erpStockId = "123";
    // final StockSummary stockSummary = client.getErpStockStatus(erpStockId);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // assertEquals("GET", clientRequest.getMethod());
    // assertEquals(URI.create(url + "?id=123"), clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals("2011-03-02T11:13:00.000Z", stockSummary.eventTime.toString());
    // assertEquals("loc:A", stockSummary.location);
    // assertEquals("ACCEPTED", stockSummary.status.toString());
    // assertEquals(true, stockSummary.inUse);
    // }
    // }
    //
    // @Test
    // public void test_retrieve_erp_stock() throws IOException {
    // final String url = URL + "/erp/v1/stock.retrieve";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("erp_stock_retrieve.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final String erpStockId = "123";
    // final Stock stock = client.retrieveErpStock(erpStockId);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // assertEquals("GET", clientRequest.getMethod());
    // assertEquals(URI.create(url + "?id=123"), clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals("2015-02-25T07:50:44.295Z", stock.eventTime.toString());
    // assertEquals("http://nedapretail.com/loc/test", stock.location);
    // assertEquals(false, stock.inUse);
    // assertEquals("12345678901231", stock.quantityList.get(0).gtin);
    // }
    // }
    //
    // @Test
    // public void test_get_erp_stock_list() throws IOException {
    // final String url = URL + "/erp/v1/stock.list";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("erp_stock_list.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final String location = "http://nedapretail.com/loc/test";
    // final List<StockSummary> erpList = client.getErpStockList(location);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // assertEquals("GET", clientRequest.getMethod());
    // assertEquals(URI.create(url + "?location=http://nedapretail.com/loc/test"), clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals("2015-02-26T07:36:20.576Z", erpList.get(0).eventTime.toString());
    // assertEquals("http://nedapretail.com/loc/test", erpList.get(0).location);
    // assertEquals(false, erpList.get(0).inUse);
    // assertEquals("ACCEPTED", erpList.get(0).status.toString());
    // }
    // }
    //
    // @Test
    // public void test_get_system_list() throws IOException {
    // final String url = URL + "/system/v1/list";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("system_list.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final List<SystemListPayload> systemList = client.getSystemList();
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // assertEquals("GET", clientRequest.getMethod());
    // assertEquals(URI.create(url), clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals("uuid-1", systemList.get(0).getSystemId());
    // assertEquals("EAS main entrance", systemList.get(0).getName());
    // assertEquals("Customer > Country > Store", systemList.get(0).getLocation());
    // }
    // }
    //
    // @Test
    // public void test_get_system_status() throws IOException {
    // final String url = URL + "/system/v1/status";
    //
    // try (final InputStream is = ClientTest.class.getResourceAsStream("system_status.json")) {
    // final ClientResponse clientResponse = createClientResponse(200, is);
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final List<SystemStatusPayload> systemStatus = client.getSystemStatus();
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // assertEquals("GET", clientRequest.getMethod());
    // assertEquals(URI.create(url), clientRequest.getURI());
    //
    // // RESPONSE TEST
    // assertEquals("uuid-1", systemStatus.get(0).getSystemId());
    // assertEquals("12.37", systemStatus.get(0).getFirmwareVersion());
    // assertEquals("CRITICAL", systemStatus.get(0).getStatus().toString());
    // assertEquals("OK", systemStatus.get(0).getDetailedStatus().get(0).getStatus().toString());
    // }
    // }
    //
    // @Test
    // public void test_capture_workflow() throws IOException {
    // final String url = URL + "/workflow/v2/capture";
    //
    // try (final InputStream is = new ByteArrayInputStream(new byte[] {})) {
    // final ArgumentCaptor<ClientRequest> clientRequestCaptor = ArgumentCaptor.forClass(ClientRequest.class);
    // final ClientResponse clientResponse = new ClientResponse(204, null, is, null);
    //
    // when(mockHttpClient.handle(clientRequestCaptor.capture())).thenReturn(clientResponse);
    // when(mockHttpClient.resource(url)).thenCallRealMethod();
    // when(mockHttpClient.resource(URI.create(url))).thenCallRealMethod();
    //
    // final WorkflowEvent workflowEvent = makeWorkflowEvent();
    // client.captureWorkflow(workflowEvent);
    //
    // // REQUEST TEST
    // final ClientRequest clientRequest = clientRequestCaptor.getValue();
    // final WorkflowEvent requestPackage = (WorkflowEvent) clientRequest.getEntity();
    // assertEquals(workflowEvent.getLocation(), requestPackage.getLocation());
    // assertEquals("POST", clientRequest.getMethod());
    // assertEquals(URI.create(url), clientRequest.getURI());
    // }
    // }
    //
    // private WorkflowEvent makeWorkflowEvent() {
    // final WorkflowEvent workflow = new WorkflowEvent();
    // workflow.setType("cycle_count_started");
    // workflow.setEventTime(DateTime.now());
    // workflow.setLocation("Store:Sales floor");
    // workflow.setEpcCount((long) 10);
    // workflow.setMessageIds(Arrays.asList("abc-123", "def-456", "ghi-789"));
    // return workflow;
    // }
    //
    // @SuppressWarnings("unchecked")
    // private ClientResponse createClientResponse(final int status, final InputStream is) {
    // final InBoundHeaders inHeaders = new InBoundHeaders();
    // inHeaders.add("Content-Type", "application/json");
    // final MessageBodyWorkers messageBodyWorkers = mock(MessageBodyWorkers.class);
    //
    // when(
    // messageBodyWorkers.getMessageBodyReader(any(Class.class), any(Type.class), any(Annotation[].class),
    // eq(MediaType.APPLICATION_JSON_TYPE))).thenReturn(jacksonJsonProvider);
    //
    // return new ClientResponse(status, inHeaders, is, messageBodyWorkers);
    // }
}
