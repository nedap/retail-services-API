package com.nedap.retail.messages.article;

import java.util.List;

public class ArticleRetrieveRequest {
    public List<String> gtins;
    public List<String> barcodes;
    public List<String> fields;

    public ArticleRetrieveRequest() {
    }

    public ArticleRetrieveRequest(final List<String> gtins, final List<String> barcodes, final List<String> fields) {
        this.gtins = gtins;
        this.barcodes = barcodes;
        this.fields = fields;
    }
}
