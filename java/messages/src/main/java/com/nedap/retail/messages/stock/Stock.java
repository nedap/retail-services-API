package com.nedap.retail.messages.stock;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class Stock extends StockSummary {

    @org.codehaus.jackson.annotate.JsonProperty("quantity_list")
    public List<GtinQuantity> quantityList;

    public Stock() {
        this.quantityList = new ArrayList<>();
    }

    public Stock(final String location, final DateTime eventTime, final String externRef,
            final List<GtinQuantity> quantityList) {
        super(null, location, eventTime, externRef, null, false);
        this.quantityList = quantityList;
    }

    public Stock(final StockSummary stockSummary, final List<GtinQuantity> quantityList) {
        super(stockSummary.id, stockSummary.location, stockSummary.eventTime, stockSummary.externRef,
                stockSummary.status.toString(), stockSummary.quantity, stockSummary.gtinQuantity, stockSummary.inUse);
        this.quantityList = quantityList;
    }

    public void addQuantity(final String gtin14, final int quantity) {
        quantityList.add(new GtinQuantity(gtin14, quantity));
    }

    @Override
    public String toString() {
        final String result = "Stock {location=" + location + "; eventTime=" + eventTime + "; quantity(gtins)="
                + quantityList.size() + "; quantity(total)=";
        long total = 0;
        for (final GtinQuantity q : quantityList) {
            total += q.quantity;
        }
        return result + total + "}";
    }
}
