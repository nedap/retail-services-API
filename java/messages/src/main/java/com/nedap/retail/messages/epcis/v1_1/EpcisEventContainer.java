package com.nedap.retail.messages.epcis.v1_1;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

public class EpcisEventContainer {

    public static final String EVENTS = "events";
    @JsonProperty(EVENTS)
    @SerializedName(EVENTS)
    @org.codehaus.jackson.annotate.JsonProperty(EVENTS)
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
