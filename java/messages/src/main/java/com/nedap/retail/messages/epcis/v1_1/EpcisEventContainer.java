package com.nedap.retail.messages.epcis.v1_1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.annotations.SerializedName;

public class EpcisEventContainer {

    public static final String EVENTS = "events";
    @SerializedName(EVENTS)
    @JsonProperty(EVENTS)
    public List<EpcisEvent> events;

    public EpcisEventContainer() {
    }

    public EpcisEventContainer(final List<EpcisEvent> events) {
        this.events = new ArrayList<>(events);
    }

    public EpcisEventContainer(final EpcisEvent epcisEvent) {
        events = Arrays.asList(epcisEvent);
    }
}
