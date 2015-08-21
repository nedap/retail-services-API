package com.nedap.retail.messages.epc.v2.approved_difference_list;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ExportStatus {

    NOT_EXPORTED, EXPORTED, ERROR;

    /**
     * Provides similar functionality to valueOf(..).
     *
     * @param value String value of requested export status
     * @return ExportStatus enum value for provided String
     */
    @JsonCreator
    @org.codehaus.jackson.annotate.JsonCreator
    public static ExportStatus permissiveValueOf(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("Export status missing");
        }
        for (final ExportStatus v : values()) {
            if (value.equalsIgnoreCase(v.name())) {
                return v;
            }
        }
        throw new IllegalArgumentException("unknown export status");
    }

    @Override
    public final String toString() {
        return this.name();
    }
}
