package com.nedap.retail.messages.stock;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Stock extends StockSummary {

    @JsonProperty("quantity_list")
    @org.codehaus.jackson.annotate.JsonProperty("quantity_list")
    private List<GtinQuantity> quantityList;

    public Stock() {
        this.quantityList = new ArrayList<>();
    }

    public Stock(final String location, final DateTime eventTime, final String externRef,
            final List<GtinQuantity> quantityList) {
        super(null, location, eventTime, externRef, null);
        this.quantityList = quantityList;
    }

    public Stock(final StockSummary stockSummary, final List<GtinQuantity> quantityList) {
        super(stockSummary.getId(), stockSummary.getLocation(), stockSummary.getEventTime(), stockSummary
                .getExternRef(), stockSummary.getStatus().toString(), stockSummary.getQuantity(), stockSummary
                .getGtinQuantity());
        this.quantityList = quantityList;
    }

    @JsonIgnore
    @org.codehaus.jackson.annotate.JsonIgnore
    public List<GtinQuantity> getQuantityList() {
        return quantityList;
    }

    public void setQuantityList(final List<GtinQuantity> quantityList) {
        this.quantityList = quantityList;
    }

    public void addQuantity(final String gtin14, final int quantity) {
        quantityList.add(new GtinQuantity(gtin14, quantity));
    }

    @Override
    public String toString() {
        final String result = "Stock {location=" + getLocation() + "; eventTime=" + getEventTime() + "; quantity(gtins)="
                + quantityList.size() + "; quantity(total)=";
        long total = 0;
        for (final GtinQuantity q : quantityList) {
            total += q.getQuantity();
        }
        return result + total + "}";
    }
}
