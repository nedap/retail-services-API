package com.nedap.retail.messages.workflow;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class WorkflowEvent implements Serializable {

    private static final long serialVersionUID = -2412276734501447304L;

    public static final String TYPE = "type";
    public static final String EVENT_TIME = "event_time";
    public static final String START_TIME = "start_time";
    public static final String LOCATION = "location";
    public static final String EPC_COUNT = "epc_count";
    public static final String MESSAGE_IDS = "message_ids";
    public static final String CLIENT_ID = "client_id";

    public String type;

    @JsonProperty(EVENT_TIME)
    public DateTime eventTime;

    @JsonProperty(START_TIME)
    public DateTime startTime;

    public String location;

    @JsonProperty(EPC_COUNT)
    public Long epcCount;

    @JsonProperty(MESSAGE_IDS)
    public List<String> messageIds;

    @JsonProperty(CLIENT_ID)
    public String clientId;

    // Empty constructor used by Jackson
    public WorkflowEvent() {
    }

    public WorkflowEvent(final String type, final DateTime startTime, final DateTime eventTime, final String location,
            final Long epcCount, final List<String> messageIds, final String clientId) {
        super();
        this.type = type;
        this.eventTime = eventTime;
        this.startTime = startTime;
        this.location = location;
        this.epcCount = epcCount;
        this.messageIds = messageIds;
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "WorkflowEvent [type=" + type + ", eventTime=" + eventTime + ", startTime=" + startTime + ", location="
                + location + ", epcCount=" + epcCount + ", messageIds=" + messageIds + ", clientId=" + clientId + "]";
    }
}
