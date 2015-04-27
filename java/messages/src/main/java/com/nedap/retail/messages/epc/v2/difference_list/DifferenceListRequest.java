package com.nedap.retail.messages.epc.v2.difference_list;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class DifferenceListRequest {

    @org.codehaus.jackson.annotate.JsonProperty("erp_stock_id")
    public String erpStockId;

    public DateTime time;
    public String location;

    @org.codehaus.jackson.annotate.JsonProperty("only_differences")
    public boolean onlyDifferences = true;

    @org.codehaus.jackson.annotate.JsonProperty("include_articles")
    public boolean includeArticles = false;

    public DifferenceListRequest() {
    }

    public DifferenceListRequest(final String erpStockId) {
        this.erpStockId = erpStockId;
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
