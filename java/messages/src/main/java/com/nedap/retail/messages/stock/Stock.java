package com.nedap.retail.messages.stock;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 */
public class Stock extends StockSummary {

    @JsonProperty("quantity_list")
    private List<GtinQuantity> quantityList;

    public Stock() {
        this.quantityList = new ArrayList<>();
    }

    public Stock(final String location, final String eventTime, final String externRef,
            final List<GtinQuantity> quantityList) {
        super(null, location, eventTime, externRef, null);
        this.quantityList = quantityList;
    }

    public Stock(final StockSummary stockSummary, final List<GtinQuantity> quantityList) {
        super(stockSummary.getId(), stockSummary.getLocation(), stockSummary.getEventTime(), stockSummary.getExternRef(),
                stockSummary.getStatus().toString());
        this.quantityList = quantityList;
    }

    public List<GtinQuantity> getQuantityList() {
        return quantityList;
    }

    public void setQuantityList(List<GtinQuantity> quantityList) {
        this.quantityList = quantityList;
    }

    @Override
    public String toString() {
        String result = "[location=" + getLocation() + "; eventTime=" + getEventTime() + "; quantity(gtins)=" + quantityList.size() + "; quantity(total)=";
        long total = 0;
        for (GtinQuantity q : quantityList) {
            total += q.getQuantity();
        }
        return result + total + "]";
    }

    public void addQuantity(String gtin14, int quantity) {
        quantityList.add(new GtinQuantity(gtin14, quantity));
    }
}
