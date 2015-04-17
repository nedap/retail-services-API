package com.nedap.retail.messages.workflow;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class QueryRequest {

    public static final String MESSAGE_ID = "message_id";

    private String location;
    private String type;
    @JsonProperty("from_event_time")
    private DateTime from;
    @JsonProperty("until_event_time")
    private DateTime to;
    @JsonProperty(MESSAGE_ID)
    private String messageId;

    // Empty constructor used by Jackson
    public QueryRequest() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public DateTime getFrom() {
        return from;
    }

    public void setFrom(final DateTime from) {
        this.from = from;
    }

    public DateTime getTo() {
        return to;
    }

    public void setTo(final DateTime to) {
        this.to = to;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }
}