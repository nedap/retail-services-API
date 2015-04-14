package com.nedap.retail.messages;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.jaxrs.Annotations;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.Articles;
import com.nedap.retail.messages.epc.v2.approved_difference_list.ApprovedDifferenceListSummary;
import com.nedap.retail.messages.epc.v2.approved_difference_list.request.ApprovedDifferenceListCaptureRequest;
import com.nedap.retail.messages.epc.v2.approved_difference_list.response.ApprovedDifferenceListExportResponse;
import com.nedap.retail.messages.epc.v2.approved_difference_list.response.ApprovedDifferenceListResponse;
import com.nedap.retail.messages.epc.v2.difference_list.DifferenceListResponse;
import com.nedap.retail.messages.epc.v2.stock.NotOnShelfRequest;
import com.nedap.retail.messages.epc.v2.stock.NotOnShelfResponse;
import com.nedap.retail.messages.epc.v2.stock.StockResponse;
import com.nedap.retail.messages.epcis.v1_1.EpcisEvent;
import com.nedap.retail.messages.epcis.v1_1.EpcisEventContainer;
import com.nedap.retail.messages.epcis.v2.EpcisQueryParameters;
import com.nedap.retail.messages.organization.Location;
import com.nedap.retail.messages.organization.Organizations;
import com.nedap.retail.messages.stock.Stock;
import com.nedap.retail.messages.stock.StockSummary;
import com.nedap.retail.messages.stock.StockSummaryListRequest;
import com.nedap.retail.messages.subscription.Subscription;
import com.nedap.retail.messages.system.SystemListPayload;
import com.nedap.retail.messages.system.SystemStatusPayload;
import com.nedap.retail.messages.users.User;
import com.nedap.retail.messages.workflow.QueryRequest;
import com.nedap.retail.messages.workflow.WorkflowEvent;
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

    protected com.sun.jersey.api.client.Client initHttpClient() {
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
        final WebResource resource = resource("/system/v1/list");
        return get(resource, new GenericType<List<SystemListPayload>>() {
        });
    }

    /**
     * System API: status
     */
    public List<SystemStatusPayload> getSystemStatus() {
        final WebResource resource = resource("/system/v1/status");
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
        final WebResource resource = resource("/system/v1/firmware_versions").queryParam("system_id", systemId);
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
        final WebResource resource = resource("/system/v1/update").queryParam("system_id", systemId).queryParam(
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
        final Map<String, String> response = post(resource, new GenericType<Map<String, String>>() {
        }, stock);
        return response.get("id");
    }

    /**
     * ERP API: retrieve stock
     *
     * @param id ID of stock
     * @return The requested stock
     */
    public Stock retrieveErpStock(final String id) {
        final WebResource resource = resource("/erp/v1/stock.retrieve").queryParam("id", id);
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
     * @param request Stock summary list request with location and start/stop search dates
     * @return List of summaries per stock available for the specified location
     */
    public List<StockSummary> getErpStockList(final StockSummaryListRequest request) {
        WebResource resource = resource("/erp/v1/stock.list").queryParam("location", request.location);

        if (request.fromEventTime != null) {
            resource = resource.queryParam("from_event_time", request.fromEventTime.toString());
        }
        if (request.untilEventTime != null) {
            resource = resource.queryParam("until_event_time", request.untilEventTime.toString());
        }

        return get(resource, new GenericType<List<StockSummary>>() {
        });
    }

    /**
     * ERP API: delete stock for provided stock id
     *
     * @param id ID of stock
     * @return 204 status if delete is successful, this process is irreversible
     */
    public void deleteErpStock(final String id) {
        final WebResource resource = resource("/erp/v1/stock.delete").queryParam("id", id);
        delete(resource);
    }

    /**
     * Requests the total number of articles registered in !D Cloud.
     *
     * @return Total number of articles.
     */
    public Long articleQuantity() {
        final WebResource resource = resource("/article/v2/quantity");
        final Map<String, Long> response = get(resource, new GenericType<Map<String, Long>>() {
        });
        return response.get("quantity");
    }

    /**
     * Retrieve one or more articles based on GTIN value(s). Optionally specify returned Article properties.
     *
     * @param gtins The GTIN(s) of which article information should be returned. Repeat key-value for retrieving
     *            multiple GTINs.
     * @param fields Optional. Which fields should be included in the response. Can be any of the Article fields. When
     *            omitted: all fields will be included in the response. Repeat key-value for retrieving multiple fields.
     * @return List of articles retrieved.
     */
    public List<Article> articleDetailsByGtins(final List<String> gtins, final List<String> fields) {
        WebResource resource = resource("/article/v2/retrieve");

        for (final String gtin : gtins) {
            resource = resource.queryParam("gtins[]", gtin);
        }
        if (fields != null) {
            for (final String field : fields) {
                resource = resource.queryParam("fields[]", field);
            }
        }

        return get(resource, new GenericType<List<Article>>() {
        });
    }

    /**
     * Retrieve one or more articles based on barcode(s). Optionally specify returned Article properties. Barcode type
     * is ignored. All articles that have a barcode that match (case insensitive for alphanumeric barcodes) one of the
     * given barcodes is returned.
     *
     * @param barcodes The barcode(s) of which article information should be returned. Repeat key-value for retrieving
     *            multiple barcodes.
     * @param fields Optional. Which fields should be included in the response. Can be any of the Article fields. When
     *            omitted: all fields will be included in the response. Repeat key-value for retrieving multiple fields.
     * @return List of articles retrieved.
     */
    public List<Article> articleDetailsByBarcodes(final List<String> barcodes, final List<String> fields) {
        WebResource resource = resource("/article/v2/retrieve");

        for (final String barcode : barcodes) {
            resource = resource.queryParam("barcodes[]", barcode);
        }
        if (fields != null) {
            for (final String field : fields) {
                resource = resource.queryParam("fields[]", field);
            }
        }

        return get(resource, new GenericType<List<Article>>() {
        });
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
    public List<Article> retrieveArticles(final DateTime updatedAfter, final int skip, final int count,
            final List<String> fields) {
        WebResource resource = resource("/article/v2/retrieve");

        if (updatedAfter != null) {
            resource = resource.queryParam("updated_after", updatedAfter.toString());
        }

        resource = resource.queryParam("skip", String.valueOf(skip));
        resource = resource.queryParam("count", String.valueOf(count));

        if (fields != null) {
            for (final String field : fields) {
                resource = resource.queryParam("fields[]", field);
            }
        }

        return get(resource, new GenericType<List<Article>>() {
        });
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
        delete(resource);
    }

    /**
     * Captures RFID stock.
     *
     * @param stock RFID stock to capture
     * @return Id of captured stock
     */
    public String captureRfidStock(final Stock stock) {
        final WebResource resource = resource("/epc/v2/stock.capture");
        final Map<String, String> response = post(resource, new GenericType<Map<String, String>>() {
        }, stock);
        return response.get("id");
    }

    /**
     * Captures approved difference list.
     *
     * @param approvedDifferenceListCaptureRequest
     * @return Id of the captured approved difference list
     */
    public String captureApprovedDifferenceList(
            final ApprovedDifferenceListCaptureRequest approvedDifferenceListCaptureRequest) {

        final WebResource resource = resource("/epc/v2/approved_difference_list.capture");

        final Map<String, String> response = post(resource, new GenericType<Map<String, String>>() {
        }, approvedDifferenceListCaptureRequest);
        return response.get("id");
    }

    /**
     * Returns approved difference list with a given id.
     *
     * @param approvedDifferenceListId Id of wanted approved difference list
     * @return Wanted approved difference list
     */
    public ApprovedDifferenceListResponse getApprovedDifferenceList(final String approvedDifferenceListId) {
        final WebResource resource = resource("/epc/v2/approved_difference_list.retrieve").queryParam("id",
                approvedDifferenceListId);

        return get(resource, ApprovedDifferenceListResponse.class);
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
     * Retrieves approved difference list using difference list ID as it is going to be exported.
     *
     * @param approvedDifferenceListId Id approved difference list
     * @return Approved difference list for exporting with provided id
     */
    public ApprovedDifferenceListExportResponse approvedDifferenceListExport(final String approvedDifferenceListId) {
        if (approvedDifferenceListId == null) {
            throw new IllegalArgumentException("Approved difference list id is required");
        }

        final WebResource resource = resource("/epc/v2/approved_difference_list.export").queryParam("id",
                approvedDifferenceListId);

        return get(resource, ApprovedDifferenceListExportResponse.class);
    }

    /**
     * Returns approved difference list summaries for location optional between counting times.
     *
     * @param organizationId Id of organization for approved difference list
     * @param locationId Location identifier
     * @param fromRfidTime Lower boundary for approved difference list time
     * @param untilRfidTime Upper boundary for approved difference list time
     * @return List of approved difference list summaries requested location
     */
    public List<ApprovedDifferenceListSummary> getApprovedDifferenceListSummaries(final String locationId,
            final DateTime fromRfidTime, final DateTime untilRfidTime) {

        WebResource resource = resource("/epc/v2/approved_difference_list.list").queryParam("location", locationId);

        if (fromRfidTime != null) {
            resource = resource.queryParam("from_rfid_time", fromRfidTime.toString());
        }
        if (untilRfidTime != null) {
            resource = resource.queryParam("until_rfid_time", untilRfidTime.toString());
        }

        return get(resource, new GenericType<List<ApprovedDifferenceListSummary>>() {
        });
    }

    /**
     * Returns summary of approved difference list for location and RFID time.
     *
     * @param locationId The location for status of approved difference list
     * @param rfidTime Rfid time of approved difference list for status information
     * @return list of approved difference list summary resources
     */
    public ApprovedDifferenceListSummary getApprovedDifferenceListStatus(final String locationId, final String rfidTime) {

        final WebResource resource = resource("/epc/v2/approved_difference_list.status").queryParam("location",
                locationId).queryParam("rfid_time", rfidTime);

        return get(resource, ApprovedDifferenceListSummary.class);
    }

    /**
     * Deletes approved difference list with provided id
     *
     * @param id Id of approved difference list for deletion
     */
    public void deleteApprovedDifferenceList(final UUID id) {
        final WebResource resource = resource("/epc/v2/approved_difference_list.delete")
                .queryParam("id", id.toString());
        delete(resource);
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
     * Returns RFID Stock for location.
     *
     * @param locationId Location identifier
     * @param fromRfidTime Lower boundary for rfid stock time
     * @param untilRfidTime Upper boundary for rfid stock time
     * @return List of summaries for rfid stock on requested location
     */
    public List<StockSummary> getRfidStockList(final String locationId, final DateTime fromRfidTime,
            final DateTime untilRfidTime) {

        WebResource resource = resource("/epc/v2/stock.list").queryParam("location", locationId);

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
     * Lists all the items that are not-on-shelf for a certain store.
     *
     * @param request NotOnShelfRequest
     * @return NotOnShelfResponse
     */
    public NotOnShelfResponse notOnShelf(final NotOnShelfRequest request) {
        final WebResource resource = resource("/epc/v2/not_on_shelf").queryParam("location", request.location);
        return get(resource, NotOnShelfResponse.class);
    }

    /**
     * The Capture Service captures one or more EPCIS events at a time. This does not imply any relationship between
     * these EPCIS events. Each element of the argument list is accepted if it is a valid EPCIS event or subtype that
     * conforms to the above EPCIS event types.
     *
     * @param events EPCIS events to capture
     */
    public void captureEpcisEvents(final EpcisEventContainer events) {
        final WebResource resource = resource("/epcis/v2/capture");
        post(resource, events);
    }

    /**
     * Return a list of EPCIS events with given parameters.
     *
     * @param request Epcis query parameters
     * @return List of EPCIS events which satisfies query parameters
     */
    public List<EpcisEvent> queryEpcisEvents(final EpcisQueryParameters request) {
        final WebResource resource = resource("/epcis/v2/query");
        final List<EpcisEvent> events = post(resource, new GenericType<List<EpcisEvent>>() {
        }, request);
        return events;
    }

    /**
     * Stores the workflow event.
     *
     * @param workflow Workflow event object.
     */
    public void captureWorkflow(final WorkflowEvent workflow) {
        final WebResource resource = resource("/workflow/v2/capture");
        post(resource, workflow);
    }

    /**
     * Returns a list od Workflow events with required parameters.
     *
     * @param request Query request with parameters
     * @return List of Workflow events matching required parameters
     */
    public List<WorkflowEvent> queryWorkflow(final QueryRequest request) {
        WebResource resource = resource("/workflow/v2/query");

        if (request.getLocation() != null) {
            resource = resource.queryParam("location", request.getLocation());
        }
        if (request.getType() != null) {
            resource = resource.queryParam("type", request.getType());
        }
        if (request.getFrom() != null) {
            resource = resource.queryParam("from_event_time", request.getFrom().toString());
        }
        if (request.getTo() != null) {
            resource = resource.queryParam("until_event_time", request.getTo().toString());
        }

        return get(resource, new GenericType<List<WorkflowEvent>>() {
        });
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
        } catch (final ClientException ex) {
            // Thrown when the status of the HTTP response is greater than or equal to 300.
            if (ex.getStatusCode() == 404) {
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

    protected static <T> T get(final WebResource resource, final Class<T> responseClass) {
        try {
            return resource.accept(APPLICATION_JSON_TYPE).get(responseClass);
        } catch (final UniformInterfaceException ex) {
            throw new ClientException(ex);
        }
    }

    protected static <T> T get(final WebResource resource, final GenericType<T> responseClass) {
        try {
            return resource.accept(APPLICATION_JSON_TYPE).get(responseClass);
        } catch (final UniformInterfaceException ex) {
            throw new ClientException(ex);
        }
    }

    protected static void post(final WebResource resource) {
        try {
            resource.accept(APPLICATION_JSON_TYPE).post();
        } catch (final UniformInterfaceException ex) {
            throw new ClientException(ex);
        }
    }

    protected static <T> T post(final WebResource resource, final Class<T> responseClass) {
        try {
            return resource.accept(APPLICATION_JSON_TYPE).post(responseClass);
        } catch (final UniformInterfaceException ex) {
            throw new ClientException(ex);
        }
    }

    protected static void post(final WebResource resource, final Object requestEntity) {
        try {
            resource.accept(APPLICATION_JSON_TYPE).type(APPLICATION_JSON_TYPE).post(requestEntity);
        } catch (final UniformInterfaceException ex) {
            throw new ClientException(ex);
        }
    }

    protected static <T> T post(final WebResource resource, final Class<T> responseClass, final Object requestEntity) {
        try {
            return resource.accept(APPLICATION_JSON_TYPE).type(APPLICATION_JSON_TYPE)
                    .post(responseClass, requestEntity);
        } catch (final UniformInterfaceException ex) {
            throw new ClientException(ex);
        }
    }

    protected static <T> T post(final WebResource resource, final GenericType<T> responseClass,
            final Object requestEntity) {
        return resource.accept(APPLICATION_JSON_TYPE).type(APPLICATION_JSON_TYPE).post(responseClass, requestEntity);
    }

    protected static void delete(final WebResource resource) {
        try {
            resource.accept(APPLICATION_JSON_TYPE).delete();
        } catch (final UniformInterfaceException ex) {
            throw new ClientException(ex);
        }
    }
}
