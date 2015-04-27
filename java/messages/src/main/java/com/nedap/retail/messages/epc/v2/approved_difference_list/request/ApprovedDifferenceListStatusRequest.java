package com.nedap.retail.messages.epc.v2.approved_difference_list.request;

import org.joda.time.DateTime;

public class ApprovedDifferenceListStatusRequest {

    @org.codehaus.jackson.annotate.JsonProperty("rfid_time")
    public DateTime rfidTime;

    public String location;
}
