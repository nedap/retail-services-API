package com.nedap.retail.messages.organization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Response from endpoint: GET https://api.nedapretail.com/organization/v1/retrieve
 */
@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Organizations implements Serializable {

    private static final long serialVersionUID = 2904138281032930023L;

    public Organization own;
    public List<Organization> linked;

    public Organizations() {
    }

    public Organizations(final Organization own) {
        this(own, new ArrayList<Organization>());
    }

    public Organizations(final Organization own, final List<Organization> linked) {
        this.own = own;
        this.linked = linked;
    }

    public Organization find(final long id) {

        if (own == null) {
            return null;
        }
        if (own.id == id) {
            return own;
        }

        for (final Organization organization : linked) {
            if (organization.id == id) {
                return organization;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Organizations{" + "own=" + own + ", linked=" + linked + '}';
    }
}
