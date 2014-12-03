package com.nedap.retail.messages.users;

import java.io.Serializable;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User Profile.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User implements Serializable {

    private String id;
    private String displayName;
    private String gender;
    private String username;
    private String language;
    private Set<Authorisation> authorisations;
    private long sitegroupId;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public static final String DISPLAY_NAME = "display_name";

    @JsonProperty(DISPLAY_NAME)
    @org.codehaus.jackson.annotate.JsonProperty(DISPLAY_NAME)
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty(DISPLAY_NAME)
    @org.codehaus.jackson.annotate.JsonProperty(DISPLAY_NAME)
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public Set<Authorisation> getAuthorisations() {
        return authorisations;
    }

    public void setAuthorisations(final Set<Authorisation> authorisations) {
        this.authorisations = authorisations;
    }

    public static final String SITEGROUP_ID = "sitegroup_id";

    @JsonProperty(SITEGROUP_ID)
    @org.codehaus.jackson.annotate.JsonProperty(SITEGROUP_ID)
    public long getSitegroupId() {
        return sitegroupId;
    }

    @JsonProperty(SITEGROUP_ID)
    @org.codehaus.jackson.annotate.JsonProperty(SITEGROUP_ID)
    public void setSitegroupId(final long sitegroupId) {
        this.sitegroupId = sitegroupId;
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
                if (authorisation.getRole().equalsIgnoreCase(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
