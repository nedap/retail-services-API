package com.nedap.retail.messages.epc.v2.stock;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NotOnShelfSummary {

    public DateTime rfidStockTime;
    public Integer notOnShelfGtinQuantity;
    public Integer rfidGtinQuantity;
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
