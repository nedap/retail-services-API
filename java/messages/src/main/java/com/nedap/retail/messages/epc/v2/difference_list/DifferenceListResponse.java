package com.nedap.retail.messages.epc.v2.difference_list;

import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nedap.retail.messages.article.Article;

public class DifferenceListResponse {

    public List<String> gtins;

    @JsonProperty("erp_stock")
    @org.codehaus.jackson.annotate.JsonProperty("erp_stock")
    public List<Integer> erpStock;

    @JsonProperty("rfid_stock")
    @org.codehaus.jackson.annotate.JsonProperty("rfid_stock")
    public List<Integer> rfidStock;

    public List<Article> articles;

    @JsonProperty("erp_stock_time")
    @org.codehaus.jackson.annotate.JsonProperty("erp_stock_time")
    public DateTime erpStockTime;

    @JsonProperty("rfid_stock_time")
    @org.codehaus.jackson.annotate.JsonProperty("rfid_stock_time")
    public DateTime rfidStockTime;

    @JsonProperty("erp_gtin_quantity")
    @org.codehaus.jackson.annotate.JsonProperty("erp_gtin_quantity")
    public Integer erpGtinQuantity;

    @JsonProperty("rfid_gtin_quantity")
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
