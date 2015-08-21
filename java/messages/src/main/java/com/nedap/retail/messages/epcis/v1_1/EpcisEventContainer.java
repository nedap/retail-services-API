package com.nedap.retail.messages.epcis.v1_1;

import java.util.ArrayList;
import java.util.List;

public class EpcisEventContainer {

    public List<EpcisEvent> events;

    public EpcisEventContainer() {
    }

    public EpcisEventContainer(final List<EpcisEvent> events) {
        this.events = events;
    }

    public EpcisEventContainer(final EpcisEvent epcisEvent) {
        events = new ArrayList<>();
        events.add(epcisEvent);
    }
}
