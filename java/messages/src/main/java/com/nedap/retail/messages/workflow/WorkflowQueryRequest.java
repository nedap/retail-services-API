package com.nedap.retail.messages.workflow;

import org.joda.time.DateTime;

public class WorkflowQueryRequest {

    public static final String MESSAGE_ID = "message_id";

    public String location;
    public String type;
    public DateTime fromEventTime;
    public DateTime untilEventTime;
    public String messageId;

    // Empty constructor used by Jackson
    public WorkflowQueryRequest() {
    }
}
