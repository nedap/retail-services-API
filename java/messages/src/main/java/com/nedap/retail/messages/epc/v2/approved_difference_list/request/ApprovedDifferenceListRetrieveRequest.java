package com.nedap.retail.messages.epc.v2.approved_difference_list.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class ApprovedDifferenceListRetrieveRequest {

    public UUID id;

    @JsonProperty("include_articles")
    public boolean includeArticles = false;

}
