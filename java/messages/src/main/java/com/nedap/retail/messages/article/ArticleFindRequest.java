package com.nedap.retail.messages.article;

import java.util.Arrays;
import java.util.List;

public class ArticleFindRequest {

    /**
     * Search query.
     * Please refer to the Article API documentation for a complete description.
     */
    public String query;
    /**
     * Return this number of articles.
     */
    public Integer count;
    /**
     * Skip this number of articles.
     */
    public Integer skip;
    /**
     * Columns to which ordering should be applied.
     * For example "color", "name", "barcodes.value".
     * Please refer to the Article API documentation for a complete description.
     */
    public List<String> order;
    /**
     * Optional external reference. This can be used to match requests with responses from the server (for example,
     * Ajax requests are asynchronous and thus can return out of sequence)
     */
    public Long externRef;

    public ArticleFindRequest() {
        // Empty constructor used by Jackson
    }

    public ArticleFindRequest(final String query) {
        this(query, 100, 0, Arrays.asList(Article.GTIN));
    }

    public ArticleFindRequest(final String query, final Integer count, final Integer skip, final List<String> order) {
        this.query = query;
        this.count = count;
        this.skip = skip;
        this.order = order;
    }
}
