package com.nedap.retail.messages;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.Articles;
import com.nedap.retail.messages.organization.Location;
import com.nedap.retail.messages.organization.Organizations;
import com.nedap.retail.messages.stock.Stock;
import com.nedap.retail.messages.subscription.Subscription;
import com.nedap.retail.messages.system.SystemListPayload;
import com.nedap.retail.messages.system.SystemStatusPayload;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * Nedap Retail Services Client.
 */
public class Client {

    private static final String ORGANIZATION_ID = "organization_id";
    private static final String USER_ID = "user_id";
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private final String url;
    private final com.sun.jersey.api.client.Client httpClient;

    public Client(final String url, final String clientId, final String secret) {
        this.url = url;
        this.httpClient = initHttpClient();
        final AccessTokenResolver accessTokenResolver = new AccessTokenResolver(url, clientId, secret, httpClient);
        this.httpClient.addFilter(new AuthorizationClientFilter(accessTokenResolver));
    }

    public Client(final String url, final IAccessTokenResolver accessTokenResolver) {
        this.url = url;
        this.httpClient = initHttpClient();
        this.httpClient.addFilter(new AuthorizationClientFilter(accessTokenResolver));
    }

    private static com.sun.jersey.api.client.Client initHttpClient() {
        // http://stackoverflow.com/questions/9627170/cannot-unmarshal-a-json-array-of-objects-using-jersey-client
        // Jackson's MessageBodyReader implementation appears to be more well-behaved than the Jersey JSON one.
        final ClientConfig cfg = new DefaultClientConfig();
        cfg.getClasses().add(JacksonJsonProvider.class);

        // Creating an instance of a Client is an expensive operation, so try to avoid creating an unnecessary
        // number of client instances. A good approach is to reuse an existing instance, when possible.
        return com.sun.jersey.api.client.Client.create(cfg);
    }

    public void destroy() {
        httpClient.destroy();
    }

    /**
     * System API: list
     */
    public List<SystemListPayload> getSystemList() {

        final WebResource resource = resource("/system/1.0/list");
        return get(resource, new GenericType<List<SystemListPayload>>() {
        });
    }

    /**
     * System API: status
     */
    public List<SystemStatusPayload> getSystemStatus() {

        final WebResource resource = resource("/system/1.0/status");
        return get(resource, new GenericType<List<SystemStatusPayload>>() {
        });
    }

    /**
     * ERP API: capture stock
     *
     * @param stock Stock entry to capture
     * @return ID of processed stock
     */
    public String captureErpStock(final Stock stock) {

        final WebResource resource = resource("/erp/v2/stock.capture");
        final Map response = post(resource, Map.class, stock);
        return (String) response.get("id");
    }

    public void captureArticles(final List<Article> articles) {

        final WebResource resource = resource("/article/v2/create_or_replace");
        post(resource, new Articles(articles));
    }

    /**
     * Retrieves organizations.
     *
     * @return organizations.
     */
    public Organizations getOrganizations() {

        final WebResource resource = resource("/organization/v1/retrieve");
        return get(resource, Organizations.class);
    }

    /**
     * Retrieves list of sites.
     *
     * @return List of sites.
     */
    public List<Location> getSites() {

        final WebResource resource = resource("/organization/v1/sites");
        return getLocations(resource);
    }

    /**
     * Retrieves list of sites.
     *
     * @param storeCode (Optional) Store code (branch id) to search for. Can be null.
     * @return List of sites.
     */
    public List<Location> getSites(final String storeCode) {

        final WebResource resource = resource("/organization/v1/sites").queryParam("store_code", storeCode);
        return getLocations(resource);
    }

    /**
     * Retrieves list of sites for given Organization ID and User ID.
     *
     * Note that:
     * - The Organization ID must matched one of the Organization ID for which the access token is authorized.
     * If not the request will be rejected.
     * - When access token has User ID associated, the server will use the access token User ID
     * (and thus ignore the supplied User ID parameter).
     *
     * @param organizationId Organization ID.
     * @param userId User ID.
     * @return List of sites.
     */
    public List<Location> getSites(final long organizationId, final String userId) {

        WebResource resource = resource("/organization/v1/sites").
                queryParam(ORGANIZATION_ID, Long.toString(organizationId));
        if (userId != null) {
            resource = resource.queryParam(USER_ID, userId);
        }

        return getLocations(resource);
    }

    /**
     * Retrieves one location object for the given Organization ID, User ID, and locationId.
     * 
     * If location object is of type "SITE", it will also include the "children" having all (direct) sub-locations for
     * that site.
     * 
     * @param organizationId
     * @param userId
     * @param locationId
     * @return Location
     */
    public Location getLocation(final long organizationId, final String userId, final String locationId)
            throws UniformInterfaceException {
        if (locationId == null) {
            throw new IllegalArgumentException("location_id is required");
        }

        WebResource resource = resource("/organization/v1/location");

        resource = resource.queryParam(ORGANIZATION_ID, Long.toString(organizationId))
                .queryParam("id", locationId);
        if (userId != null) {
            resource = resource.queryParam(USER_ID, userId);
        }

        return get(resource, Location.class);
    }

    /**
     * To update a system to a new firmware version, you need to know which
     * updates are available for the system. This could depend on the current
     * firmware version, or on the installed hardware.
     *
     * @param systemId Identifies the system.
     * @return List of fimware versions available for upgrade.
     */
    public List<String> getFirmwareList(final String systemId) {

        final WebResource resource = resource("/system/1.0/firmware_versions").queryParam("system_id", systemId);
        return get(resource, new GenericType<List<String>>() {
        });
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
    public void triggerFirmwareUpgrade(final String systemId, final String firmwareVersion) {

        final WebResource resource = resource("/system/1.0/update").
                queryParam("system_id", systemId).
                queryParam("firmware_version", firmwareVersion);
        post(resource);
    }

    /**
     * Push API: subscribe.
     */
    public void subscribe(final String topic, final String callback, final String secret,
            final int lease_seconds) {

        final WebResource resource = resource("/subscription/1.0/subscribe").
                queryParam("hub.topic", topic).
                queryParam("hub.callback", callback).
                queryParam("hub.secret", secret).
                queryParam("hub.lease_seconds", "" + lease_seconds);

        post(resource);
    }

    /**
     * Push API: unsubscribe
     */
    public void unsubscribe(final String topic) {

        final WebResource resource = resource("/subscription/1.0/unsubscribe").queryParam("hub.topic", topic);
        post(resource);
    }

    /**
     * Push API: list
     */
    public List<Subscription> getSubscriptionList() {

        final WebResource resource = resource("/subscription/1.0/list");
        return get(resource, new GenericType<List<Subscription>>() {
        });
    }

    protected WebResource resource(final String uri) {

        logger.debug("resource {}", uri);
        return httpClient.resource(url + uri);
    }

    protected static List<Location> getLocations(final WebResource resource) {
        final List<Location> locations = get(resource, new GenericType<List<Location>>() {
        });

        Collections.sort(locations, new Comparator<Location>() {
            @Override
            public int compare(final Location l1, final Location l2) {
                if (l1 == null || l1.getName() == null) {
                    return -1;
                } else {
                    return l1.getName().compareToIgnoreCase(l2.getName());
                }
            }
        });
        return locations;
    }

    protected static <T> T get(final WebResource resource, final Class<T> responseClass)
            throws UniformInterfaceException {
        return resource.accept(APPLICATION_JSON_TYPE).get(responseClass);
    }

    private static <T> T get(final WebResource resource, final GenericType<T> responseClass)
            throws UniformInterfaceException {
        return resource.accept(APPLICATION_JSON_TYPE).get(responseClass);
    }

    protected static void post(final WebResource resource) throws UniformInterfaceException {
        resource.accept(APPLICATION_JSON_TYPE).post();
    }

    protected static <T> T post(final WebResource resource, final Class<T> responseClass)
            throws UniformInterfaceException {
        return resource.accept(APPLICATION_JSON_TYPE).post(responseClass);
    }

    protected static void post(final WebResource resource, final Object requestEntity)
            throws UniformInterfaceException {
        resource.accept(APPLICATION_JSON_TYPE).type(APPLICATION_JSON_TYPE).post(requestEntity);
    }

    protected static <T> T post(final WebResource resource, final Class<T> responseClass, final Object requestEntity)
            throws UniformInterfaceException {
        return resource.accept(APPLICATION_JSON_TYPE).type(APPLICATION_JSON_TYPE).post(responseClass, requestEntity);
    }

    public static String getErrorMessage(final ClientResponse response) {
        final Map payload = response.getEntity(Map.class);
        final String reason = (String) payload.get("reason");
        return reason;
    }
}
