package com.nedap.retail.messages.epc.v2.stock;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.stock.StockSummary;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StockResponse extends StockLevelSummary {

    public List<String> gtins;
    public List<String> locations;
    public List<Integer> quantities;
    public DateTime time;
    public List<Article> articles;

    public StockResponse() {
    }

    public StockResponse(final List<String> gtins, final List<String> locations, final List<Integer> quantities,
            final List<Article> articles, final StockSummary summary) {
        this.gtins = gtins;
        this.locations = locations;
        this.quantities = quantities;
        this.time = summary.eventTime;
        this.articles = articles;
        this.clientIds = summary.clientIds;
        this.rfidStockStartTime = summary.startTime;
        this.rfidStockTime = summary.eventTime;
    }

    public void setStockLevelSummary(final StockLevelSummary stockLevelSummary) {
        generated = stockLevelSummary.generated;
        rfidStockTime = stockLevelSummary.rfidStockTime;
        rfidStockStartTime = stockLevelSummary.rfidStockStartTime;
        stockRoomsQuantity = stockLevelSummary.stockRoomsQuantity;
        salesFloorsQuantity = stockLevelSummary.salesFloorsQuantity;
        storeQuantity = stockLevelSummary.storeQuantity;
        stockRatio = stockLevelSummary.stockRatio;
        clientIds = stockLevelSummary.clientIds;
    }
}
