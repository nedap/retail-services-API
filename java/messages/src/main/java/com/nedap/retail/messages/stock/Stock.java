package com.nedap.retail.messages.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

public class Stock extends StockSummary {

    @JsonProperty("quantity_list")
    public List<GtinQuantity> quantityList;

    public Stock() {
        this.quantityList = new ArrayList<>();
    }

    public Stock(final String location, final DateTime eventTime, final String externRef, final Set<String> clientIds,
            final List<GtinQuantity> quantityList) {
        super(null, location, eventTime, externRef, null, false, clientIds);
        this.quantityList = quantityList;
    }

    public Stock(final StockSummary stockSummary, final List<GtinQuantity> quantityList) {
        super(stockSummary.id, stockSummary.location, stockSummary.eventTime, stockSummary.externRef,
                stockSummary.status.toString(), stockSummary.quantity, stockSummary.excludedQuantity,
                stockSummary.gtinQuantity, stockSummary.excludedGtinQuantity, stockSummary.inUse,
                stockSummary.clientIds);
        this.startTime = stockSummary.startTime;
        this.quantityList = quantityList;
    }

    public void addQuantity(final String gtin14, final int quantity) {
        quantityList.add(new GtinQuantity(gtin14, quantity));
    }

    @Override
    public String toString() {
        final String result = "Stock {location=" + location + "; eventTime=" + eventTime + "; clientIds="
                + StringUtils.join(clientIds, ',') + "; quantity(gtins)=" + gtinQuantity
                + "; quantity(excluded gtins)=" + excludedGtinQuantity + "; quantity(total)=";
        long total = 0;
        for (final GtinQuantity q : quantityList) {
            total += q.quantity;
        }
        return result + total + "}";
    }
}
