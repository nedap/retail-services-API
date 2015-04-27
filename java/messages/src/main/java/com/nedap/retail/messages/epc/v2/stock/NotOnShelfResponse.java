package com.nedap.retail.messages.epc.v2.stock;

import java.util.List;

import org.joda.time.DateTime;

import com.nedap.retail.messages.article.Article;

public class NotOnShelfResponse extends NotOnShelfSummary {

    public List<String> gtins;
    public List<Integer> salesFloorStock;
    public List<String> stockRoomLocations;
    public List<Integer> stockRoomStock;
    public List<Article> articles;

    public NotOnShelfResponse() {
    }

    public NotOnShelfResponse(final DateTime rfidStockTime, final Integer notOnShelfGtinQuantity,
            final Integer rfidGtinQuantity, final Double notOnShelfPercentage, final List<String> gtins,
            final List<Integer> salesFloorStock, final List<String> stockRoomLocations,
            final List<Integer> stockRoomStock) {
        super(rfidStockTime, notOnShelfGtinQuantity, rfidGtinQuantity, notOnShelfPercentage);
        this.gtins = gtins;
        this.salesFloorStock = salesFloorStock;
        this.stockRoomLocations = stockRoomLocations;
        this.stockRoomStock = stockRoomStock;
    }
}
