package com.nedap.retail.messages.epc.v2.difference_list;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonAnySetter;

public class DifferenceListRequest {

    @JsonProperty("erp_stock_id")
    public String erpStockId;

    public DateTime time;
    public String location;

    @JsonProperty("only_differences")
    public boolean onlyDifferences = true;

    @JsonProperty("include_articles")
    public boolean includeArticles = false;

    public DifferenceListRequest() {
    }

    public DifferenceListRequest(final String erpStockId) {
        this.erpStockId = erpStockId;
    }

    /**
     * Fallback handler for all unrecognized properties found in JSON content.
     *
     * @param key unknown key
     * @param value value of unkown key
     */
    @JsonAnySetter
    public void handleUnknown(final String key, final Object value) {
        // ignore unknown properties
    }
}
