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

    public void setGtin(final String gtin) {
        this.gtin = gtin;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (gtin == null ? 0 : gtin.hashCode());
        result = prime * result + quantity;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GtinQuantity other = (GtinQuantity) obj;
        if (gtin == null) {
            if (other.gtin != null) {
                return false;
            }
        } else if (!gtin.equals(other.gtin)) {
            return false;
        }
        if (quantity != other.quantity) {
            return false;
        }
        return true;
    }
}
