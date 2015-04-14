package com.nedap.retail.messages.users;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Authorisation implements Serializable {

    private static final long serialVersionUID = -961930253964396609L;

    private String role;
    private DateTime expiresAt;

    public Authorisation() {
    }

    public Authorisation(final String role) {
        this.role = role;
    }

    public Authorisation(final Role role) {
        this.role = role.name();
    }

    public String getRole() {
        return role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public static final String EXPIRES_AT = "expires_at";

    @JsonProperty(EXPIRES_AT)
    @org.codehaus.jackson.annotate.JsonProperty(EXPIRES_AT)
    public DateTime getExpiresAt() {
        return expiresAt;
    }

    @JsonProperty(EXPIRES_AT)
    @org.codehaus.jackson.annotate.JsonProperty(EXPIRES_AT)
    public void setExpiresAt(final DateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
