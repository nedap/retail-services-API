package com.nedap.retail.messages.epc.v2.approved_difference_list;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GtinDifference {
    public static final String GTIN = "gtin";
    public static final String ERP_QUANTITY = "erp_quantity";
    public static final String RFID_QUANTITY = "rfid_quantity";
    public static final String APPROVED = "approved";

    public String gtin;

    @JsonProperty(ERP_QUANTITY)
    @org.codehaus.jackson.annotate.JsonProperty(ERP_QUANTITY)
    public int erpQuantity;

    @JsonProperty(RFID_QUANTITY)
    @org.codehaus.jackson.annotate.JsonProperty(RFID_QUANTITY)
    public int rfidQuantity;

    public boolean approved;
}
