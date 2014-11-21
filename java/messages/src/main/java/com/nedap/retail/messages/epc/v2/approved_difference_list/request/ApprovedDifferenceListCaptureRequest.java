package com.nedap.retail.messages.epc.v2.approved_difference_list.request;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

public class ApprovedDifferenceListCaptureRequest {
    @JsonProperty("rfid_time")
    public DateTime rfidTime;

    @JsonProperty("erp_stock_id")
    public String erpStockId;

    public String location;

    @JsonProperty("approved_gtins")
    public List<String> approvedGtins;
}
