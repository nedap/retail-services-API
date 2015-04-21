package com.nedap.retail.messages.epcis.v2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class EpcisQueryParameters {

    @JsonProperty("parameters")
    public List<ParameterObject> parameters;

    public EpcisQueryParameters() {
    }
}
