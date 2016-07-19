package com.nedap.retail.messages.epcis.v1_1.elements;

import java.math.BigDecimal;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;

public class QuantityElement {

    /**
     * A class-level identifier for the class to which the specified quantity of objects belongs.
     * For SGTIN, use URI syntax: urn:epc:id:sgtin:CompanyPrefix.ItemRefAndIndicator.SerialNumber
     * For GTIN, use URI syntax: urn:epc:id:gtin:CompanyPrefix.ItemRefAndIndicator
     */
    @JsonProperty("epc_class")
    public String epcClass;
    /**
     * A number that specifies how many or how much of the specified EPCClass is denoted by this QuantityElement.
     * Negative values are not allowed
     */
    public BigDecimal quantity;

    /**
     * Specifies a unit of measure by which the specified quantity is to be interpreted as a physical measure,
     * specifying how much of the specified EPCClass is denoted by this QuantityElement.
     */
    public String uom;

    public QuantityElement() {
    }

    public QuantityElement(final String epcClass, final BigDecimal quantity) {
        this.epcClass = epcClass;
        this.quantity = quantity;
    }

    public QuantityElement(final String epcClass, final BigDecimal quantity, final String uom) {
        this.epcClass = epcClass;
        this.quantity = quantity;
        this.uom = uom;
    }

    @Override
    public String toString() {
        return "QuantityElement: epcClass=" + epcClass + ",quantity=" + quantity + (uom != null ? ",uom=" + uom : "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuantityElement)) return false;

        QuantityElement that = (QuantityElement) o;

        if (!epcClass.equals(that.epcClass)) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        return uom != null ? uom.equals(that.uom) : that.uom == null;

    }

    @Override
    public int hashCode() {
        int result = epcClass.hashCode();
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (uom != null ? uom.hashCode() : 0);
        return result;
    }
}
