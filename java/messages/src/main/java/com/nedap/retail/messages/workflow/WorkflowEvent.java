package com.nedap.retail.messages.workflow;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(Include.NON_NULL)
public class WorkflowEvent implements Serializable {

    private static final long serialVersionUID = -2412276734501447304L;

    public static final String TYPE = "type";
    public static final String EVENT_TIME = "event_time";
    public static final String LOCATION = "location";
    public static final String EPC_COUNT = "epc_count";
    public static final String MESSAGE_IDS = "message_ids";

    private String type;
    @JsonProperty(EVENT_TIME)
    @org.codehaus.jackson.annotate.JsonProperty(EVENT_TIME)
    private DateTime eventTime;
    private String location;
    @JsonProperty(EPC_COUNT)
    @org.codehaus.jackson.annotate.JsonProperty(EPC_COUNT)
    private Long epcCount;
    @JsonProperty(MESSAGE_IDS)
    @org.codehaus.jackson.annotate.JsonProperty(MESSAGE_IDS)
    private List<String> messageIds;

    // Empty constructor used by Jackson
    public WorkflowEvent() {
    }

    public WorkflowEvent(final String type, final DateTime eventTime, final String location, final Long epcCount,
            final List<String> messageIds) {
        super();
        this.type = type;
        this.eventTime = eventTime;
        this.location = location;
        this.epcCount = epcCount;
        this.messageIds = messageIds;
    }

    @JsonIgnore
    @org.codehaus.jackson.annotate.JsonIgnore
    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @JsonIgnore
    @org.codehaus.jackson.annotate.JsonIgnore
    public DateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(final DateTime eventTime) {
        this.eventTime = eventTime;
    }

    @JsonIgnore
    @org.codehaus.jackson.annotate.JsonIgnore
    public String getLocation() {
        return location;
    }
    public void setLocation(final String location) {
        this.location = location;
    }

    @JsonIgnore
    @org.codehaus.jackson.annotate.JsonIgnore
    public Long getEpcCount() {
        return epcCount;
    }

    public void setEpcCount(final Long epcCount) {
        this.epcCount = epcCount;
    }

    @JsonIgnore
    @org.codehaus.jackson.annotate.JsonIgnore
    public List<String> getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(final List<String> messageIds) {
        this.messageIds = messageIds;
    }

    @Override
    public String toString() {
        return "WorkflowEvent [type=" + type + ", eventTime=" + eventTime + ", location=" + location + ", epcCount="
                + epcCount + ", messageIds=" + messageIds + "]";
    }
}
