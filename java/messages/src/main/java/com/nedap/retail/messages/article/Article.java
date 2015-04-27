package com.nedap.retail.messages.article;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Article implements Serializable {

    private static final long serialVersionUID = 3681466505727698821L;

    public static final String GTIN = "gtin";
    public static final String STYLE = "style";
    public static final String OPTION = "option";
    public static final String COLOR = "color";
    public static final String SIZES = "sizes";
    public static final String PRICES = "prices";
    public static final String SUPPLIER = "supplier";
    public static final String CATEGORY = "category";
    public static final String CODE = "code";
    public static final String BRAND = "brand";
    public static final String SEASON = "season";
    public static final String NAME = "name";
    public static final String BARCODES = "barcodes";
    public static final String LAST_UPDATED = "last_updated";
    public static final String MARKDOWN = "markdown";

    public String gtin;
    public List<Barcode> barcodes;
    public String code;
    public String brand;
    public String season;
    public String name;
    public String option;
    public String style;
    public String color;
    public List<Size> sizes;
    public String supplier;
    public String category;
    public List<Price> prices;
    public Boolean markdown;

    @org.codehaus.jackson.annotate.JsonProperty(LAST_UPDATED)
    public DateTime lastUpdated;

    @JsonIgnore
    @org.codehaus.jackson.annotate.JsonIgnore
    public UUID lastUpdatedUUID;

    // Empty constructor used by Jackson
    public Article() {
    }

    public Article(final String gtin, final List<Barcode> barcodes, final String code, final String brand,
            final String season, final String name, final String option, final String style, final String color,
            final List<Size> sizes, final String supplier, final String category, final List<Price> prices,
            final DateTime lastUpdated) {
        this.gtin = gtin;
        this.barcodes = barcodes;
        this.code = code;
        this.brand = brand;
        this.season = season;
        this.name = name;
        this.option = option;
        this.style = style;
        this.color = color;
        this.sizes = sizes;
        this.supplier = supplier;
        this.category = category;
        this.prices = prices;
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Article [gtin=" + gtin + ", barcodes=" + StringUtils.join(barcodes, ";") + ", code=" + code
                + ", brand=" + brand + ", season=" + season + ", name=" + name + ", option=" + option + ", style="
                + style + ", color=" + color + ", sizes=" + StringUtils.join(sizes, ";") + ", supplier=" + supplier
                + ", category=" + category + ", prices=" + StringUtils.join(prices, ";") + ", last updated="
                + lastUpdated + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (gtin == null ? 0 : gtin.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        if (gtin == null) {
            if (other.gtin != null) {
                return false;
            }
        } else if (!gtin.equals(other.gtin)) {
            return false;
        }
        return true;
    }
}
