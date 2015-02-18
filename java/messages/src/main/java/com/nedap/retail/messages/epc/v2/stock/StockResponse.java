package com.nedap.retail.messages.epc.v2.stock;

import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nedap.retail.messages.article.Article;

@JsonInclude(Include.NON_NULL)
public class StockResponse extends StockLevelSummary {

    public List<String> gtins;
    public List<String> locations;
    public List<Integer> quantities;
    public DateTime time;
    public List<Article> articles;

    public StockResponse() {
    }

    public StockResponse(final List<String> gtins, final List<String> locations, final List<Integer> quantities,
            final DateTime time, final List<Article> articles) {
        this.gtins = gtins;
        this.locations = locations;
        this.quantities = quantities;
        this.time = time;
        this.articles = articles;
    }

    public void setStockLevelSummary(final StockLevelSummary stockLevelSummary) {
        generated = stockLevelSummary.generated;
        rfidStockTime = stockLevelSummary.rfidStockTime;
        stockRoomsQuantity = stockLevelSummary.stockRoomsQuantity;
        salesFloorsQuantity = stockLevelSummary.salesFloorsQuantity;
        storeQuantity = stockLevelSummary.storeQuantity;
        stockRatio = stockLevelSummary.stockRatio;
    }
}
