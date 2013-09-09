package com.nedap.retail.messages;

import java.util.Map;
import java.util.Map.Entry;
import org.codehaus.jackson.annotate.JsonIgnore;

public class BusRequest extends AbstractBusRequest {

    private static final long serialVersionUID = 1L;
    protected Map<String, Object> payload;

    @Override
    public Map getPayload() {
        return payload;
    }

    public void setPayload(Map payload) {
        this.payload = payload;
    }

    /**
     * Creates a HTTP query string for the payload.
     *
     * For example, if payload contains:
     * {
     * "sitegroup_id" => 123,
     * "date" => "2013-01-24"
     * }
     * Then the query string will be: "sitegroup_id=123&date=2013-01-24"
     */
    @JsonIgnore
    public String getQueryString() {
        final StringBuilder sb = new StringBuilder();
        for (final Entry<String, Object> entry : payload.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }
}
