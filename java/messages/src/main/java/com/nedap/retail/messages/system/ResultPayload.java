package com.nedap.retail.messages.system;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.nedap.retail.messages.InvalidMessage;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResultPayload {

    public String result;
    public String reason;

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
}
