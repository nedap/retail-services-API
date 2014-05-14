package com.nedap.retail.messages.stock;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //  to prevent writing of null properties
public class StockSummary {

    public enum Status {

        ACCEPTED, VALIDATING
    }

    private String id;
    @JsonProperty("location")
    private String location;
    @JsonProperty("event_time")
    private String eventTime;
    @JsonProperty("extern_ref")
    private String externRef;
    private Status status;

    public StockSummary() {
    }

    public StockSummary(String id, String location, String eventTime, String externRef, String status) {
        this.id = id;
        this.location = location;
        this.eventTime = eventTime;
        this.externRef = externRef;
        if (status != null) {
            this.status = Status.valueOf(status);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getExternRef() {
        return externRef;
    }

    public void setExternRef(String externRef) {
        this.externRef = externRef;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StockSummary{" + "id=" + id + ", location=" + location + ", eventTime=" + eventTime + ", externRef=" + externRef + ", status=" + status + '}';
    }
}
