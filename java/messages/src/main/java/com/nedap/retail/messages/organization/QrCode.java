package com.nedap.retail.messages.organization;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class QrCode implements Serializable {

    private String qrcode;
    private DateTime expires;
    @JsonProperty("organization_id")
    private Long organizationId;

    public QrCode() {
    }

    public QrCode(final String qrcode, final DateTime expires, final Long organizationId) {
        this.qrcode = qrcode;
        this.expires = expires;
        this.organizationId = organizationId;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public DateTime getExpires() {
        return expires;
    }

    public void setExpires(DateTime expires) {
        this.expires = expires;
    }

    @JsonIgnore
    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public String toString() {
        return "[" + qrcode + "|" + expires + "|" + organizationId + "]";
    }

}
