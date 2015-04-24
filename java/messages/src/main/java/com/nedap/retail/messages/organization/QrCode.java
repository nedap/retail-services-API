package com.nedap.retail.messages.organization;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class QrCode implements Serializable {

    private static final long serialVersionUID = -8306009109121831979L;

    public String qrcode;
    public DateTime expires;

    @JsonProperty("organization_id")
    @org.codehaus.jackson.annotate.JsonProperty("organization_id")
    public Long organizationId;

    public QrCode() {
    }

    public QrCode(final String qrcode, final DateTime expires, final Long organizationId) {
        this.qrcode = qrcode;
        this.expires = expires;
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "[" + qrcode + "|" + expires + "|" + organizationId + "]";
    }
}
