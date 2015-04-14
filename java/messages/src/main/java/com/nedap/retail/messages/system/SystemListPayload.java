package com.nedap.retail.messages.system;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SystemListPayload implements Serializable {

    private static final long serialVersionUID = 93067602970433955L;

    private String systemId;
    private String name;
    private String location;

    public SystemListPayload() {
    }

    @JsonProperty("system_id")
    @org.codehaus.jackson.annotate.JsonProperty("system_id")
    public String getSystemId() {
        return systemId;
    }

    @JsonProperty("system_id")
    @org.codehaus.jackson.annotate.JsonProperty("system_id")
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }

    @JsonProperty("name")
    @org.codehaus.jackson.annotate.JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    @org.codehaus.jackson.annotate.JsonProperty("name")
    public void setName(final String name) {
        this.name = name;
    }

    @JsonProperty("location")
    @org.codehaus.jackson.annotate.JsonProperty("location")
    public String getLocation() {
        return location;
    }

    @JsonProperty("location")
    @org.codehaus.jackson.annotate.JsonProperty("location")
    public void setLocation(final String location) {
        this.location = location;
    }
}
