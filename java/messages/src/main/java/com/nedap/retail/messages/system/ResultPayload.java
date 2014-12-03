package com.nedap.retail.messages.system;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nedap.retail.messages.InvalidMessage;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResultPayload {

    private String result;
    private String reason;

    public ResultPayload() {
    }

    public ResultPayload(final String result) {
        this.result = result;
    }

    public ResultPayload(final String result, final InvalidMessage ex) {
        this(result, ex.getMessage());
    }

    public ResultPayload(final String result, final String reason) {
        this.result = result;
        this.reason = reason;
    }

    public String getResult() {
        return result;
    }

    public void setResult(final String result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }
}
