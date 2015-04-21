package com.nedap.retail.messages.workflow;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

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

    public String type;
    @JsonProperty(EVENT_TIME)
    @org.codehaus.jackson.annotate.JsonProperty(EVENT_TIME)
    public DateTime eventTime;
    public String location;
    @JsonProperty(EPC_COUNT)
    @org.codehaus.jackson.annotate.JsonProperty(EPC_COUNT)
    public Long epcCount;
    @JsonProperty(MESSAGE_IDS)
    @org.codehaus.jackson.annotate.JsonProperty(MESSAGE_IDS)
    public List<String> messageIds;

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

    @Override
    public String toString() {
        return "WorkflowEvent [type=" + type + ", eventTime=" + eventTime + ", location=" + location + ", epcCount="
                + epcCount + ", messageIds=" + messageIds + "]";
    }
}
