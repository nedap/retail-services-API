package com.nedap.retail.messages.system;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SystemListPayload implements Serializable {

    private String systemId;
    private String name;
    private String location;

    public SystemListPayload() {
    }

    @JsonProperty("system_id")
    public String getSystemId() {
        return systemId;
    }

    @JsonProperty("system_id")
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(String location) {
        this.location = location;
    }
}
