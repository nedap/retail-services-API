package com.nedap.retail.messages.epcis.v1_1.cbv;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;

/**
 * Enumerates EPCIS ErrorDeclation Specifications follow CBV Standard v1.2 Draft.
 * As it is backwards compatible it's put in the v1_1 for ease of use.
 */
@ApiModel
public enum ErrorReasonId {

    DID_NOT_OCCUR("urn:epcglobal:cbv:er:did_not_occur"),
    INCORRECT_DATA("urn:epcglobal:cbv:er:incorrect_data");

    private String reason;

    ErrorReasonId(final String reason) {
        this.reason = reason;
    }

    @JsonValue
    public String getReason() {
        return reason;
    }

    @JsonCreator
    public static ErrorReasonId permissiveValueOf(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("errorreason missing");
        }
        for (final ErrorReasonId e : values()) {
            if (value.equalsIgnoreCase(e.getReason())) {
                return e;
            }
        }
        throw new IllegalArgumentException("unknown event type");
    }

    @Override
    public final String toString() {
        return this.name();
    }
}
