package com.nedap.retail.services.examples;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.messages.epcis.v1_1.EpcisEvent;
import com.nedap.retail.messages.epcis.v1_1.EpcisEventContainer;
import com.nedap.retail.messages.epcis.v1_1.ObjectEvent;
import com.nedap.retail.messages.epcis.v2.EpcisQueryParameters;
import com.nedap.retail.messages.epcis.v2.ParameterObject;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

import static com.nedap.retail.services.examples.EpcisHelper.createEvents;
import static com.nedap.retail.services.examples.EpcisHelper.printCaptureEpcisEvents;
import static com.nedap.retail.services.examples.PrintHelper.*;

public class EpcisExample {

    private EpcisExample() {
    }

    public static void runExample(final Client client) {
        System.out.println(NEW_LINE + "*** EPCIS API example ***");

        final String locationId = "http://nedapretail.com/loc/testing";

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
            sb.append("EPCIS event ").append(i + 1).append(COLON);
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
            sb.append(i + 1).append(DOT).append(WHITESPACE).append(event.epcList.get(i));
        }
        return sb.toString();
    }
}
