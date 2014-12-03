package com.nedap.retail.messages.organization;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Organization implements Serializable {

    public static enum Type {

        ORGANIZATION, BUSINESS_PARTNER
    }

    @JsonProperty("organization_id")
    @org.codehaus.jackson.annotate.JsonProperty("organization_id")
    private long id;
    private Type type;
    private String name;

    public Organization() {
    }

    public Organization(final long id, final Type type, final String name) {
        this.id = id;
        this.type = type;
        this.name = name;
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Organization{" + "id=" + id + ", type=" + type + ", name=" + name + '}';
    }
}
