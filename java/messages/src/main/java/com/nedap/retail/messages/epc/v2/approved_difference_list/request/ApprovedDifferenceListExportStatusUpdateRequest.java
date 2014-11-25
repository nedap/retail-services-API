package com.nedap.retail.messages.epc.v2.approved_difference_list.request;

import java.util.UUID;

import org.codehaus.jackson.annotate.JsonProperty;

import com.nedap.retail.messages.epc.v2.approved_difference_list.ExportStatus;

public class ApprovedDifferenceListExportStatusUpdateRequest {

    public UUID id;

    @JsonProperty("export_status")
    public ExportStatus exportStatus;

}
