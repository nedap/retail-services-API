package com.nedap.retail.messages.article;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Barcode implements Serializable {

    private static final long serialVersionUID = 1915457722258771573L;

    public String type;
    public String value;

    public Barcode() {
    }

    public Barcode(final String type, final String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "[" + type + "|" + value + "]";
    }
}
