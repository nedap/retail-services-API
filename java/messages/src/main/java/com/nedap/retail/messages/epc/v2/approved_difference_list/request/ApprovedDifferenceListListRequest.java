package com.nedap.retail.messages.epc.v2.approved_difference_list.request;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

public class ApprovedDifferenceListListRequest {

    @JsonProperty("from_rfid_time")
    public DateTime fromRfidTime;

    @JsonProperty("until_rfid_time")
    public DateTime untilRfidTime;

    public String location;
}
