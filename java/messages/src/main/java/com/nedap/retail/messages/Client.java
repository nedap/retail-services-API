package com.nedap.retail.messages;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.jaxrs.Annotations;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.Articles;
import com.nedap.retail.messages.epc.v2.approved_difference_list.response.ApprovedDifferenceListResponse;
import com.nedap.retail.messages.epc.v2.difference_list.DifferenceListResponse;
import com.nedap.retail.messages.organization.Location;
import com.nedap.retail.messages.organization.Organizations;
import com.nedap.retail.messages.stock.Stock;
import com.nedap.retail.messages.stock.StockSummary;
import com.nedap.retail.messages.subscription.Subscription;
import com.nedap.retail.messages.system.SystemListPayload;
import com.nedap.retail.messages.system.SystemStatusPayload;
import com.nedap.retail.messages.users.User;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
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
    private static ObjectMapper mapper;

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
        mapper = new ObjectMapper().configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        // Jackson's MessageBodyReader implementation appears to be more well-behaved than the Jersey JSON one.
        // And also this provider uses our own configured object-mapper.
        final JacksonJsonProvider provider = new JacksonJsonProvider(Annotations.JACKSON);
        provider.setMapper(mapper);

        final DefaultClientConfig config = new DefaultClientConfig();
        config.getSingletons().add(provider);

        // Creating an instance of a Client is an expensive operation, so try to avoid creating an unnecessary
        // number of client instances. A good approach is to reuse an existing instance, when possible.
        final com.sun.jersey.api.client.Client client = com.sun.jersey.api.client.Client.create(config);
        client.setFollowRedirects(false);
        client.setConnectTimeout(10000);
        client.setReadTimeout(60000);
        return client;
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

    /**
     * ERP API: retrieve stock
     *
     * @param id ID of stock
     * @return The requested stock
     */
    public Stock retrieveErpStock(final String id) {

        final WebResource resource = resource("/erp/v2/stock.retrieve")
                .queryParam("id", id);

        System.out.println(resource.accept(APPLICATION_JSON_TYPE).get(String.class));

        return get(resource, Stock.class);
    }

    /**
     * @param organizationId Id of organization for difference list
     * @param erpStockId Id of stock imported from ERP
     * @param rfidTime Time of RFID cycle count
     * @param onlyDifferences Boolean to switch between showing only differences or whole list
     * 
     * @return list of differences without article information for performance reasons
     */
    public DifferenceListResponse differenceList(final long organizationId, final String erpStockId, final DateTime rfidTime,
            final boolean onlyDifferences) {

        WebResource resource = resource("/epc/v2/difference_list").queryParam("erp_stock_id", erpStockId)
                .queryParam("include_articles", "false").queryParam(ORGANIZATION_ID, Long.toString(organizationId));

        if (rfidTime != null) {
            resource = resource.queryParam("time", rfidTime.toString());
        }
        // Only differences is by default true
        if (!onlyDifferences) {
            resource = resource.queryParam("only_differences", "false");
        }

        return get(resource, DifferenceListResponse.class);
    }

    /**
     * @param organizationId Id of organization for approved difference list
     * @param approvedDifferenceListId Id approved difference list
     * 
     * @return Approved difference list with provided id
     */
    public ApprovedDifferenceListResponse approvedDifferenceList(final long organizationId,
            final String approvedDifferenceListId) {

        if (approvedDifferenceListId == null) {
            throw new IllegalArgumentException("Approved difference list id is required");
        }

        final WebResource resource = resource("/epc/v2/approved_difference_list.retrieve").queryParam("id",
                approvedDifferenceListId).queryParam(ORGANIZATION_ID, Long.toString(organizationId));

        return get(resource, ApprovedDifferenceListResponse.class);
    }

    /**
     * ERP API: retrieve stock status and summary
     *
     * @param id ID of stock
     * @return Summary of the requested stock
     */
    public StockSummary getErpStockStatus(final String id) {

        final WebResource resource = resource("/erp/v2/stock.status")
                .queryParam("id", id);
        return get(resource, StockSummary.class);
    }

    /**
     * ERP API: get a list of available stocks
     *
     * @param location Location identifier
     * @return List of summaries per stock available for the specified location
     */
    public List<StockSummary> getErpStockList(final String location) {

        final WebResource resource = resource("/erp/v2/stock.list")
                .queryParam("location", location);
        return get(resource, new GenericType<List<StockSummary>>() {
        });
    }

    /**
     * @param organizationId Id of organization for rfid stock list
     * @param locationId Location identifier
     * @param fromRfidTime Lower boundary for rfid stock time
     * @param untilRfidTime Upper boundary for rfid stock time
     * @return List of summaries for rfid stock on requested location
     */
    public List<StockSummary> getRfidStockList(final long organizationId, final String locationId,
            final DateTime fromRfidTime, final DateTime untilRfidTime) {

        WebResource resource = resource("/epc/v2/stock.list").queryParam("location", locationId).queryParam(
                ORGANIZATION_ID, Long.toString(organizationId));

        if (fromRfidTime != null) {
            resource = resource.queryParam("from_event_time", fromRfidTime.toString());
        }
        if (untilRfidTime != null) {
            resource = resource.queryParam("until_event_time", untilRfidTime.toString());
        }

        return get(resource, new GenericType<List<StockSummary>>() {
        });
    }

    /**
     * Article API: update article information
     *
     * @param articles Articles to update or add
     */
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

    /**
     * Get user profile by user ID.
     * Endpoint GET /users/1.0/get
     *
     * @param userId The ID of the user to get the profile for. The special value "me" can be used to indicate the authenticated user.
     * @return User profile. Returns <tt>null</tt> when not found (status code 404).
     */
    public User getUser(final String userId) {
        try {
            final WebResource resource = resource("/users/1.0/get").queryParam("user_id", userId);
            return get(resource, User.class);
        } catch (final UniformInterfaceException ex) {
            // Thrown when the status of the HTTP response is greater than or equal to 300.
            final ClientResponse response = ex.getResponse();
            if (response.getStatus() == 404) {
                return null;
            } else {
                throw ex;
            }
        }
    }

    protected WebResource resource(final String uri) {

        logger.debug("resource {}", uri);
        return httpClient.resource(url + uri);
    }

    protected static List<Location> getLocations(final WebResource resource) {
        return get(resource, new GenericType<List<Location>>() {
        });
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
