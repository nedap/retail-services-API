package com.nedap.retail.services.examples;

import com.nedap.retail.client.ApiClient;
import com.nedap.retail.client.ApiException;
import com.nedap.retail.client.api.EpcisApi;
import com.nedap.retail.client.model.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import static com.nedap.retail.services.examples.EpcisHelper.createEvents;
import static com.nedap.retail.services.examples.EpcisHelper.printCaptureEpcisEvents;
import static com.nedap.retail.services.examples.PrintHelper.*;

import org.joda.time.DateTime;

public class EpcisExample {

    private EpcisExample() {
    }

    public static void runExample(final ApiClient client) {
        System.out.println(NEW_LINE + "*** EPCIS API example ***");

        EpcisApi api = new EpcisApi(client);

        final String locationId = "http://nedapretail.com/loc/testing";

        try {
            // Capture EPCIS events
            System.out.println(NEW_LINE + "Capturing EPCIS events...");
            final List<EpcisEvent> epcisEventsList = createEvents(locationId);
            api.captureEpcisEvents(epcisEventsList);
            System.out.println(printCaptureEpcisEvents(epcisEventsList));

            // Query EPCIS events
            System.out.println(NEW_LINE + "Quering EPCIS events...");
            final List<EpcisEvent> events = api.queryEpcisEvents(makeEpcisQueryParameters());
            System.out.println("Retrieved EPCIS events within last minute:");
            System.out.println("Events: " + events.size());
            System.out.println(printEpcisEvents(events));

            System.out.println(NEW_LINE + "--- EPCIS API example finished ---");

        } catch (final ApiException ex) {
           System.err.println("Server responded with an error: " + ex.getMessage());
       }
    }

    private static EpcisQueryParameters makeEpcisQueryParameters() {
        final EpcisQueryParameters queryParameters = new EpcisQueryParameters();
        queryParameters.parameters(Arrays.asList(makeParameterObject1()));
        return queryParameters;
    }

    private static ParameterObject makeParameterObject1() {
        final ParameterObject parameter = new ParameterObject();
        parameter.name("GE_event_time");
        parameter.value(formatDateTime(DateTime.now().minusMinutes(1)));
        return parameter;
    }

    private static String printEpcisEvents(final List<EpcisEvent> events) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < events.size(); i++) {
            sb.append(NEW_LINE).append(NEW_LINE).append(TAB);
            sb.append("EPCIS event ").append(i + 1).append(COLON);
            sb.append(printEpcisEvent(events.get(i)));
        }
        return sb.toString();
    }

    private static String printEpcisEvent(final EpcisEvent event) {
        final StringBuilder sb = new StringBuilder();
        sb.append(NEW_LINE).append(TAB).append("Id: ").append(event.getId());
        sb.append(NEW_LINE).append(TAB).append("Type: ").append(event.getType());
        sb.append(NEW_LINE).append(TAB).append("Event time: ").append(event.getEventTime());
        sb.append(NEW_LINE).append(TAB).append("Action: ").append(event.getAction());
        sb.append(NEW_LINE).append(TAB).append("Disposition: ").append(event.getDisposition());
        sb.append(NEW_LINE).append(TAB).append("Biz location: ").append(event.getBizLocation());
        sb.append(NEW_LINE).append(TAB).append("Biz step: ").append(event.getBizStep());
        sb.append(NEW_LINE).append(TAB).append("Read point: ").append(event.getReadPoint());
        sb.append(NEW_LINE).append(TAB).append("Epc list: ");
        sb.append(printEpcList(event));
        return sb.toString();
    }

    private static String printEpcList(final EpcisEvent event) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < event.getEpcList().size(); i++) {
            sb.append(NEW_LINE).append(DOUBLE_TAB);
            sb.append(i + 1).append(DOT).append(WHITESPACE).append(event.getEpcList().get(i));
        }
        return sb.toString();
    }

    private static String formatDateTime(DateTime dateTime) {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(dateTime.toDate());
    }
}
