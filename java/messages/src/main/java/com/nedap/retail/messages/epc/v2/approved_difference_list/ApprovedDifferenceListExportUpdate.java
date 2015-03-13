package com.nedap.retail.messages.epc.v2.approved_difference_list;

import java.util.UUID;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ApprovedDifferenceListExportUpdate {

    public static final String ERP_STOCK_ID = "erp_stock_id";
    public static final String ERP_STOCK_TIME = "erp_stock_time";
    public static final String RFID_STOCK_TIME = "rfid_stock_time";

    public UUID id;

    public String location;

    @JsonProperty(ERP_STOCK_ID)
    @org.codehaus.jackson.annotate.JsonProperty(ERP_STOCK_ID)
    public String erpStockId;

    @JsonProperty(ERP_STOCK_TIME)
    @org.codehaus.jackson.annotate.JsonProperty(ERP_STOCK_TIME)
    public DateTime erpStockTime;

    @JsonProperty(RFID_STOCK_TIME)
    @org.codehaus.jackson.annotate.JsonProperty(RFID_STOCK_TIME)
    public DateTime rfidStockTime;

    public ApprovedDifferenceListExportUpdate() {

    }

    public ApprovedDifferenceListExportUpdate(final UUID id, final String location, final String erpStockId,
            final DateTime erpStockTime, final DateTime rfidStockTime) {
        this.id = id;
        this.location = location;
        this.erpStockId = erpStockId;
        this.erpStockTime = erpStockTime;
        this.rfidStockTime = rfidStockTime;
    }

}
