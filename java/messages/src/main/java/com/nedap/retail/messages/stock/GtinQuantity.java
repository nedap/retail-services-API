package com.nedap.retail.messages.stock;

public class GtinQuantity {

    private String gtin;
    private int quantity;

    public GtinQuantity() {
    }

    public GtinQuantity(final String gtin, final int quantity) {
        this.gtin = gtin;
        this.quantity = quantity;
    }

    public String getGtin() {
        return gtin;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return "GtinQuantity{" + "gtin=" + gtin + ", quantity=" + quantity + '}';
    }

}
