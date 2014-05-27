package com.nedap.retail.messages.organization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Response from endpoint:
 * GET https://api.nedapretail.com/organization/v1/retrieve
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Organizations implements Serializable {

    private Organization own;
    private List<Organization> linked;

    public Organizations() {
    }

    public Organizations(Organization own) {
        this(own, new ArrayList<Organization>());
    }

    public Organizations(Organization own, List<Organization> linked) {
        this.own = own;
        this.linked = linked;
    }

    public Organization getOwn() {
        return own;
    }

    public void setOwn(Organization own) {
        this.own = own;
    }

    public List<Organization> getLinked() {
        return linked;
    }

    public void setLinked(List<Organization> linked) {
        this.linked = linked;
    }

    public Organization find(final long id) {

        if (own == null) {
            return null;
        }
        if (own.getId() == id) {
            return own;
        }

        for (final Organization organization : linked) {
            if (organization.getId() == id) {
                return organization;
            }
        }
        return null;
    }
}
