package com.nedap.retail.messages.epc.v2;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

public class DifferenceListRequest {

    @JsonProperty("erp_stock_id")
    private String erpStockId;
    private DateTime time;
    @JsonProperty("only_differences")
    private boolean onlyDifferences = true;
    @JsonProperty("include_articles")
    private boolean includeArticles = false;

    public DifferenceListRequest() {
    }

    public DifferenceListRequest(final String erpStockId) {
        this.erpStockId = erpStockId;
    }

    public String getErpStockId() {
        return erpStockId;
    }

    public void setErpStockId(final String erpStockId) {
        this.erpStockId = erpStockId;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(final DateTime time) {
        this.time = time;
    }

    public Boolean getOnlyDifferences() {
        return onlyDifferences;
    }

    public void setOnlyDifferences(final Boolean onlyDifferences) {
        this.onlyDifferences = onlyDifferences;
    }

    public Boolean getIncludeArticles() {
        return includeArticles;
    }

    public void setIncludeArticles(final Boolean includeArticles) {
        this.includeArticles = includeArticles;
    }

    /**
     * Fallback handler for all unrecognized properties found in JSON content.
     * 
     * @param key
     * @param value
     */
    @JsonAnySetter
    public void handleUnknown(final String key, final Object value) {
    }

}
