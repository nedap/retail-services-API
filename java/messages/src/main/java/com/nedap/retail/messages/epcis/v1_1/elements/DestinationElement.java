package com.nedap.retail.messages.epcis.v1_1.elements;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.annotations.SerializedName;

public class DestinationElement {

    @JsonProperty("type")
    @SerializedName("type")
    public String type;

    @JsonProperty("destination")
    @SerializedName("destination")
    public String destination;

    public DestinationElement() {
    }

    public DestinationElement(final String type, final String destination) {
        this.type = type;
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "DestinationElement: type=" + type + ",destination=" + destination;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (destination == null ? 0 : destination.hashCode());
        result = prime * result + (type == null ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DestinationElement other = (DestinationElement) obj;
        if (destination == null) {
            if (other.destination != null) {
                return false;
            }
        } else if (!destination.equals(other.destination)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
}
