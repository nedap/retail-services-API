package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.EpcisHelper.createEvents;
import static com.nedap.retail.services.examples.EpcisHelper.printCaptureEpcisEvents;
import static com.nedap.retail.services.examples.PrintHelper.DOT;
import static com.nedap.retail.services.examples.PrintHelper.DOUBLE_TAB;
import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;
import static com.nedap.retail.services.examples.PrintHelper.TAB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.messages.epcis.v1_1.EpcisEvent;
import com.nedap.retail.messages.epcis.v1_1.EpcisEventContainer;
import com.nedap.retail.messages.epcis.v1_1.ObjectEvent;
import com.nedap.retail.messages.workflow.WorkflowEvent;
import com.nedap.retail.messages.workflow.WorkflowQueryRequest;

public class WorkflowExample {

    private static List<String> MESSAGE_IDS = new ArrayList<>();

    private WorkflowExample() {
    }

    public static void runExample(final Client client) {
        System.out.println(NEW_LINE + "*** Workflow API example ***");

        final String locationId = client.getSites().get(0).id;

        try {
            // Make some EPCIS events first
            System.out.println(NEW_LINE + "Capturing some EPCIS events first...");
            final EpcisEventContainer epcisEventsContainer = new EpcisEventContainer();
            epcisEventsContainer.events = createEvents(locationId);
            MESSAGE_IDS.add(UUID.randomUUID().toString());
            MESSAGE_IDS.add(UUID.randomUUID().toString());
            client.captureEpcisEvents(epcisEventsContainer);
            System.out.println(printCaptureEpcisEvents(epcisEventsContainer));

            // Workflow capture
            System.out.println(NEW_LINE + "Capturing workflow event...");
            final WorkflowEvent workflow = makeWorkflowEvent(locationId, epcisEventsContainer);
            client.captureWorkflow(workflow);
            System.out.println("Captured workflow event with:" + printWorkflow(workflow));

            // Workflow query
            System.out.println("Quering worflow...");
            final List<WorkflowEvent> workflowEvents = client.queryWorkflow(makeWorkflowQueryRequest());
            System.out.println(printWorkflowEvents(workflowEvents));

            System.out.println(NEW_LINE + "--- Workflow API example finished ---");

        } catch (final ClientException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
        }
    }

    private static WorkflowEvent makeWorkflowEvent(final String locationId,
            final EpcisEventContainer epcisEventsContainer) {

        final WorkflowEvent workflow = new WorkflowEvent();
        workflow.type = "cycle_count_finished";
        workflow.eventTime = DateTime.now();
        workflow.location = locationId;
        workflow.epcCount = countEpcs(epcisEventsContainer);
        workflow.messageIds = MESSAGE_IDS;
        return workflow;
    }

    private static long countEpcs(final EpcisEventContainer epcisEventsContainer) {
        long counter = 0;

        for (final EpcisEvent epcisEvent : epcisEventsContainer.events) {
            final ObjectEvent objectEventIteration = (ObjectEvent) epcisEvent;
            counter += objectEventIteration.epcList.size();
        }
        return Long.valueOf(counter);
    }

    private static String printWorkflow(final WorkflowEvent workflow) {
        final StringBuilder sb = new StringBuilder();
        sb.append(NEW_LINE).append(TAB).append("Type: ").append(workflow.type);
        sb.append(NEW_LINE).append(TAB).append("Event time: ").append(workflow.eventTime);
        sb.append(NEW_LINE).append(TAB).append("Location: ").append(workflow.location);
        sb.append(NEW_LINE).append(TAB).append("Epc count: ").append(workflow.epcCount);
        sb.append(NEW_LINE).append(TAB).append("Message ids: ");
        sb.append(printMessageIds(workflow));
        return sb.toString();
    }

    private static String printMessageIds(final WorkflowEvent workflow) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < workflow.messageIds.size(); i++) {
            sb.append(NEW_LINE).append(DOUBLE_TAB);
            sb.append(i + 1).append(DOT);
            sb.append(workflow.messageIds.get(i));
        }
        return sb.toString();
    }

    private static WorkflowQueryRequest makeWorkflowQueryRequest() {
        final WorkflowQueryRequest request = new WorkflowQueryRequest();
        request.fromEventTime = DateTime.now().minusMinutes(10);
        return request;
    }

    private static String printWorkflowEvents(final List<WorkflowEvent> workflowEvents) {
        final StringBuilder sb = new StringBuilder("Retrieved workflow events within a last 10 minutes:");
        for (int i = 0; i < workflowEvents.size(); i++) {
            sb.append(NEW_LINE).append(NEW_LINE).append(TAB);
            sb.append("Workflow event ").append(i + 1).append(DOT);
            sb.append(printWorkflow(workflowEvents.get(i)));
        }
        return sb.toString();
    }
}
