package com.nedap.retail.messages;

import com.nedap.retail.messages.subscription.Subscription;
import com.nedap.retail.messages.subscription.SubscriptionListResponse;
import com.nedap.retail.messages.system.SystemListPayload;
import com.nedap.retail.messages.system.SystemListResponse;
import com.nedap.retail.messages.system.SystemStatusPayload;
import com.nedap.retail.messages.system.SystemStatusResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.List;
import java.util.Map;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Nedap Retail Services Client.
 */
public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private final String url;
    private final String clientId;
    private final String secret;
    private final com.sun.jersey.api.client.Client httpClient;
    private String accessToken;

    public Client(final String url, final String clientId, final String secret) {
        this.url = url;
        this.clientId = clientId;
        this.secret = secret;

        // http://stackoverflow.com/questions/9627170/cannot-unmarshal-a-json-array-of-objects-using-jersey-client
        // Jackson's MessageBodyReader implementation appears to be more well-behaved than the Jersey JSON one.
        ClientConfig cfg = new DefaultClientConfig();
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
     * To update a system to a new firmware version, you need to know which updates are available for the system.
     * This could depend on the current firmware version, or on the installed hardware.
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
     * This request triggers the firmware update mechanism on a system.
     * This starts a download and installation of the firmware. During download and installation, the system remains
     * fully functional. After installation, the system is restarted. Depending on the speed of the internet connection,
     * the number of changes in the firmware and the number of devices within the system, the update can take up
     * to one hour. Installing a firmware version that is older than the currently installed version is not possible.
     * Systems that do not run an officially released firmware version can not be upgraded this way.
     * Official firmware versions have a version number in the form of year.weeknumber (for example, 13.21).
     *
     * You can check if the firmware update succeeded by requesting the status of the system regularly, for example
     * every 5 minutes after sending the request.
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

    private WebResource resource(String uri) {

        return httpClient.resource(url + uri);
    }

    private <T extends AbstractApiResponse> T get(final WebResource resource, final Class<T> responseClass)
            throws InvalidMessage {

        return method("GET", resource, responseClass);
    }

    private <T extends AbstractApiResponse> T post(final WebResource resource, final Class<T> responseClass)
            throws InvalidMessage {

        return method("POST", resource, responseClass);
    }

    private <T extends AbstractApiResponse> T method(final String method, final WebResource resource,
            final Class<T> responseClass) throws InvalidMessage {

        T response = null;
        for (int trycount = 0; trycount < 3; trycount++) {
            if (accessToken == null) {
                accessToken = authorize();
            }

            // Add access token.
            logger.debug("method: {}, resource: {}", method, resource);
            final Builder builder = resource.header("Authorization", accessToken).accept(APPLICATION_JSON);
            response = builder.method(method, responseClass);

            // Check if access_token is expired. If so get new access_token and repeat request.
            if (response.getStatus() == ApiResponse.Unauthorized) {
                logger.debug("access token is expired or invalid. try again");
                accessToken = null;
            } else {
                break;
            }
        }

        final int status = response.getStatus();
        logger.debug("response status: {}", status);
        if (status == ApiResponse.OK) {
            return response;
        } else {
            throw new InvalidMessage(status, getErrorMessage(response));
        }
    }

    /**
     * Login with Nedap OAuth 2.0 Authorization Server.
     *
     * @return OAuth 2.0 access token
     * @throws UniformInterfaceException - if the status of the HTTP response is greater than or equal to 300 and
     * c is not the type ClientResponse.
     * @throws ClientHandlerException - if the client handler fails to process the request or response.
     */
    private String authorize() {

        logger.debug("getting OAuth 2.0 access token: {}", clientId);
        final Builder builder = resource("/login/oauth/token").
                queryParam("grant_type", "client_credentials").
                queryParam("client_id", clientId).
                queryParam("client_secret", secret).
                accept(APPLICATION_JSON);

        final OAuthResponse result = builder.post(OAuthResponse.class);
        logger.debug("successfull. access token: {}", result.getAccess_token());
        return result.getAccess_token();
    }

    private String getErrorMessage(final AbstractApiResponse response) {

        String reason = response.getError();
        if (reason == null) {
            final Object payload = response.getPayload();
            if (payload instanceof Map) {
                reason = (String) ((Map) payload).get("reason");
            }
        }
        return reason;
    }
}