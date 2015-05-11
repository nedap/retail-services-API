package com.nedap.retail.messages.epc.v2.stock;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StockLevelSummary {

    public DateTime generated;

    @JsonProperty("rfid_stock_time")
    public DateTime rfidStockTime;

    @JsonProperty("rfid_stock_start_time")
    public DateTime rfidStockStartTime;

    @JsonProperty("stock_rooms_quantity")
    public Integer stockRoomsQuantity;

    @JsonProperty("sales_floors_quantity")
    public Integer salesFloorsQuantity;

    @JsonProperty("store_quantity")
    public Integer storeQuantity;

    @JsonProperty("stock_ratio")
    public Double stockRatio;

    public StockLevelSummary() {
    }

    public StockLevelSummary(final DateTime generated, final DateTime rfidStockTime, final DateTime rfidStockStartTime,
            final Integer stockRoomsQuantity, final Integer salesFloorsQuantity, final Integer storeQuantity,
            final Double stockRatio) {
        this.generated = generated;
        this.rfidStockTime = rfidStockTime;
        this.rfidStockStartTime = rfidStockStartTime;
        this.stockRoomsQuantity = stockRoomsQuantity;
        this.salesFloorsQuantity = salesFloorsQuantity;
        this.storeQuantity = storeQuantity;
        this.stockRatio = stockRatio;
    }
}
