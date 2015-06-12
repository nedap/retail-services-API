package com.nedap.retail.messages.stock;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockSummaryRequest {

    private final String INCLUDE_EXCLUDED = "include_excluded";

    public String id;

    @JsonProperty(INCLUDE_EXCLUDED)
    public boolean includeExcluded = false;

}
