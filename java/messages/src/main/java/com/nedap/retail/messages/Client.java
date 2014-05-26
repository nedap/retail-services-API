package com.nedap.retail.messages;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.Articles;
import com.nedap.retail.messages.stock.Stock;
import com.nedap.retail.messages.subscription.Subscription;
import com.nedap.retail.messages.subscription.SubscriptionListResponse;
import com.nedap.retail.messages.system.SystemListPayload;
import com.nedap.retail.messages.system.SystemListResponse;
import com.nedap.retail.messages.system.SystemStatusPayload;
import com.nedap.retail.messages.system.SystemStatusResponse;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Nedap Retail Services Client.
 */
public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private final String url;
    private com.sun.jersey.api.client.Client httpClient;
    private final IAccessTokenResolver accessTokenResolver;
    private String accessToken;

    public Client(final String url, final String clientId, final String secret) {
        this.url = url;
        initHttpClient();
        this.accessTokenResolver = new AccessTokenResolver(url, clientId, secret, httpClient);
    }

    public Client(final String url, final String clientId, final String secret,
            final com.sun.jersey.api.client.Client httpClient) {
        this.url = url;
        this.httpClient = httpClient;
        this.accessTokenResolver = new AccessTokenResolver(clientId, secret, url, httpClient);
    }

    public Client(final String url, final IAccessTokenResolver accessTokenResolver) {
        this.url = url;
        initHttpClient();
        this.accessTokenResolver = accessTokenResolver;
    }

    private void initHttpClient() {
        // http://stackoverflow.com/questions/9627170/cannot-unmarshal-a-json-array-of-objects-using-jersey-client
        // Jackson's MessageBodyReader implementation appears to be more well-behaved than the Jersey JSON one.
        final ClientConfig cfg = new DefaultClientConfig();
        cfg.getClasses().add(JacksonJsonProvider.class);

        // Creating an instance of a Client is an expensive operation, so try to avoid creating an unnecessary
        // number of client instances. A good approach is to reuse an existing instance, when possible.
        httpClient = com.sun.jersey.api.client.Client.create(cfg);
    }

    public void destroy() {
        httpClient.destroy();
    }

    /**
     * System API: list
     */
    public List<SystemListPayload> getSystemList() throws InvalidMessage {

        final WebResource resource = resource("/system/1.0/list");
        return get(resource, SystemListResponse.class).getPayload();
    }

    /**
     * System API: status
     */
    public List<SystemStatusPayload> getSystemStatus() throws InvalidMessage {

        final WebResource resource = resource("/system/1.0/status");
        return get(resource, SystemStatusResponse.class).getPayload();
    }

    /**
     * ERP API: capture stock
     *
     * @param stock Stock entry to capture
     * @return ID of processed stock
     * @throws com.nedap.retail.messages.InvalidMessage
     */
    public String captureErpStock(final Stock stock) throws InvalidMessage {
        logger.debug("ERP API capturing {}", stock);
        final WebResource resource = resource("/erp/v2/stock.capture");

        final Map response = post(resource, Map.class, stock);
        logger.debug("response {}", response);
        return (String) response.get("id");
    }

    public Map captureArticles(final List<Article> articles) throws InvalidMessage {
        logger.debug("Article API: capturing {} articles", articles.size());
        final WebResource resource = resource("/article/v2/create_or_replace");

        final Map response = post(resource, Map.class, new Articles(articles));
        logger.debug("response {}", response);
        return response;
    }

    /**
     * Retrieves list of sites.
     * @param storeCode (Optional) Store code (branch id) to search for. Can be null.
     * @return List of sites.
     * @throws InvalidMessage
     */
    public List getSites(final String storeCode) throws InvalidMessage {

        WebResource resource = resource("/organization/v1/sites");
        if (storeCode != null) {
            resource = resource.queryParam("store_code", storeCode);
        }

        final List response = get(resource, List.class);
        logger.debug("response {}", response);
        return response;
    }

    public String getLocations(final Long organizationId) throws InvalidMessage {
        final WebResource resource;
        try {
            resource = resource("/organization/v1/sites").queryParam("organization_id", organizationId.toString());
        } catch (final Exception ex) {
            throw new InvalidMessage("Cannot GET location identifier for organization_id '" + organizationId + "'");
        }

        final String response = get(resource, String.class);
        logger.debug("response {}", response);
        return response;
    }

    /**
     * To update a system to a new firmware version, you need to know which
     * updates are available for the system. This could depend on the current
     * firmware version, or on the installed hardware.
     *
     * @param systemId Identifies the system.
     * @return List of fimware versions available for upgrade.
     */
    public List<String> getFirmwareList(final String systemId) throws InvalidMessage {

        final WebResource resource = resource("/system/1.0/firmware_versions").queryParam("system_id", systemId);
        final ApiResponse response = get(resource, ApiResponse.class);
        return (List<String>) response.getPayload();
    }

    /**
     * This request triggers the firmware update mechanism on a system. This
     * starts a download and installation of the firmware. During download and
     * installation, the system remains fully functional. After installation,
     * the system is restarted. Depending on the speed of the internet
     * connection, the number of changes in the firmware and the number of
     * devices within the system, the update can take up to one hour. Installing
     * a firmware version that is older than the currently installed version is
     * not possible. Systems that do not run an officially released firmware
     * version can not be upgraded this way. Official firmware versions have a
     * version number in the form of year.weeknumber (for example, 13.21).
     *
     * You can check if the firmware update succeeded by requesting the status
     * of the system regularly, for example every 5 minutes after sending the
     * request.
     *
     * @param systemId Identifies the system to be upgraded.
     * @param firmwareVersion Requested firmware version to upgrade to.
     */
    public void triggerFirmwareUpgrade(final String systemId, final String firmwareVersion) throws InvalidMessage {

        final WebResource resource = resource("/system/1.0/update").
                queryParam("system_id", systemId).
                queryParam("firmware_version", firmwareVersion);
        post(resource, ApiResponse.class);
    }

    /**
     * Push API: subscribe.
     */
    public void subscribe(final String topic, final String callback, final String secret,
            final int lease_seconds) throws InvalidMessage {

        final WebResource resource = resource("/subscription/1.0/subscribe").
                queryParam("hub.topic", topic).
                queryParam("hub.callback", callback).
                queryParam("hub.secret", secret).
                queryParam("hub.lease_seconds", "" + lease_seconds);

        post(resource, ApiResponse.class);
    }

    /**
     * Push API: unsubscribe
     */
    public void unsubscribe(final String topic) throws InvalidMessage {

        final WebResource resource = resource("/subscription/1.0/unsubscribe").queryParam("hub.topic", topic);
        post(resource, ApiResponse.class);
    }

    /**
     * Push API: list
     */
    public List<Subscription> getSubscriptionList() throws InvalidMessage {

        final WebResource resource = resource("/subscription/1.0/list");
        return get(resource, SubscriptionListResponse.class).getPayload();
    }

    public WebResource resource(final String uri) {

        return httpClient.resource(url + uri);
    }

    protected <T> T get(final WebResource resource, final Class<T> responseClass)
            throws InvalidMessage {

        return method("GET", resource, responseClass, null);
    }

    protected <T> T post(final WebResource resource, final Class<T> responseClass)
            throws InvalidMessage {

        return method("POST", resource, responseClass, null);
    }

    protected <T> T post(final WebResource resource, final Class<T> responseClass,
            final Object requestEntity) throws InvalidMessage {

        return method("POST", resource, responseClass, requestEntity);
    }

    private <T> T method(final String method, final WebResource resource,
            final Class<T> responseClass, final Object requestEntity) throws InvalidMessage {

        ClientResponse response = null;
        int status = 500;
        for (int trycount = 0; trycount < 3; trycount++) {
            try {
                if (accessToken == null) {
                    accessToken = accessTokenResolver.resolve();
                }

                // Add access token.
                logger.debug("method: {}, resource: {}", method, resource);
                if (requestEntity == null) {
                    final Builder builder = resource.header("Authorization", accessToken).accept(APPLICATION_JSON);
                    return builder.method(method, responseClass);
                } else {
                    final Builder builder = resource.header("Authorization", accessToken).accept(APPLICATION_JSON).
                            type(APPLICATION_JSON);
                    return builder.method(method, responseClass, requestEntity);
                }
            } catch (final UniformInterfaceException ex) {
                response = ex.getResponse();
                status = response.getStatus();
                logger.debug("response status: {}", status);
                // Check if access_token is expired. If so get new access_token and repeat request.
                if (status == ApiResponse.Unauthorized) {
                    logger.debug("access token is expired or invalid. try again");
                    accessToken = null;
                }
            }
        }
        throw new InvalidMessage(status, getErrorMessage(response));
    }

    private String getErrorMessage(final ClientResponse response) {
        final Map payload = response.getEntity(Map.class);
        final String reason = (String) payload.get("reason");
        return reason;
    }
}
