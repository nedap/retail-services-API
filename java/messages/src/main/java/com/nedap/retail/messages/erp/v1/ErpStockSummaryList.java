package com.nedap.retail.messages.erp.v1;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ErpStockSummaryList {

    public static final String FROM_EVENT_TIME = "from_event_time";
    public static final String UNTIL_EVENT_TIME = "until_event_time";

    public String location;

    @JsonProperty(FROM_EVENT_TIME)
    @org.codehaus.jackson.annotate.JsonProperty(FROM_EVENT_TIME)
    public DateTime fromEventTime;

    @JsonProperty(UNTIL_EVENT_TIME)
    @org.codehaus.jackson.annotate.JsonProperty(UNTIL_EVENT_TIME)
    public DateTime untilEventTime;

    public ErpStockSummaryList() {

    }

    public ErpStockSummaryList(final String location, final DateTime fromEventTime, final DateTime untilEventTime) {
        this.location = location;
        this.fromEventTime = fromEventTime;
        this.untilEventTime = untilEventTime;
    }

}
