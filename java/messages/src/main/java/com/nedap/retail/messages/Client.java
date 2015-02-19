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
import com.nedap.retail.messages.epc.v2.difference_list.DifferenceListResponse;
import com.nedap.retail.messages.epc.v2.stock.StockResponse;
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
     * To update a system to a new firmware version, you need to know which updates are available for the system. This
     * could depend on the current firmware version, or on the installed hardware.
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
     * This request triggers the firmware update mechanism on a system. This starts a download and installation of the
     * firmware. During download and installation, the system remains fully functional. After installation, the system
     * is restarted. Depending on the speed of the internet connection, the number of changes in the firmware and the
     * number of devices within the system, the update can take up to one hour. Installing a firmware version that is
     * older than the currently installed version is not possible. Systems that do not run an officially released
     * firmware version can not be upgraded this way. Official firmware versions have a version number in the form of
     * year.weeknumber (for example, 13.21).
     *
     * You can check if the firmware update succeeded by requesting the status of the system regularly, for example
     * every 5 minutes after sending the request.
     *
     * @param systemId Identifies the system to be upgraded.
     * @param firmwareVersion Requested firmware version to upgrade to.
     */
    public void triggerFirmwareUpgrade(final String systemId, final String firmwareVersion) {

        final WebResource resource = resource("/system/1.0/update").queryParam("system_id", systemId).queryParam(
                "firmware_version", firmwareVersion);
        post(resource);
    }

    /**
     * ERP API: capture stock
     *
     * @param stock Stock entry to capture
     * @return ID of processed stock
     */
    public String captureErpStock(final Stock stock) {

        final WebResource resource = resource("/erp/v1/stock.capture");
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

        final WebResource resource = resource("/erp/v1/stock.retrieve").queryParam("id", id);

        System.out.println(resource.accept(APPLICATION_JSON_TYPE).get(String.class));

        return get(resource, Stock.class);
    }

    /**
     * ERP API: retrieve stock status and summary
     *
     * @param id ID of stock
     * @return Summary of the requested stock
     */
    public StockSummary getErpStockStatus(final String id) {

        final WebResource resource = resource("/erp/v1/stock.status").queryParam("id", id);
        return get(resource, StockSummary.class);
    }

    /**
     * ERP API: get a list of available stocks
     *
     * @param location Location identifier
     * @return List of summaries per stock available for the specified location
     */
    public List<StockSummary> getErpStockList(final String location) {

        final WebResource resource = resource("/erp/v1/stock.list").queryParam("location", location);
        return get(resource, new GenericType<List<StockSummary>>() {
        });
    }

    /**
     * Requests the total number of articles registered in !D Cloud.
     *
     * @return Total number of articles.
     */
    public Long articleQuantity() {

        final WebResource resource = resource("/article/v2/quantity");
        final Map response = get(resource, Map.class);
        return (Long) response.get("quantity");
    }

    /**
     * Retrieve one or more articles based on GTIN value(s) or barcodes. Optionally specify returned Article properties.
     * When querying on barcode, the barcode type is ignored. All articles that have a barcode that match (case
     * insensitive for alphanumeric barcodes) one of the given barcodes is returned.
     *
     * @param gtins The GTIN(s) of which article information should be returned. Repeat key-value for retrieving
     *            multiple GTINs.Mutually exclusive with barcodes[].
     * @param barcodes The barcode(s) of which article information should be returned. Repeat key-value for retrieving
     *            multiple barcodes.Mutually exclusive with gtins[].
     * @param fields Optional. Which fields should be included in the response. Can be any of the Article fields. When
     *            omitted: all fields will be included in the response. Repeat key-value for retrieving multiple fields.
     * @return List of articles retrieved.
     */
    public Articles articleDetails(final List<String> gtins, final List<String> barcodes, final List<String> fields) {
        WebResource resource = resource("/article/v2/retrieve");

        for (final String gtin : gtins) {
            resource = resource.queryParam("gtins[]", gtin);
        }

        for (final String barcode : barcodes) {
            resource = resource.queryParam("barcodes[]", barcode);
        }

        if (fields != null) {
            for (final String field : fields) {
                resource = resource.queryParam("fields[]", field);
            }
        }

        return get(resource, Articles.class);
    }

    /**
     * Retrieve articles. Optionally specify returned Article properties.
     *
     * @param updatedAfter Articles updated on or after this time. When omitted: return all Article objects since 1
     *            january 1970.
     * @param skip Skip this number of articles. When omitted: skip none.
     * @param count Return this number of articles. When omitted: return 100 Article objects. The number of returned
     *            Resources is limited at 50.000.
     * @param fields Which fields should be included in the response. Can be one of the earlier defined fields. When
     *            omitted: return all fields in Article resource. Repeat key-value for retrieving multiple fields.
     * @return List of articles retrieved.
     */
    public Articles retrieveArticles(final DateTime updatedAfter, final int skip, final int count,
            final List<String> fields) {
        WebResource resource = resource("/article/v2/retrieve");

        if (updatedAfter != null) {
            resource = resource.queryParam("updated_after", updatedAfter.toString());
        }

        resource = resource.queryParam("skip", String.valueOf(skip));
        resource = resource.queryParam("count", String.valueOf(count));

        for (final String field : fields) {
            resource = resource.queryParam("fields[]", field);
        }

        return get(resource, Articles.class);
    }

    /**
     * Article API: update article information.
     *
     * @param articles Articles to update or add.
     */
    public void captureArticles(final List<Article> articles) {

        final WebResource resource = resource("/article/v2/create_or_replace");
        post(resource, new Articles(articles));
    }

    /**
     * Article entries are deleted for given organization. All articles will be physically deleted from database and
     * this process is irreversible.
     */
    public void articleDelete() {

        final WebResource resource = resource("/article/v2/delete");
        post(resource);
    }

    /**
     * Retrieve GTIN-based difference list for a single location. By default, only differences are returned and items
     * where there is no difference are omitted. When a difference list for multiple locations are required, this call
     * should be used repeatedly. How it works: Get ERP stock defined by erp_stock_id. Get RFID count at time.
     *
     * @param erpStockId ERP stock ID. A list of ERP stock ID's can be retrieved with the ERP API. Implicitly defines
     *            LocationIdentifier for the location for which the difference list is generated.
     * @param rfidTime The date and time you would like to have the RFID stock information from. When omitted: the
     *            report will contain the stock information at the current server time.
     * @param onlyDifferences Optional. When set to TRUE (default) this will return only differences. When set to FALSE,
     *            this will return all entries in ERP stock or RFID stock. When omitted: default value is TRUE,
     *            returning only differences.
     * @param includeArticles Optional. When set to TRUE this will return an array of articles. When omitted: default
     *            value is FALSE.
     * @return DifferenceList response object
     */
    public DifferenceListResponse differenceList(final String erpStockId, final DateTime rfidTime,
            final Boolean onlyDifferences, final Boolean includeArticles) {

        WebResource resource = resource("/epc/v2/difference_list").queryParam("erp_stock_id", erpStockId);

        if (rfidTime != null) {
            resource = resource.queryParam("time", rfidTime.toString());
        }

        if (onlyDifferences != null) {
            resource = resource.queryParam("only_differences", onlyDifferences.toString());
        }

        if (includeArticles != null) {
            resource = resource.queryParam("include_articles", includeArticles.toString());
        }

        return get(resource, DifferenceListResponse.class);
    }

    /**
     * Requests the current stock status at a certain location at the GTIN level. This can be used to evaluate the total
     * stock in a store along with stock summary information for that store, or calculate options for in-store
     * replenishment by querying the stock in any of sublocations. If site stock is requested stock summary for store is
     * included in response. On !D Cloud we apply logic that filters out EPCs based on their location as defined by the
     * EPC's bizLocation and the status at that location as defined by the EPC's disposition.
     *
     * @param location Contains the location that we are interested in. Based on GLN + extension number; see also EPCIS
     *            master data.
     * @param gtins Optional. Filter by GTIN. When omitted: returns all available GTINs, so no filtering based on GTIN
     *            is applied.
     * @param dispositions Optional. Contains a list of dispositions that we consider in stock. All GTINs that match any
     *            of the the given dispositions are returned. When omitted: the report will be based on the dispositions
     *            urn:epcglobal:cbv:disp:sellable_accessible, urn:epcglobal:cbv:disp:sellable_not_accessible,
     *            urn:epcglobal:cbv:disp:non_sellable_other, http://nedapretail.com/disp/maybe_stolen.
     * @param time Optional. The date and time you would like to have the stock information from. When omitted: the
     *            report will contain the latest available stock information.
     * @param includeArticles Optional. When set to TRUE this will return an array of articles. When omitted: default
     *            value is FALSE.
     * @return StockGtin response object
     */
    public StockResponse stockGtin(final String location, final List<String> gtins, final List<String> dispositions,
            final DateTime time, final Boolean includeArticles) {

        WebResource resource = resource("/epc/v3/stock.gtin14").queryParam("location", location);

        if (gtins != null) {
            for (final String gtin : gtins) {
                resource = resource.queryParam("gtins[]", gtin);
            }
        }

        if (dispositions != null) {
            for (final String disposition : dispositions) {
                resource = resource.queryParam("dispositions[]", disposition);
            }
        }

        if (time != null) {
            resource = resource.queryParam("time", time.toString());
        }

        if (includeArticles != null) {
            resource = resource.queryParam("include_articles", includeArticles.toString());
        }

        return get(resource, StockResponse.class);
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
     * Push API: subscribe.
     */
    public void subscribe(final String topic, final String callback, final String secret, final int lease_seconds) {

        final WebResource resource = resource("/subscription/1.0/subscribe").queryParam("hub.topic", topic)
                .queryParam("hub.callback", callback).queryParam("hub.secret", secret)
                .queryParam("hub.lease_seconds", "" + lease_seconds);

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
     * Get user profile by user ID. Endpoint GET /users/1.0/get
     *
     * @param userId The ID of the user to get the profile for. The special value "me" can be used to indicate the
     *            authenticated user.
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

    /**
     * Heartbeat
     */
    public void heartbeat() {
        final WebResource resource = resource("/device/1.0/heartbeat");
        post(resource);
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

    protected static <T> T get(final WebResource resource, final GenericType<T> responseClass)
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

    protected static void post(final WebResource resource, final Object requestEntity) throws UniformInterfaceException {
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
