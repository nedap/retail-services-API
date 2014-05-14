package com.nedap.retail.messages.epcis.v1_1.elements;

import com.google.gson.annotations.SerializedName;
import org.codehaus.jackson.annotate.JsonProperty;

public class BizTransactionElement {

    @JsonProperty("type")
    @SerializedName("type")
    public String type;

    @JsonProperty("biz_transaction")
    @SerializedName("biz_transaction")
    public String bizTransaction;

    public BizTransactionElement() {
    }

    public BizTransactionElement(String type, String bizTransaction) {
        this.type = type;
        this.bizTransaction = bizTransaction;
    }

    @Override
    public String toString() {
        return "BizTransactionElement: type=" + type + ",biz_transaction=" + bizTransaction;
    }
}
