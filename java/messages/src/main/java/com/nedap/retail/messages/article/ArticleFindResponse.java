package com.nedap.retail.messages.article;

import java.util.List;

public class ArticleFindResponse {

    /**
     * The external reference that this object is a response to.
     * From the extern_ref parameter sent as part of the data request.
     */
    public Long externRef;

    /**
     * Total records, before filtering (i.e. the total number of records in the database)
     */
    public long recordsTotal;

    /**
     * Total records, after filtering (i.e. the total number of records after filtering has been applied - not just the
     * number of records being returned for this page of data).
     */
    public long recordsFiltered;

    /**
     * Ordered array of articles.
     */
    public List<Article> articles;

    public ArticleFindResponse() {
        // Empty constructor used by Jackson
    }
}
