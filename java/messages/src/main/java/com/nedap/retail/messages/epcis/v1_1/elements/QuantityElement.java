package com.nedap.retail.messages.epcis.v1_1.elements;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.annotations.SerializedName;

public class QuantityElement {

    /**
     * A class-level identifier for the class to which the specified quantity of objects belongs.
     * For SGTIN, use URI syntax: urn:epc:id:sgtin:CompanyPrefix.ItemRefAndIndicator.SerialNumber
     * For GTIN, use URI syntax: urn:epc:id:gtin:CompanyPrefix.ItemRefAndIndicator
     */
    public static final String EPC_CLASS = "epc_class";
    @SerializedName(EPC_CLASS)
    @JsonProperty(EPC_CLASS)
    public String epcClass;
    /**
     * A number that specifies how many or how much of the specified EPCClass is denoted by this QuantityElement.
     * Negative values are not allowed
     */
    public static final String QUANTITY = "quantity";
    @SerializedName(QUANTITY)
    @JsonProperty(QUANTITY)
    public float quantity;

    /**
     * Specifies a unit of measure by which the specified quantity is to be interpreted as a physical measure,
     * specifying how much of the specified EPCClass is denoted by this QuantityElement.
     */
    public static final String UOM = "uom";
    @SerializedName(UOM)
    @JsonProperty(UOM)
    public String uom;

    public QuantityElement() {
    }

    public QuantityElement(final String epcClass, final float quantity) {
        this.epcClass = epcClass;
        this.quantity = quantity;
    }

    public QuantityElement(final String epcClass, final float quantity, final String uom) {
        this.epcClass = epcClass;
        this.quantity = quantity;
        this.uom = uom;
    }

    @Override
    public String toString() {
        return "QuantityElement: epcClass=" + epcClass + ",quantity=" + quantity + (uom != null ? ",uom=" + uom : "");
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final QuantityElement other = (QuantityElement) obj;
        if (!Objects.equals(this.epcClass, other.epcClass)) {
            return false;
        }
        if (Float.floatToIntBits(this.quantity) != Float.floatToIntBits(other.quantity)) {
            return false;
        }
        if (!Objects.equals(this.uom, other.uom)) {
            return false;
        }
        return true;
    }
}
