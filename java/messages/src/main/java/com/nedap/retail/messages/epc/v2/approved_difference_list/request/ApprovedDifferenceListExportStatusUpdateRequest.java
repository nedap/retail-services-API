package com.nedap.retail.messages.epc.v2.approved_difference_list.request;

import java.util.UUID;

import com.nedap.retail.messages.epc.v2.approved_difference_list.ExportStatus;

public class ApprovedDifferenceListExportStatusUpdateRequest {

    public UUID id;

    @org.codehaus.jackson.annotate.JsonProperty("export_status")
    public ExportStatus exportStatus;
}
