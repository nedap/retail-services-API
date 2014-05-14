package com.nedap.retail.messages.epcis.v1_1.elements;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonProperty;

public class DestinationElement {

    @JsonProperty("type")
    @SerializedName("type")
    public String type;

    @JsonProperty("destination")
    @SerializedName("destination")
    public String destination;

    public DestinationElement() {
    }

    public DestinationElement(String type, String destination) {
        this.type = type;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "DestinationElement: type=" + type + ",destination=" + destination;
    }
}
