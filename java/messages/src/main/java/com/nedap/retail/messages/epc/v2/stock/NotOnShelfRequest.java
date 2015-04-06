package com.nedap.retail.messages.epc.v2.stock;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotOnShelfRequest {

    public String location;
    @JsonProperty("include_articles")
    public boolean includeArticles;

    public NotOnShelfRequest() {
    }

    public NotOnShelfRequest(final String location, final boolean includeArticles) {
        this.location = location;
        this.includeArticles = includeArticles;
    }
}
