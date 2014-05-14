package com.nedap.retail.messages.epcis.v1_1;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

public class EpcisEventContainer {

    @JsonProperty("events")
    @SerializedName("events")
    public List<EpcisEvent> events;

    public EpcisEventContainer() {
    }

    public EpcisEventContainer(List<EpcisEvent> events) {
        this.events = events;
    }

    public EpcisEventContainer(EpcisEvent epcisEvent) {
        events = new ArrayList<>();
        events.add(epcisEvent);
    }
}
