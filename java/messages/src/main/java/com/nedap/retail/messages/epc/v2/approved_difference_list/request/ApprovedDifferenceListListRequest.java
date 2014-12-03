package com.nedap.retail.messages.epc.v2.approved_difference_list.request;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApprovedDifferenceListListRequest {
    @JsonProperty("from_rfid_time")
    @org.codehaus.jackson.annotate.JsonProperty("from_rfid_time")
    public DateTime fromRfidTime;

    @JsonProperty("until_rfid_time")
    @org.codehaus.jackson.annotate.JsonProperty("until_rfid_time")
    public DateTime untilRfidTime;

    public String location;
}
