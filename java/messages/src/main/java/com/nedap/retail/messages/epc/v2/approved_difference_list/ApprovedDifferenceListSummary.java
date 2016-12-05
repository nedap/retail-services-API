package com.nedap.retail.messages.epc.v2.approved_difference_list;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApprovedDifferenceListSummary {

    public static final String ORGANIZATION_ID = "organization_id";
    public static final String APPROVED_ON = "approved_on";
    public static final String APPROVED_BY = "approved_by";
    public static final String APPROVED_BY_ID = "approved_by_id";
    public static final String LOCATION_ID = "location_id";
    public static final String ERP_STOCK_ID = "erp_stock_id";
    public static final String ERP_STOCK_TIME = "erp_stock_time";
    public static final String ERP_QUANTITY = "erp_quantity";
    public static final String ERP_GTIN_QUANTITY = "erp_gtin_quantity";
    public static final String RFID_STOCK_TIME = "rfid_stock_time";
    public static final String RFID_QUANTITY = "rfid_quantity";
    public static final String RFID_GTIN_QUANTITY = "rfid_gtin_quantity";
    public static final String ABSOLUTE_DIFFERENCE = "absolute_difference";
    public static final String PLUS_DIFFERENCE = "plus_difference";
    public static final String MINUS_DIFFERENCE = "minus_difference";
    public static final String EXPORT_STATUS = "export_status";
    public static final String EXPORT_TIME = "export_time";
    public static final String EXTERN_REF = "extern_ref";

    @JsonProperty(APPROVED_ON)
    public DateTime approvedOn;

    @JsonProperty(APPROVED_BY)
    public String approvedBy;

    @JsonIgnore
    public String approvedById;

    public String location;

    @JsonProperty(ERP_STOCK_ID)
    public String erpStockId;

    @JsonProperty(ERP_STOCK_TIME)
    public DateTime erpStockTime;

    @JsonProperty(ERP_QUANTITY)
    public int erpQuantity;

    @JsonProperty(ERP_GTIN_QUANTITY)
    public int erpGtinQuantity;

    @JsonProperty(RFID_STOCK_TIME)
    public DateTime rfidStockTime;

    @JsonProperty(RFID_QUANTITY)
    public int rfidQuantity;

    @JsonProperty(RFID_GTIN_QUANTITY)
    public int rfidGtinQuantity;

    @JsonProperty(ABSOLUTE_DIFFERENCE)
    public int absoluteDifference;

    @JsonProperty(PLUS_DIFFERENCE)
    public int plusDifference;

    @JsonProperty(MINUS_DIFFERENCE)
    public int minusDifference;

    @JsonProperty(EXPORT_STATUS)
    public ExportStatus exportStatus;

    @JsonProperty(EXPORT_TIME)
    public DateTime exportTime;

    @JsonProperty(EXTERN_REF)
    public String externRef;

    public UUID id;

    public ApprovedDifferenceListSummary() {
    }

    public ApprovedDifferenceListSummary(final ApprovedDifferenceListSummary approvedDifferenceListSummary) {
        approvedOn = approvedDifferenceListSummary.approvedOn;
        approvedBy = approvedDifferenceListSummary.approvedBy;
        approvedById = approvedDifferenceListSummary.approvedById;
        location = approvedDifferenceListSummary.location;
        erpStockId = approvedDifferenceListSummary.erpStockId;
        erpStockTime = approvedDifferenceListSummary.erpStockTime;
        erpQuantity = approvedDifferenceListSummary.erpQuantity;
        erpGtinQuantity = approvedDifferenceListSummary.erpGtinQuantity;
        rfidStockTime = approvedDifferenceListSummary.rfidStockTime;
        rfidQuantity = approvedDifferenceListSummary.rfidQuantity;
        rfidGtinQuantity = approvedDifferenceListSummary.rfidGtinQuantity;
        absoluteDifference = approvedDifferenceListSummary.absoluteDifference;
        plusDifference = approvedDifferenceListSummary.plusDifference;
        minusDifference = approvedDifferenceListSummary.minusDifference;
        exportStatus = approvedDifferenceListSummary.exportStatus;
        exportTime = approvedDifferenceListSummary.exportTime;
        externRef = approvedDifferenceListSummary.externRef;
        id = approvedDifferenceListSummary.id;
    }
}
