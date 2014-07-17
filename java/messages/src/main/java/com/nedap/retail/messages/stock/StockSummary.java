package com.nedap.retail.messages.stock;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
// to prevent writing of null properties
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
    @JsonProperty("quantity")
    private Integer quantity;
    @JsonProperty("gtin_quantity")
    private Integer gtinQuantity;

    public StockSummary() {
    }

    public StockSummary(final String id, final String location, final String eventTime, final String externRef,
            final String status) {
        this.id = id;
        this.location = location;
        this.eventTime = eventTime;
        this.externRef = externRef;
        if (status != null) {
            this.status = Status.valueOf(status);
        }
    }

    public StockSummary(final String id, final String location, final String eventTime, final String externRef,
            final String status, final Integer quantity, final Integer gtinQuantity) {
        this(id, location, eventTime, externRef, status);
        this.quantity = quantity;
        this.gtinQuantity = gtinQuantity;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(final String eventTime) {
        this.eventTime = eventTime;
    }

    public String getExternRef() {
        return externRef;
    }

    public void setExternRef(final String externRef) {
        this.externRef = externRef;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getGtinQuantity() {
        return gtinQuantity;
    }

    public void setGtinQuantity(final Integer gtinQuantity) {
        this.gtinQuantity = gtinQuantity;
    }

    @Override
    public String toString() {
        return "StockSummary{" + "id=" + id + ", location=" + location + ", eventTime=" + eventTime + ", externRef="
                + externRef + ", status=" + status + ", quantity=" + quantity + ", gtinQuantity=" + gtinQuantity + '}';
    }
}
