package com.nedap.retail.messages.article;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Price implements Serializable {

    private String currency;
    private String region;
    private Double amount;

    public Price() {
    }

    public Price(final String currency, final String region, final Double amount) {
        this.currency = currency;
        this.region = region;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(final String region) {
        this.region = region;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }
    
    @Override
    public String toString() {
        return "[" + region + "|" + currency + "|" + amount + "]";
    }
}
