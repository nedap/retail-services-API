package com.nedap.retail.messages.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 *
 */
public class Stock extends StockSummary {

    @JsonIgnore
    private HashMap<String,Integer> quantities = new HashMap<String,Integer>();
    
    @JsonProperty("quantity_list")
    private List<GtinQuantity> quantityList;

    public Stock() {
        this.quantityList = new ArrayList<GtinQuantity>();
    }

    public Stock(final String location, final String eventTime, final String externRef, final List<GtinQuantity> quantityList) {
        super(null, location, eventTime, externRef, null);
        this.quantityList = quantityList;
    }
    
    public Stock(final StockSummary stockSummary, final List<GtinQuantity> quantityList) {
        super(stockSummary.getId(), stockSummary.getLocation(), stockSummary.getEventTime(), stockSummary.getExternRef(), stockSummary.getStatus().toString());
        this.quantityList = quantityList;
    }

    public List<GtinQuantity> getQuantityList() {
        quantityList.clear();
        for (String key : quantities.keySet()) {
            quantityList.add(new GtinQuantity(key, quantities.get(key)));
        }
        return quantityList;
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
        // Check if gtin14 already existed. If so, retrieve it and update its quantity. 
        // A gtin14 might be listed numerous times, for example when each gtin has only 1 quantity per row.
        Integer gtinQuantity;
        if (quantities.containsKey(gtin14)) {
            gtinQuantity = quantities.get(gtin14);
            gtinQuantity += quantity;
        } else {
            gtinQuantity = new Integer(quantity);
        }
        quantities.put(gtin14, gtinQuantity);
    }
}
