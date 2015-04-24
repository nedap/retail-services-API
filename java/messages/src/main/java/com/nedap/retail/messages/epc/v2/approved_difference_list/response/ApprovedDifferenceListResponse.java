package com.nedap.retail.messages.epc.v2.approved_difference_list.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nedap.retail.messages.epc.v2.approved_difference_list.ApprovedDifferenceList;
import com.nedap.retail.messages.epc.v2.approved_difference_list.ApprovedDifferenceListSummary;
import com.nedap.retail.messages.epc.v2.approved_difference_list.GtinDifference;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ApprovedDifferenceListResponse extends ApprovedDifferenceListSummary {

    public List<String> gtins;

    @JsonProperty("erp_stock")
    @org.codehaus.jackson.annotate.JsonProperty("erp_stock")
    public List<Integer> erpStock;

    @JsonProperty("rfid_stock")
    @org.codehaus.jackson.annotate.JsonProperty("rfid_stock")
    public List<Integer> rfidStock;

    public List<Boolean> approved;

    public ApprovedDifferenceListResponse() {
    }

    public ApprovedDifferenceListResponse(final ApprovedDifferenceList approvedDifferenceList) {
        super(approvedDifferenceList.approvedDifferenceListSummary);

        gtins = new ArrayList<>();
        erpStock = new ArrayList<>();
        rfidStock = new ArrayList<>();
        approved = new ArrayList<>();
        if (!CollectionUtils.isEmpty(approvedDifferenceList.gtinDifferences)) {
            for (final GtinDifference gtinDifference : approvedDifferenceList.gtinDifferences) {
                gtins.add(gtinDifference.gtin);
                erpStock.add(gtinDifference.erpQuantity);
                rfidStock.add(gtinDifference.rfidQuantity);
                approved.add(gtinDifference.approved);
            }
        }
    }
}
