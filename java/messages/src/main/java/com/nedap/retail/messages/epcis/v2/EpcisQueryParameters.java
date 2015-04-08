package com.nedap.retail.messages.epcis.v2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class EpcisQueryParameters {

    @JsonProperty("parameters")
    private List<ParameterObject> parameters;

    public EpcisQueryParameters() {
    }

    public List<ParameterObject> getParameters() {
        return parameters;
    }

    public void setParameters(final List<ParameterObject> parameters) {
        this.parameters = parameters;
    }
}
