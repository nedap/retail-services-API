package com.nedap.retail.messages.epcis.v1_1.elements;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonProperty;

public class SourceElement {

    @JsonProperty("type")
    @SerializedName("type")
    public String type;

    @JsonProperty("source")
    @SerializedName("source")
    public String source;

    public SourceElement() {
    }

    public SourceElement(String type, String source) {
        this.type = type;
        this.source = source;
    }

    @Override
    public String toString() {
        return "SourceElement: type=" + type + ",source=" + source;
    }
}
