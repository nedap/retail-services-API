package com.nedap.retail.messages.article;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

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
    public static final String CODE = "code";
    public static final String BRAND = "brand";
    public static final String SEASON = "season";
    public static final String NAME = "name";
    public static final String BARCODES = "barcodes";
    public static final String LAST_UPDATED = "last_updated";

    private String gtin;
    private List<Barcode> barcodes;
    private String code;
    private String brand;
    private String season;
    private String name;
    private String option;
    private String style;
    private String color;
    private List<Size> sizes;
    private String supplier;
    private List<Price> prices;
    @JsonProperty(LAST_UPDATED)
    public DateTime lastUpdated;

    // Empty constructor used by Jackson
    public Article() {
    }

    public Article(final String gtin, final List<Barcode> barcodes, final String code, final String brand,
            final String season, final String name, final String option, final String style, final String color,
            final List<Size> sizes, final String supplier, final List<Price> prices, final DateTime lastUpdated) {
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
        this.prices = prices;
        this.lastUpdated = lastUpdated;
    }

    public List<Barcode> getBarcodes() {
        return barcodes;
    }

    public void setBarcodes(final List<Barcode> barcodes) {
        this.barcodes = barcodes;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(final List<Size> sizes) {
        this.sizes = sizes;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(final List<Price> prices) {
        this.prices = prices;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(final String season) {
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getOption() {
        return option;
    }

    public void setOption(final String option) {
        this.option = option;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(final String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(final String supplier) {
        this.supplier = supplier;
    }

    public void setGtin(final String gtin) {
        this.gtin = gtin;
    }

    public String getGtin() {
        return gtin;
    }

    public DateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final DateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    @Override
    public String toString() {
        return "Article [gtin=" + gtin + ", barcodes=" + StringUtils.join(barcodes, ";") + ", code=" + code
                + ", brand=" + brand + ", season=" + season + ", name=" + name + ", option=" + option + ", style="
                + style + ", color=" + color + ", sizes=" + StringUtils.join(sizes, ";") + ", supplier=" + supplier
                + ", prices=" + StringUtils.join(prices, ";") + ", last updated=" + lastUpdated + "]";
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
