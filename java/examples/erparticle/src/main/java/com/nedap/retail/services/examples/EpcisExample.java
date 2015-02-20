package com.nedap.retail.services.examples;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.epcis.v1_1.EpcisEvent;
import com.nedap.retail.messages.epcis.v1_1.ObjectEvent;
import com.nedap.retail.messages.epcis.v1_1.cbv.Action;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;
import com.nedap.retail.messages.epcis.v1_1.elements.QuantityElement;
import com.sun.jersey.api.client.UniformInterfaceException;

public class EpcisExample {

    public static void runExample(final Client client) {
        System.out.println("-------------");
        System.out.println("EPCIS API example");
        System.out.println("-------------");

        final List<EpcisEvent> events = createEvents();

        try {
            // capture
            System.out.println("------------- Capture epcis events");
            client.captureEpcisEvents(events);
            System.out.println("captured epcis events");

        } catch (final UniformInterfaceException e) {
            System.out.println("Server responded with an error:");
            System.out.println(e.getResponse().getEntity(String.class));
        }

        System.out.println("------------- Done");
    }

    private static List<EpcisEvent> createEvents() {
        final List<EpcisEvent> events = new ArrayList<EpcisEvent>();

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

        final List<String> epcList = new ArrayList<String>();
        epcList.add("urn:epc:id:sgtin:1234567.000246.0001");
        epcList.add("urn:epc:id:sgtin:1234567.000246.0002");
        epcList.add("urn:epc:id:sgtin:1234567.000246.0003");
        epcList.add("urn:epc:id:sgtin:1234567.000246.0004");

        final List<QuantityElement> quantityList = new ArrayList<QuantityElement>();
        quantityList.add(new QuantityElement("urn:epc:idpat:sgtin:1234567.000246.0001", 100));
        quantityList.add(new QuantityElement("urn:epc:idpat:sgtin:1234567.000246.0002", 50));
        quantityList.add(new QuantityElement("urn:epc:idpat:sgtin:1234567.000246.0003", 80));
        quantityList.add(new QuantityElement("urn:epc:idpat:sgtin:1234567.000246.0004", 30));

        final EpcisEvent event1 = new ObjectEvent(id, eventTime, recordTime, eventTimeZoneOffset, action, bizLocation,
                readPoint, disposition, epcList, quantityList);

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

        final List<String> epcList = new ArrayList<String>();
        epcList.add("urn:epc:id:sgtin:1234567.000246.0005");
        epcList.add("urn:epc:id:sgtin:1234567.000246.0006");
        epcList.add("urn:epc:id:sgtin:1234567.000246.0007");
        epcList.add("urn:epc:id:sgtin:1234567.000246.0008");

        final List<QuantityElement> quantityList = new ArrayList<QuantityElement>();
        quantityList.add(new QuantityElement("urn:epc:idpat:sgtin:1234567.000246.0005", 50));
        quantityList.add(new QuantityElement("urn:epc:idpat:sgtin:1234567.000246.0006", 30));
        quantityList.add(new QuantityElement("urn:epc:idpat:sgtin:1234567.000246.0007", 100));
        quantityList.add(new QuantityElement("urn:epc:idpat:sgtin:1234567.000246.0008", 20));

        final EpcisEvent event2 = new ObjectEvent(id, eventTime, recordTime, eventTimeZoneOffset, action, bizLocation,
                readPoint, disposition, epcList, quantityList);

        return event2;
    }
}