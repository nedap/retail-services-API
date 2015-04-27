package com.nedap.retail.messages.epc.v2.difference_list;

import java.util.List;

import org.joda.time.DateTime;

import com.nedap.retail.messages.article.Article;

public class DifferenceListResponse {

    public List<String> gtins;

    @org.codehaus.jackson.annotate.JsonProperty("erp_stock")
    public List<Integer> erpStock;

    @org.codehaus.jackson.annotate.JsonProperty("rfid_stock")
    public List<Integer> rfidStock;

    public List<Article> articles;

    @org.codehaus.jackson.annotate.JsonProperty("erp_stock_time")
    public DateTime erpStockTime;

    @org.codehaus.jackson.annotate.JsonProperty("rfid_stock_time")
    public DateTime rfidStockTime;

    @org.codehaus.jackson.annotate.JsonProperty("erp_gtin_quantity")
    public Integer erpGtinQuantity;

    @org.codehaus.jackson.annotate.JsonProperty("rfid_gtin_quantity")
    public Integer rfidGtinQuantity;

    public DifferenceListResponse() {
    }

    public DifferenceListResponse(final List<String> gtins, final List<Integer> erpStock,
            final List<Integer> rfidStock, final List<Article> articles, final DateTime erpStockTime,
            final DateTime rfidStockTime, final Integer erpGtinQuantity, final Integer rfidGtinQuantity) {
        this.gtins = gtins;
        this.erpStock = erpStock;
        this.rfidStock = rfidStock;
        this.articles = articles;
        this.erpStockTime = erpStockTime;
        this.rfidStockTime = rfidStockTime;
        this.erpGtinQuantity = erpGtinQuantity;
        this.rfidGtinQuantity = rfidGtinQuantity;
    }
}
