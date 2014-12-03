package com.nedap.retail.messages.epc.v2.approved_difference_list.request;

import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApprovedDifferenceListCaptureRequest {
    @JsonProperty("rfid_time")
    @org.codehaus.jackson.annotate.JsonProperty("rfid_time")
    public DateTime rfidTime;

    @JsonProperty("erp_stock_id")
    @org.codehaus.jackson.annotate.JsonProperty("erp_stock_id")
    public String erpStockId;

    public String location;

    @JsonProperty("approved_gtins")
    @org.codehaus.jackson.annotate.JsonProperty("approved_gtins")
    public List<String> approvedGtins;
}
