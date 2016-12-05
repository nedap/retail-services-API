package com.nedap.retail.messages.organization;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Organization implements Serializable {

    private static final long serialVersionUID = -646278093791631036L;

    public enum Type {

        ORGANIZATION, BUSINESS_PARTNER
    }

    @JsonProperty("organization_id")
    public long organizationId;

    public Type type;
    public String name;

    public Organization() {
    }

    public Organization(final long id, final Type type, final String name) {
        this.organizationId = id;
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Organization{" + "id=" + organizationId + ", type=" + type + ", name=" + name + '}';
    }
}
