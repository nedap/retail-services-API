package com.nedap.retail.messages.organization;

import org.codehaus.jackson.annotate.JsonCreator;

public enum LocationType {

    COUNTRY,
    GROUP,
    SITE,
    LOCATION,
    LOCATION_INTERNAL,
    LOCATION_EXTERNAL;

    @JsonCreator
    public static LocationType create(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        for (LocationType v : values()) {
            if (value.equals(v.name())) {
                return v;
            }
        }
        return SITE;
    }
}
