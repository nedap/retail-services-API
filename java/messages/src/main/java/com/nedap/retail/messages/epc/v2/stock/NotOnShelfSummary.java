package com.nedap.retail.messages.epc.v2.stock;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NotOnShelfSummary {

    @JsonProperty("rfid_stock_time")
    public DateTime rfidStockTime;

    @JsonProperty("not_on_shelf_gtin_quantity")
    public Integer notOnShelfGtinQuantity;

    @JsonProperty("rfid_gtin_quantity")
    public Integer rfidGtinQuantity;

    @JsonProperty("not_on_shelf_percentage")
    public Double notOnShelfPercentage;

    public NotOnShelfSummary() {
    }

    public NotOnShelfSummary(final DateTime rfidStockTime, final Integer notOnShelfGtinQuantity,
            final Integer rfidGtinQuantity, final Double notOnShelfPercentage) {
        this.rfidStockTime = rfidStockTime;
        this.notOnShelfGtinQuantity = notOnShelfGtinQuantity;
        this.rfidGtinQuantity = rfidGtinQuantity;
        this.notOnShelfPercentage = notOnShelfPercentage;
    }

    @JsonIgnore
    public NotOnShelfSummary getSummary() {
        return new NotOnShelfSummary(this.rfidStockTime, this.notOnShelfGtinQuantity, this.rfidGtinQuantity,
                this.notOnShelfPercentage);
    }
}
