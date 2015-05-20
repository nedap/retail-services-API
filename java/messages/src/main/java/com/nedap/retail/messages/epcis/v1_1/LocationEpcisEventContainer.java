package com.nedap.retail.messages.epcis.v1_1;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.nedap.retail.messages.workflow.WorkflowEvent;

public class LocationEpcisEventContainer extends EpcisEventContainer {

    public String location;

    @JsonProperty("workflow_events")
    public List<WorkflowEvent> workflowEvents;

    public LocationEpcisEventContainer() {
    }

    public LocationEpcisEventContainer(final List<EpcisEvent> events, final String location,
            final List<WorkflowEvent> workflowEvents) {
        super(events);
        this.location = location;
        this.workflowEvents = workflowEvents;
    }
}
