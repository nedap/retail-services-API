package com.nedap.retail.messages.metrics;

import java.io.Serializable;

public class DetailedStatus implements Serializable {

    private int type;
    private Status status;

    public DetailedStatus() {
    }

    public DetailedStatus(int type, Status status) {
        this.type = type;
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "type=" + type + ", status=" + status;
    }
}
