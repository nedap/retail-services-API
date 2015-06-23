package com.nedap.retail.messages.stock;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RetrieveStockSummaryRequest {

    private static final String WITH_EXCLUDED = "with_excluded";

    public String id;

    @JsonProperty(WITH_EXCLUDED)
    public boolean withExcluded = false;

}
