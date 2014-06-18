package com.nedap.retail.messages.epcis.v1_1.elements;

import org.codehaus.jackson.annotate.JsonProperty;

import com.google.gson.annotations.SerializedName;

public class BizTransactionElement {

    @JsonProperty("type")
    @SerializedName("type")
    public String type;

    @JsonProperty("biz_transaction")
    @SerializedName("biz_transaction")
    public String bizTransaction;

    public BizTransactionElement() {
    }

    public BizTransactionElement(final String type, final String bizTransaction) {
        this.type = type;
        this.bizTransaction = bizTransaction;
    }

    @Override
    public String toString() {
        return "BizTransactionElement: type=" + type + ",biz_transaction=" + bizTransaction;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (bizTransaction == null ? 0 : bizTransaction.hashCode());
        result = prime * result + (type == null ? 0 : type.hashCode());
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
        final BizTransactionElement other = (BizTransactionElement) obj;
        if (bizTransaction == null) {
            if (other.bizTransaction != null) {
                return false;
            }
        } else if (!bizTransaction.equals(other.bizTransaction)) {
            return false;
        }
        if (type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!type.equals(other.type)) {
            return false;
        }
        return true;
    }
}
