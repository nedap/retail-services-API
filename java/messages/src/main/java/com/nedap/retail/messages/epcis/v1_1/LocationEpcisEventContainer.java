package com.nedap.retail.messages.epcis.v1_1;

import java.util.List;

public class LocationEpcisEventContainer extends EpcisEventContainer {

    public String location;

    public LocationEpcisEventContainer() {
    }

    public LocationEpcisEventContainer(final List<EpcisEvent> events, final String location) {
        super(events);
        this.location = location;
    }
}
