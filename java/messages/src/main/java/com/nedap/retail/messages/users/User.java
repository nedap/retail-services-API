package com.nedap.retail.messages.users;

import java.io.Serializable;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * User Profile.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID = -8964914993773388184L;

    public String id;
    public String displayName;
    public String gender;
    public String username;
    public String language;
    public Set<Authorisation> authorisations;
    public long sitegroupId;

    public User() {
    }

    @JsonIgnore
    @org.codehaus.jackson.annotate.JsonIgnore
    public boolean isAuthorized(final Role role) {
        return isAuthorized(role.name());
    }

    @JsonIgnore
    @org.codehaus.jackson.annotate.JsonIgnore
    public boolean isAuthorized(final String roleName) {
        if (authorisations != null) {
            for (final Authorisation authorisation : authorisations) {
                if (authorisation.role.equalsIgnoreCase(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
