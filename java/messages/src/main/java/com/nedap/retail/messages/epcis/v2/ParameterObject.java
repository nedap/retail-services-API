package com.nedap.retail.messages.epcis.v2;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class ParameterObject {

    public String name;
    public String type;
    public String value;
    public List<String> values;

    public ParameterObject() {
    }

    public boolean hasValue() {
        return StringUtils.isNotBlank(this.value);
    }

    public boolean hasValues() {
        return CollectionUtils.isNotEmpty(values);
    }
}
