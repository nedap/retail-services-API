package com.nedap.retail.messages.stock;

public class GtinQuantity {

    public String gtin;
    public int quantity;
    public boolean exclude;

    public GtinQuantity() {
    }
    
    public GtinQuantity(final String gtin, final int quantity) {
    	this(gtin, quantity, false);
    }

    public GtinQuantity(final String gtin, final int quantity, boolean exclude) {
        this.gtin = gtin;
        this.quantity = quantity;
        this.exclude = exclude;
    }

    @Override
    public String toString() {
        return "GtinQuantity{" + "gtin=" + gtin + ", quantity=" + quantity + "exclude=" + exclude + '}';
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (gtin == null ? 0 : gtin.hashCode());
        result = prime * result + quantity;
        result = prime * result + (exclude ? 1 : 0);
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
        
        if (exclude != other.exclude) {
        	return false;
        }
        
        
        return true;
    }
}
