package com.nedap.retail.messages.workflow;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class QueryRequest {

    public static final String MESSAGE_ID = "message_id";

    public String location;
    public String type;

    @JsonProperty("from_event_time")
    public DateTime from;

    @JsonProperty("until_event_time")
    public DateTime to;

    @JsonProperty(MESSAGE_ID)
    public String messageId;

    // Empty constructor used by Jackson
    public QueryRequest() {
    }
}
