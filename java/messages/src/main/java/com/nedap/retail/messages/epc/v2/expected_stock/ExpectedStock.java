package com.nedap.retail.messages.epc.v2.expected_stock;

import java.util.List;

import org.joda.time.DateTime;

import com.nedap.retail.messages.stock.GtinQuantity;

public class ExpectedStock {
    public DateTime eventTime;
    public Integer quantity;
    public Integer gtinQuantity;
    public List<GtinQuantity> quantityList;

    public ExpectedStock(final DateTime eventTime, final Integer quantity, final Integer gtinQuantity,
                         final List<GtinQuantity> quantityList) {
        this.eventTime = eventTime;
        this.quantity = quantity;
        this.gtinQuantity = gtinQuantity;
        this.quantityList = quantityList;
    }
}
