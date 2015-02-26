package com.nedap.retail.services.examples;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.epcis.v1_1.EpcisEvent;
import com.nedap.retail.messages.epcis.v1_1.EpcisEventContainer;
import com.nedap.retail.messages.epcis.v1_1.ObjectEvent;
import com.nedap.retail.messages.epcis.v1_1.cbv.Action;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;
import com.sun.jersey.api.client.UniformInterfaceException;

public class EpcisExample {

    public static void runExample(final Client client) {
        System.out.println("*** EPCIS API example ***");

        final EpcisEventContainer epcisEventsList = new EpcisEventContainer();
        epcisEventsList.events = createEvents();

        try {
            // capture
            System.out.println("Capturing epcis events...");
            client.captureEpcisEvents(epcisEventsList);
            System.out.println("--- Done ---");

        } catch (final UniformInterfaceException e) {
            System.err.println("Server responded with an error: " + e.getResponse().getEntity(String.class));
        }
    }

    private static List<EpcisEvent> createEvents() {
        final List<EpcisEvent> events = new ArrayList<>();
        events.add(createEpcisEvent1());
        events.add(createEpcisEvent2());
        return events;
    }

    private static EpcisEvent createEpcisEvent1() {
        final String id = "12f03260-c56f-11e3-9c1a-0800200c9a66";
        final DateTime eventTime = DateTime.parse("2014-02-01T12:00:00.000+01:00");
        final DateTime recordTime = DateTime.now();
        final String eventTimeZoneOffset = "+01:00";
        final Action action = Action.OBSERVE;
        final String bizLocation = "urn:epc:id:sgln:012345.67890.001";
        final String readPoint = "urn:epc:id:sgln:012345.67890.001";
        final Disposition disposition = Disposition.SELLABLE_ACCESSIBLE;

        final List<String> epcList = new ArrayList<>();
        epcList.add("urn:epc:id:sgtin:061414.12346.0001");
        epcList.add("urn:epc:id:sgtin:061414.12346.0002");
        epcList.add("urn:epc:id:sgtin:061414.12346.0003");
        epcList.add("urn:epc:id:sgtin:061414.12346.0004");

        final EpcisEvent event1 = new ObjectEvent(id, eventTime, recordTime, eventTimeZoneOffset, action, bizLocation,
                readPoint, disposition, epcList, null);

        return event1;
    }

    private static EpcisEvent createEpcisEvent2() {
        final String id = "12f03260-c56f-11e3-9c1a-0800200c9a67";
        final DateTime eventTime = DateTime.parse("2014-02-01T12:01:00.000+01:00");
        final DateTime recordTime = DateTime.now();
        final String eventTimeZoneOffset = "+01:00";
        final Action action = Action.OBSERVE;
        final String bizLocation = "urn:epc:id:sgln:012345.67890.002";
        final String readPoint = "urn:epc:id:sgln:012345.67890.001";
        final Disposition disposition = Disposition.SELLABLE_ACCESSIBLE;

        final List<String> epcList = new ArrayList<>();
        epcList.add("urn:epc:id:sgtin:061414.12346.0005");
        epcList.add("urn:epc:id:sgtin:061414.12346.0006");
        epcList.add("urn:epc:id:sgtin:061414.12346.0007");
        epcList.add("urn:epc:id:sgtin:061414.12346.0008");

        final EpcisEvent event2 = new ObjectEvent(id, eventTime, recordTime, eventTimeZoneOffset, action, bizLocation,
                readPoint, disposition, epcList, null);

        return event2;
    }
}