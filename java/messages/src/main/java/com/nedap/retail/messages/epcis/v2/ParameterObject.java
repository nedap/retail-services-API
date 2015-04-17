package com.nedap.retail.messages.epcis.v2;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ParameterObject {

    private String name;
    private String type;
    private String value;
    private List<String> values;

    public ParameterObject() {

    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
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

    public List<String> getValues() {
        return values;
    }

    public void setValues(final List<String> values) {
        this.values = values;
    }

    public boolean hasValue() {
        return StringUtils.isNotBlank(this.value);
    }

    public boolean hasValues() {
        return CollectionUtils.isNotEmpty(values);
    }
}
