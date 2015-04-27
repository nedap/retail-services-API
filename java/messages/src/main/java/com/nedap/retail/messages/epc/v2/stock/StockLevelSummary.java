package com.nedap.retail.messages.epc.v2.stock;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StockLevelSummary {

    public DateTime generated;

    @org.codehaus.jackson.annotate.JsonProperty("rfid_stock_time")
    public DateTime rfidStockTime;

    @org.codehaus.jackson.annotate.JsonProperty("stock_rooms_quantity")
    public Integer stockRoomsQuantity;

    @org.codehaus.jackson.annotate.JsonProperty("sales_floors_quantity")
    public Integer salesFloorsQuantity;

    @org.codehaus.jackson.annotate.JsonProperty("store_quantity")
    public Integer storeQuantity;

    @org.codehaus.jackson.annotate.JsonProperty("stock_ratio")
    public Double stockRatio;

    public StockLevelSummary() {
    }

    public StockLevelSummary(final DateTime generated, final DateTime rfidStockTime, final Integer stockRoomsQuantity,
            final Integer salesFloorsQuantity, final Integer storeQuantity, final Double stockRatio) {
        this.generated = generated;
        this.rfidStockTime = rfidStockTime;
        this.stockRoomsQuantity = stockRoomsQuantity;
        this.salesFloorsQuantity = salesFloorsQuantity;
        this.storeQuantity = storeQuantity;
        this.stockRatio = stockRatio;
    }
}
