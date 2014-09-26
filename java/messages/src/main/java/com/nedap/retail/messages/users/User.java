package com.nedap.retail.messages.users;

import java.io.Serializable;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * User Profile.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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

    @JsonProperty("display_name")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("display_name")
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

    @JsonProperty("sitegroup_id")
    public long getSitegroupId() {
        return sitegroupId;
    }

    @JsonProperty("sitegroup_id")
    public void setSitegroupId(final long sitegroupId) {
        this.sitegroupId = sitegroupId;
    }

    @JsonIgnore
    public boolean isAuthorized(final Role role) {
        return isAuthorized(role.name());
    }

    @JsonIgnore
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
