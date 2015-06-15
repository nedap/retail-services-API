package com.nedap.retail.messages.stock;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StockSummary {

    public enum Status {
        ACCEPTED, VALIDATING
    }

    public String id;
    public String location;

    @JsonProperty("event_time")
    // event time is time of last event (end time), will not be changed because of backward compatibility
    public DateTime eventTime;

    @JsonProperty("start_time")
    public DateTime startTime;

    @JsonProperty("extern_ref")
    public String externRef;

    @JsonProperty("status")
    public Status status;

    @JsonProperty("quantity")
    public Integer quantity;

    @JsonProperty("excluded_quantity")
    public Integer excludedQuantity;

    @JsonProperty("gtin_quantity")
    public Integer gtinQuantity;

    @JsonProperty("excluded_gtin_quantity")
    public Integer excludedGtinQuantity;

    @JsonProperty("in_use")
    public boolean inUse;

    @JsonProperty("client_ids")
    public Set<String> clientIds;

    public StockSummary() {
        this.clientIds = new HashSet<>();
    }

    public StockSummary(final String id, final String location, final DateTime eventTime, final String externRef,
            final String status, final boolean inUse, final Set<String> clientIds) {
        this.id = id;
        this.location = location;
        this.eventTime = eventTime;
        this.externRef = externRef;
        if (status != null) {
            this.status = Status.valueOf(status);
        }
        this.inUse = inUse;
        this.clientIds = clientIds;
    }

    public StockSummary(final String id, final String location, final DateTime eventTime, final String externRef,
            final String status, final Integer quantity, final Integer excludedQuantity, final Integer gtinQuantity,
            final Integer excludedGtinQuantity, final boolean inUse, final Set<String> clientIds) {
        this(id, location, eventTime, externRef, status, inUse, clientIds);
        this.quantity = quantity;
        this.excludedQuantity = excludedQuantity;
        this.gtinQuantity = gtinQuantity;
        this.excludedGtinQuantity = excludedGtinQuantity;
    }

    @Override
    public String toString() {
        return "StockSummary {" + "id=" + id + ", location=" + location + ", eventTime="
                + eventTime.toString("dd/MM/yyyy HH:mm:ss") + ", externRef=" + externRef + ", status=" + status
                + ", quantity=" + quantity + ", excludedQuantity=" + excludedQuantity + ", gtinQuantity="
                + gtinQuantity + ", excludedGtinQuantity=" + excludedGtinQuantity + ", clientIds="
                + StringUtils.join(clientIds, ',') + '}';
    }
}
