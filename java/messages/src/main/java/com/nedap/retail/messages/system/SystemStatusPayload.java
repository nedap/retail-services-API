package com.nedap.retail.messages.system;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.nedap.retail.messages.metrics.DetailedStatus;
import com.nedap.retail.messages.metrics.Status;

/**
 * System status payload.
 */
@JsonInclude(Include.NON_NULL)
public class SystemStatusPayload implements Serializable {

    private static final long serialVersionUID = -1153550835709974759L;

    public String systemId;
    public String firmwareVersion;
    public Status status;
    public List<DetailedStatus> detailedStatus;
    public String offlineSince;

    public SystemStatusPayload() {
    }
}
