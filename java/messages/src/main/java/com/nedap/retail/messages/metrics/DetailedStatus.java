package com.nedap.retail.messages.metrics;

import java.io.Serializable;

public class DetailedStatus implements Serializable {

    private static final long serialVersionUID = -6932705271595999378L;

    public int type;
    public Status status;

    public DetailedStatus() {
    }

    public DetailedStatus(final int type, final Status status) {
        this.type = type;
        this.status = status;
    }

    @Override
    public String toString() {
        return "type=" + type + ", status=" + status;
    }
}
