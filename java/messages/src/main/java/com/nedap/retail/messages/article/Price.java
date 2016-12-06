package com.nedap.retail.messages.article;

import java.io.Serializable;

public class Price implements Serializable {

    private static final long serialVersionUID = 7065318648199132728L;

    public String currency;
    public String region;
    public Double amount;

    public Price() {
    }

    public Price(final String currency, final String region, final Double amount) {
        this.currency = currency;
        this.region = region;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "[" + region + "|" + currency + "|" + amount + "]";
    }
}
