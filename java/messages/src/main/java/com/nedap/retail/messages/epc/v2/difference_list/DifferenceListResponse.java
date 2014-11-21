package com.nedap.retail.messages.epc.v2.difference_list;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import com.nedap.retail.messages.article.Article;

public class DifferenceListResponse {

    private List<String> gtins;
    @JsonProperty("erp_stock")
    private List<Integer> erpStock;
    @JsonProperty("rfid_stock")
    private List<Integer> rfidStock;
    private List<Article> articles;
    @JsonProperty("erp_stock_time")
    private DateTime erpStockTime;
    @JsonProperty("rfid_stock_time")
    private DateTime rfidStockTime;
    @JsonProperty("erp_gtin_quantity")
    private Integer erpGtinQuantity;
    @JsonProperty("rfid_gtin_quantity")
    private Integer rfidGtinQuantity;

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

    public List<String> getGtins() {
        return gtins;
    }

    public void setGtins(final List<String> gtins) {
        this.gtins = gtins;
    }

    public List<Integer> getErpStock() {
        return erpStock;
    }

    public void setErpStock(final List<Integer> erpStock) {
        this.erpStock = erpStock;
    }

    public List<Integer> getRfidStock() {
        return rfidStock;
    }

    public void setRfidStock(final List<Integer> rfidStock) {
        this.rfidStock = rfidStock;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(final List<Article> articles) {
        this.articles = articles;
    }

    public DateTime getErpStockTime() {
        return erpStockTime;
    }

    public void setErpStockTime(DateTime erpStockTime) {
        this.erpStockTime = erpStockTime;
    }

    public DateTime getRfidStockTime() {
        return rfidStockTime;
    }

    public void setRfidStockTime(final DateTime rfidStockTime) {
        this.rfidStockTime = rfidStockTime;
    }

    public Integer getErpGtinQuantity() {
        return erpGtinQuantity;
    }

    public void setErpGtinQuantity(Integer erpGtinQuantity) {
        this.erpGtinQuantity = erpGtinQuantity;
    }

    public Integer getRfidGtinQuantity() {
        return rfidGtinQuantity;
    }

    public void setRfidGtinQuantity(Integer rfidGtinQuantity) {
        this.rfidGtinQuantity = rfidGtinQuantity;
    }
}
