package com.nedap.retail.messages.article;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)  
public class Barcode implements Serializable {
    private String type;
    private String value;

    public Barcode() {
    }

    public Barcode(final String type, final String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
    
    public String toString() {
        return "[" + type + "|" + value + "]";
    }
}