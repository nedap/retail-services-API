package com.nedap.retail.messages.epc.v2.approved_difference_list.request;

import java.util.List;

import org.joda.time.DateTime;

public class ApprovedDifferenceListCaptureRequest {

    @org.codehaus.jackson.annotate.JsonProperty("rfid_time")
    public DateTime rfidTime;

    @org.codehaus.jackson.annotate.JsonProperty("erp_stock_id")
    public String erpStockId;

    public String location;

    @org.codehaus.jackson.annotate.JsonProperty("approved_gtins")
    public List<String> approvedGtins;
}
