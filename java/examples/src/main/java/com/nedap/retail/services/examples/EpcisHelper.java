package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.PrintHelper.DOT;
import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;
import static com.nedap.retail.services.examples.PrintHelper.ORGANIZATION_PREFIX;
import static com.nedap.retail.services.examples.PrintHelper.TAB;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.nedap.retail.messages.epcis.v1_1.EpcisEvent;
import com.nedap.retail.messages.epcis.v1_1.EpcisEventContainer;
import com.nedap.retail.messages.epcis.v1_1.ObjectEvent;
import com.nedap.retail.messages.epcis.v1_1.cbv.Action;
import com.nedap.retail.messages.epcis.v1_1.cbv.Bizstep;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;

public class EpcisHelper {

    private EpcisHelper() {
    }

    public static List<EpcisEvent> createEvents(final String locationId) {
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

    /**
     * Calculates consistent (hash) ID based on organization prefix and counter value of epc events.
     */
    public static UUID generateEpcisEventID(final long organizationPrefix, final int epcisEventCounter) {
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

    private static List<String> makeEpcList2() {
        final List<String> epcList = new ArrayList<>();
        epcList.add("urn:epc:id:sgtin:2011200.000111.2");
        epcList.add("urn:epc:id:sgtin:2011200.090002.1");
        epcList.add("urn:epc:id:sgtin:2011200.000201.1");
        epcList.add("urn:epc:id:sgtin:2011200.000202.1");
        epcList.add("urn:epc:id:sgtin:2011200.000203.1");
        return epcList;
    }

    public static String printCaptureEpcisEvents(final EpcisEventContainer epcisEventContainer) {
        final StringBuilder sb = new StringBuilder("Captured EPCIS object events with ids:");
        for (int i = 0; i < epcisEventContainer.events.size(); i++) {
            sb.append(NEW_LINE).append(TAB);
            sb.append(i + 1).append(DOT);
            sb.append(epcisEventContainer.events.get(i).id);
        }
        return sb.toString();
    }
}
