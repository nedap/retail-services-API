package com.nedap.retail.messages.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

public class StockSummaryListRequest {

    public static final String FROM_EVENT_TIME = "from_event_time";
    public static final String UNTIL_EVENT_TIME = "until_event_time";

    public String location;

    @JsonProperty(FROM_EVENT_TIME)
    public DateTime fromEventTime;

    @JsonProperty(UNTIL_EVENT_TIME)
    public DateTime untilEventTime;

    public StockSummaryListRequest() {
    }

    public StockSummaryListRequest(final String location, final DateTime fromEventTime, final DateTime untilEventTime) {
        this.location = location;
        this.fromEventTime = fromEventTime;
        this.untilEventTime = untilEventTime;
    }
}
