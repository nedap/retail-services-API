package com.nedap.retail.messages.users;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Authorisation implements Serializable {

    private String role;
    private DateTime expiresAt;

    public Authorisation() {
    }

    public Authorisation(String role) {
        this.role = role;
    }

    public Authorisation(Role role) {
        this.role = role.name();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("expires_at")
    public DateTime getExpiresAt() {
        return expiresAt;
    }

    @JsonProperty("expires_at")
    public void setExpiresAt(DateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
