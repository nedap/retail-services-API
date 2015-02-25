package com.nedap.retail.messages;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.Annotations;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.nedap.retail.messages.article.Article;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.core.header.InBoundHeaders;
import com.sun.jersey.spi.MessageBodyWorkers;

public class ClientTest {

    private static final String URL = "http://api.url.com";

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
            assertEquals("GET", clientRequest.getMethod());
            assertEquals(URI.create(url + "?gtins%5B%5D=03327009483366&fields%5B%5D=code&fields%5B%5D=name"),
                    clientRequest.getURI());

            // RESPONSE TEST
            assertEquals(1, articles.size());
            final Article article = articles.get(0);
            assertArticle(article);
        }
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
            assertEquals("GET", clientRequest.getMethod());
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
            assertEquals("GET", clientRequest.getMethod());
            assertEquals(URI.create(url + "?skip=0&count=5"),
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
