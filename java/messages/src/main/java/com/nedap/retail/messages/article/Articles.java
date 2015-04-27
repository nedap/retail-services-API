package com.nedap.retail.messages.article;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Articles implements Serializable {

    private static final long serialVersionUID = -4848388482600713978L;

    public List<Article> articles;

    // Empty constructor used by Jackson
    public Articles() {
    }

    public Articles(final List<Article> articles) {
        this.articles = articles;
    }
}
