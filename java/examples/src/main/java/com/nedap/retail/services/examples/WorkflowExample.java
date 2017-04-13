package com.nedap.retail.services.examples;

import com.nedap.retail.client.ApiClient;
import com.nedap.retail.client.ApiException;
import com.nedap.retail.client.api.EpcisApi;
import com.nedap.retail.client.api.WorkflowApi;
import com.nedap.retail.client.model.EpcisEvent;
import com.nedap.retail.client.model.EpcisEventContainer;
import com.nedap.retail.client.model.WorkflowEvent;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.nedap.retail.services.examples.EpcisHelper.createEvents;
import static com.nedap.retail.services.examples.EpcisHelper.printCaptureEpcisEvents;
import static com.nedap.retail.services.examples.PrintHelper.*;

public class WorkflowExample {

    private static List<String> MESSAGE_IDS = new ArrayList<>();

    private WorkflowExample() {
    }

    public static void runExample(final ApiClient client) {
        System.out.println(NEW_LINE + "*** Workflow API example ***");

        final String locationId = "http://nedapretail.com/loc/testing";
        final WorkflowApi workflowApi = new WorkflowApi(client);
        final EpcisApi epcisApi = new EpcisApi(client);

        try {
            // Make some EPCIS events first
            System.out.println(NEW_LINE + "Capturing some EPCIS events first...");

            final List<EpcisEvent> events = createEvents(locationId);

            for (final EpcisEvent event : events) {
                MESSAGE_IDS.add(event.getId());
            }

            epcisApi.captureEpcisEvents(events);
            System.out.println(printCaptureEpcisEvents(events));

            // Workflow capture
            System.out.println(NEW_LINE + "Capturing workflow event...");
            final WorkflowEvent workflow = makeWorkflowEvent(locationId, events);
            workflowApi.captureWorkflowEvent(workflow);
            System.out.println("Captured workflow event with:" + printWorkflow(workflow));

            // Workflow query
            System.out.println("Quering worflow...");
            final List<WorkflowEvent> workflowEvents = workflowApi.queryWorkflowEvents(null, null,
                    DateTime.now().minusMinutes(10), null);
            System.out.println(printWorkflowEvents(workflowEvents));

            System.out.println(NEW_LINE + "--- Workflow API example finished ---");

        } catch (final ApiException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
        }
    }

    private static WorkflowEvent makeWorkflowEvent(final String locationId, final List<EpcisEvent> events) {

        final WorkflowEvent workflow = new WorkflowEvent();
        workflow.type("cycle_count_finished");
        workflow.eventTime(DateTime.now());
        workflow.location(locationId);
        workflow.epcCount(countEpcs(events));
        workflow.messageIds(MESSAGE_IDS);
        return workflow;
    }

    private static long countEpcs(final List<EpcisEvent> events) {
        long counter = 0;

        for (final EpcisEvent epcisEvent : events) {
            counter += epcisEvent.getEpcList().size();
        }
        return Long.valueOf(counter);
    }

    private static String printWorkflow(final WorkflowEvent workflow) {
        final StringBuilder sb = new StringBuilder();
        sb.append(NEW_LINE).append(TAB).append("Type: ").append(workflow.getType());
        sb.append(NEW_LINE).append(TAB).append("Event time: ").append(workflow.getEventTime());
        sb.append(NEW_LINE).append(TAB).append("Location: ").append(workflow.getLocation());
        sb.append(NEW_LINE).append(TAB).append("Epc count: ").append(workflow.getEpcCount());
        sb.append(NEW_LINE).append(TAB).append("Message ids: ");
        sb.append(printMessageIds(workflow));
        return sb.toString();
    }

    private static String printMessageIds(final WorkflowEvent workflow) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < workflow.getMessageIds().size(); i++) {
            sb.append(NEW_LINE).append(DOUBLE_TAB);
            sb.append(i + 1).append(DOT);
            sb.append(workflow.getMessageIds().get(i));
        }
        return sb.toString();
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
