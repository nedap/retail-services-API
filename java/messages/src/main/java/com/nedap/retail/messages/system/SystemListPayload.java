package com.nedap.retail.messages.system;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SystemListPayload implements Serializable {

    private static final long serialVersionUID = 93067602970433955L;

    public String systemId;
    public String name;
    public String location;

    public SystemListPayload() {
    }
}
