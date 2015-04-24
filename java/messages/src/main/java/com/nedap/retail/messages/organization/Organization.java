package com.nedap.retail.messages.organization;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Organization implements Serializable {

    private static final long serialVersionUID = -646278093791631036L;

    public static enum Type {

        ORGANIZATION, BUSINESS_PARTNER
    }

    @JsonProperty("organization_id")
    @org.codehaus.jackson.annotate.JsonProperty("organization_id")
    public long id;

    public Type type;
    public String name;

    public Organization() {
    }

    public Organization(final long id, final Type type, final String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Organization{" + "id=" + id + ", type=" + type + ", name=" + name + '}';
    }
}
