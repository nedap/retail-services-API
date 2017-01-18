package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.PrintHelper.DOT;
import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;
import static com.nedap.retail.services.examples.PrintHelper.TAB;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.nedap.retail.client.model.EpcisEvent;
import com.nedap.retail.client.model.EpcisEventContainer;
import com.nedap.retail.client.model.ObjectEvent;
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
        return new ObjectEvent()
                .epcList(makeEpcList1())
                .id(UUID.randomUUID().toString())
                .type(EpcisEvent.TypeEnum.OBJECT_EVENT)
                .eventTime(DateTime.now())
                .action(Action.OBSERVE.action())
                .disposition(Disposition.SELLABLE_ACCESSIBLE.disposition())
                .bizLocation(locationId)
                .bizStep(Bizstep.CYCLE_COUNTING.bizStep())
                .readPoint(locationId);
    }

    private static EpcisEvent createEpcisEvent2(final String locationId) {
        return new ObjectEvent()
                .epcList(makeEpcList2())
                .type(EpcisEvent.TypeEnum.OBJECT_EVENT)
                .id(UUID.randomUUID().toString())
                .eventTime(DateTime.now())
                .action(Action.OBSERVE.action())
                .disposition(Disposition.SELLABLE_ACCESSIBLE.disposition())
                .bizLocation(locationId)
                .bizStep(Bizstep.CYCLE_COUNTING.bizStep())
                .readPoint(locationId);
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
        for (int i = 0; i < epcisEventContainer.getEvents().size(); i++) {
            sb.append(NEW_LINE).append(TAB);
            sb.append(i + 1).append(DOT);
            sb.append(epcisEventContainer.getEvents().get(i).getId());
        }
        return sb.toString();
    }
}
