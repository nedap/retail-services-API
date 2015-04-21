package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.PrintHelper.DOT;
import static com.nedap.retail.services.examples.PrintHelper.DOUBLE_TAB;
import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;
import static com.nedap.retail.services.examples.PrintHelper.ORGANIZATION_PREFIX;
import static com.nedap.retail.services.examples.PrintHelper.TAB;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.messages.epcis.v1_1.EpcisEvent;
import com.nedap.retail.messages.epcis.v1_1.EpcisEventContainer;
import com.nedap.retail.messages.epcis.v1_1.ObjectEvent;
import com.nedap.retail.messages.epcis.v1_1.cbv.Action;
import com.nedap.retail.messages.epcis.v1_1.cbv.Bizstep;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;
import com.nedap.retail.messages.workflow.QueryRequest;
import com.nedap.retail.messages.workflow.WorkflowEvent;

public class WorkflowExample {

    private static List<String> MESSAGE_IDS = new ArrayList<>();

    public static void runExample(final Client client) {
        System.out.println(NEW_LINE + "*** Workflow API example ***");

        final String locationId = client.getSites().get(0).id;

        try {
            // Make some EPCIS events first
            System.out.println(NEW_LINE + "Capturing some EPCIS events first...");
            final EpcisEventContainer epcisEventsContainer = new EpcisEventContainer();
            epcisEventsContainer.events = createEvents(locationId);
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

    private static List<EpcisEvent> createEvents(final String locationId) {
        final List<EpcisEvent> events = new ArrayList<>();
        events.add(createEpcisEvent1(locationId));
        events.add(createEpcisEvent2(locationId));
        return events;
    }

    private static EpcisEvent createEpcisEvent1(final String locationId) {
        final ObjectEvent event = new ObjectEvent();
        event.id = generateEpcisEventID(ORGANIZATION_PREFIX, 1).toString();
        event.eventTime = DateTime.now();
        event.action = Action.OBSERVE.action;
        event.disposition = Disposition.SELLABLE_ACCESSIBLE.disposition;
        event.bizLocation = locationId;
        event.bizStep = Bizstep.CYCLE_COUNTING.bizStep;
        event.readPoint = locationId;
        event.epcList = makeEpcList1();
        MESSAGE_IDS.add(event.id);
        return event;
    }

    /**
     * Calculates consistent (hash) ID based on organization prefix and counter value of epc events.
     */
    private static UUID generateEpcisEventID(final long organizationPrefix, final int epcisEventCounter) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            final byte[] digest = md.digest(("" + organizationPrefix + epcisEventCounter).getBytes());
            return UUID.nameUUIDFromBytes(digest);
        } catch (final NoSuchAlgorithmException ex) {
            throw new IllegalStateException("Could not create SHA-256 message digester", ex);
        }
    }

    private static List<String> makeEpcList1() {
        final List<String> epcList = new ArrayList<>();
        epcList.add("urn:epc:id:sgtin:2011200.000111.1");
        epcList.add("urn:epc:id:sgtin:2011200.090001.1");
        epcList.add("urn:epc:id:sgtin:2011200.000101.1");
        epcList.add("urn:epc:id:sgtin:2011200.000102.1");
        epcList.add("urn:epc:id:sgtin:2011200.000103.1");
        return epcList;
    }

    private static EpcisEvent createEpcisEvent2(final String locationId) {
        final ObjectEvent event = new ObjectEvent();
        event.id = generateEpcisEventID(ORGANIZATION_PREFIX, 2).toString();
        event.eventTime = DateTime.now();
        event.action = Action.OBSERVE.action;
        event.disposition = Disposition.SELLABLE_ACCESSIBLE.disposition;
        event.bizLocation = locationId;
        event.bizStep = Bizstep.CYCLE_COUNTING.bizStep;
        event.readPoint = locationId;
        event.epcList = makeEpcList2();
        MESSAGE_IDS.add(event.id);
        return event;
    }

    private static List<String> makeEpcList2() {
        final List<String> epcList = new ArrayList<>();
        epcList.add("urn:epc:id:sgtin:2011200.000111.2");
        epcList.add("urn:epc:id:sgtin:2011200.090002.1");
        epcList.add("urn:epc:id:sgtin:2011200.000201.1");
        epcList.add("urn:epc:id:sgtin:2011200.000202.1");
        epcList.add("urn:epc:id:sgtin:2011200.000203.1");
        return epcList;
    }

    private static String printCaptureEpcisEvents(final EpcisEventContainer epcisEventContainer) {
        final StringBuilder sb = new StringBuilder("Captured EPCIS object events with ids:");
        for (int i = 0; i < epcisEventContainer.events.size(); i++) {
            sb.append(NEW_LINE + TAB);
            sb.append((i + 1) + DOT);
            sb.append(epcisEventContainer.events.get(i).id);
        }
        return sb.toString();
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
        sb.append(NEW_LINE + TAB + "Type: " + workflow.type);
        sb.append(NEW_LINE + TAB + "Event time: " + workflow.eventTime);
        sb.append(NEW_LINE + TAB + "Location: " + workflow.location);
        sb.append(NEW_LINE + TAB + "Epc count: " + workflow.epcCount);
        sb.append(NEW_LINE + TAB + "Message ids: ");
        sb.append(printMessageIds(workflow));
        return sb.toString();
    }

    private static String printMessageIds(final WorkflowEvent workflow) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < workflow.messageIds.size(); i++) {
            sb.append(NEW_LINE + DOUBLE_TAB);
            sb.append((i + 1) + DOT);
            sb.append(workflow.messageIds.get(i));
        }
        return sb.toString();
    }

    private static QueryRequest makeWorkflowQueryRequest() {
        final QueryRequest request = new QueryRequest();
        request.from = DateTime.now().minusMinutes(10);
        return request;
    }

    private static String printWorkflowEvents(final List<WorkflowEvent> workflowEvents) {
        final StringBuilder sb = new StringBuilder("Retrieved workflow events within a last 10 minutes:");
        for (int i = 0; i < workflowEvents.size(); i++) {
            sb.append(NEW_LINE + NEW_LINE + TAB);
            sb.append("Workflow event " + (i + 1) + DOT);
            sb.append(printWorkflow(workflowEvents.get(i)));
        }
        return sb.toString();
    }
}
