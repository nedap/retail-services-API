package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.PrintHelper.COLON;
import static com.nedap.retail.services.examples.PrintHelper.DOT;
import static com.nedap.retail.services.examples.PrintHelper.DOUBLE_TAB;
import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;
import static com.nedap.retail.services.examples.PrintHelper.ORGANIZATION_PREFIX;
import static com.nedap.retail.services.examples.PrintHelper.TAB;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.nedap.retail.messages.epcis.v2.EpcisQueryParameters;
import com.nedap.retail.messages.epcis.v2.ParameterObject;

public class EpcisExample {

    public static void runExample(final Client client) {
        System.out.println(NEW_LINE + "*** EPCIS API example ***");

        final String locationId = client.getSites().get(0).id;

        try {
            // Capture EPCIS events
            System.out.println(NEW_LINE + "Capturing EPCIS events...");
            final EpcisEventContainer epcisEventsList = new EpcisEventContainer();
            epcisEventsList.events = createEvents(locationId);
            client.captureEpcisEvents(epcisEventsList);
            System.out.println(printCaptureEpcisEvents(epcisEventsList));

            // Query EPCIS events
            System.out.println(NEW_LINE + "Quering EPCIS events...");
            final List<EpcisEvent> events = client.queryEpcisEvents(makeEpcisQueryParameters());
            System.out.println(printEpcisEvents(events));

            System.out.println(NEW_LINE + "--- EPCIS API example finished ---");

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
        event.action = Action.OBSERVE.action();
        event.disposition = Disposition.SELLABLE_ACCESSIBLE.disposition();
        event.bizLocation = locationId;
        event.bizStep = Bizstep.CYCLE_COUNTING.bizStep();
        event.readPoint = locationId;
        event.epcList = makeEpcList1();
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
        event.action = Action.OBSERVE.action();
        event.disposition = Disposition.SELLABLE_ACCESSIBLE.disposition();
        event.bizLocation = locationId;
        event.bizStep = Bizstep.CYCLE_COUNTING.bizStep();
        event.readPoint = locationId;
        event.epcList = makeEpcList2();
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
            sb.append(NEW_LINE).append(TAB);
            sb.append((i + 1)).append(DOT);
            sb.append(epcisEventContainer.events.get(i).id);
        }
        return sb.toString();
    }

    private static EpcisQueryParameters makeEpcisQueryParameters() {
        final EpcisQueryParameters queryParameters = new EpcisQueryParameters();
        queryParameters.parameters = Arrays.asList(makeParameterObject1());
        return queryParameters;
    }

    private static ParameterObject makeParameterObject1() {
        final ParameterObject parameter = new ParameterObject();
        parameter.name = "GE_event_time";
        parameter.value = DateTime.now().minusMinutes(1).toString();
        return parameter;
    }

    private static String printEpcisEvents(final List<EpcisEvent> events) {
        final StringBuilder sb = new StringBuilder("Retrieved EPCIS events within last minute:");
        for (int i = 0; i < events.size(); i++) {
            sb.append(NEW_LINE).append(NEW_LINE).append(TAB);
            sb.append("EPCIS event ").append((i + 1)).append(COLON);
            sb.append(printEpcisEvent(events.get(i)));
        }
        return sb.toString();
    }

    private static String printEpcisEvent(final EpcisEvent event) {
        final StringBuilder sb = new StringBuilder();
        sb.append(NEW_LINE).append(TAB).append("Id: ").append(event.id);
        sb.append(NEW_LINE).append(TAB).append("Type: ").append(event.type);
        sb.append(NEW_LINE).append(TAB).append("Event time: ").append(event.eventTime);
        sb.append(NEW_LINE).append(TAB).append("Action: ").append(event.action);
        sb.append(NEW_LINE).append(TAB).append("Disposition: ").append(event.disposition);
        sb.append(NEW_LINE).append(TAB).append("Biz location: ").append(event.bizLocation);
        sb.append(NEW_LINE).append(TAB).append("Biz step: ").append(event.bizStep);
        sb.append(NEW_LINE).append(TAB).append("Read point: ").append(event.readPoint);
        sb.append(NEW_LINE).append(TAB).append("Epc list: ");
        sb.append(printEpcList(event));
        return sb.toString();
    }

    private static String printEpcList(final EpcisEvent epcisEvent) {
        final ObjectEvent event = (ObjectEvent) epcisEvent;
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < event.epcList.size(); i++) {
            sb.append(NEW_LINE).append(DOUBLE_TAB);
            sb.append((i + 1)).append(event.epcList.get(i));
        }
        return sb.toString();
    }
}
