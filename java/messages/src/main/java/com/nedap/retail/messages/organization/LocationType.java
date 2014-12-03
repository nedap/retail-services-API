package com.nedap.retail.messages.organization;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LocationType {

    COUNTRY,
    GROUP,
    SITE,
    LOCATION,
    LOCATION_INTERNAL,
    LOCATION_EXTERNAL;

    @JsonCreator
    @org.codehaus.jackson.annotate.JsonCreator
    public static LocationType create(final String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        for (final LocationType v : values()) {
            if (value.equals(v.name())) {
                return v;
            }
        }
        return SITE;
    }
}
