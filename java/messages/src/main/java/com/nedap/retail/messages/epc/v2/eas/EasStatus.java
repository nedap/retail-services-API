package com.nedap.retail.messages.epc.v2.eas;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EasStatus {

    private static final String EPC_HEX = "epc_hex";
    private static final String EPC_URI = "epc_uri";

    @JsonProperty(EPC_HEX)
    @org.codehaus.jackson.annotate.JsonProperty(EPC_HEX)
    public String epcHex;

    @JsonProperty(EPC_URI)
    @org.codehaus.jackson.annotate.JsonProperty(EPC_URI)
    public String epcUri;

    public EasStatus() {
    }

    public EasStatus(final String epcHex, final String epcUri) {
        this.epcHex = epcHex;
        this.epcUri = epcUri;
    }
}
