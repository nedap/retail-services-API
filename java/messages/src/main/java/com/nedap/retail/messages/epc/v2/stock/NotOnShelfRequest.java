package com.nedap.retail.messages.epc.v2.stock;


public class NotOnShelfRequest {

    public String location;
    public boolean includeArticles;

    public NotOnShelfRequest() {
    }

    public NotOnShelfRequest(final String location, final boolean includeArticles) {
        this.location = location;
        this.includeArticles = includeArticles;
    }
}
