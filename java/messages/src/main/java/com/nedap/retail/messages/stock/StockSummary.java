package com.nedap.retail.messages.stock;

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
    public DateTime eventTime;

    @JsonProperty("extern_ref")
    public String externRef;

    public Status status;
    public Integer quantity;

    @JsonProperty("gtin_quantity")
    public Integer gtinQuantity;

    @JsonProperty("in_use")
    public boolean inUse;

    @JsonProperty("client_ids")
    public Set<String> clientIds;

    public StockSummary() {
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
            final String status, final Integer quantity, final Integer gtinQuantity, final boolean inUse,
            final Set<String> clientIds) {
        this(id, location, eventTime, externRef, status, inUse, clientIds);
        this.quantity = quantity;
        this.gtinQuantity = gtinQuantity;
    }

    @Override
    public String toString() {
        return "StockSummary {" + "id=" + id + ", location=" + location + ", eventTime="
                + eventTime.toString("dd/MM/yyyy HH:mm:ss") + ", externRef=" + externRef + ", status=" + status
                + ", quantity=" + quantity + ", gtinQuantity=" + gtinQuantity + ", clientIds="
                + StringUtils.join(clientIds, ',') + '}';
    }
}
