package com.nedap.retail.messages.epc.v2.approved_difference_list.response;

import java.util.ArrayList;
import java.util.List;
import com.nedap.retail.messages.epc.v2.approved_difference_list.ApprovedDifferenceListWithBarcodes;
import org.apache.commons.collections4.CollectionUtils;

public class ApprovedDifferenceListWithBarcodesResponse extends ApprovedDifferenceListResponse {
    public List<String> barcodes;
    
    public ApprovedDifferenceListWithBarcodesResponse(ApprovedDifferenceListWithBarcodes approvedDifferenceListWithBarcodes) {
        super(approvedDifferenceListWithBarcodes);
        if (!CollectionUtils.isEmpty(approvedDifferenceListWithBarcodes.barcodes)) {
            barcodes = new ArrayList<>();
            for (final String barcode : approvedDifferenceListWithBarcodes.barcodes) {
                barcodes.add(barcode);
            }
        }
    }
}
