package com.nedap.retail.messages.epc.v2.approved_difference_list.response;

import java.util.ArrayList;
import java.util.List;

import com.nedap.retail.messages.article.Article;
import org.apache.commons.collections4.CollectionUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.nedap.retail.messages.epc.v2.approved_difference_list.ApprovedDifferenceList;
import com.nedap.retail.messages.epc.v2.approved_difference_list.ApprovedDifferenceListSummary;
import com.nedap.retail.messages.epc.v2.approved_difference_list.GtinDifference;

public class ApprovedDifferenceListExportResponse extends ApprovedDifferenceListSummary {

    public List<String> gtins;

    @JsonProperty("approved_quantity")
    public List<Integer> approvedQuantity;

    public List<Article> articles;

    public ApprovedDifferenceListExportResponse() {
    }

    public ApprovedDifferenceListExportResponse(final ApprovedDifferenceList approvedDifferenceList) {
        super(approvedDifferenceList.approvedDifferenceListSummary);

        gtins = new ArrayList<>();
        approvedQuantity = new ArrayList<>();
        if (!CollectionUtils.isEmpty(approvedDifferenceList.gtinDifferences)) {
            for (final GtinDifference gtinDifference : approvedDifferenceList.gtinDifferences) {
                gtins.add(gtinDifference.gtin);

                if (gtinDifference.approved) {
                    approvedQuantity.add(gtinDifference.rfidQuantity);
                } else {
                    approvedQuantity.add(gtinDifference.erpQuantity);
                }
            }
        }
    }
}
