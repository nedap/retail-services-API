package com.nedap.retail.messages.system;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nedap.retail.messages.metrics.DetailedStatus;
import com.nedap.retail.messages.metrics.Status;

/**
 * System status payload.
 */
@JsonInclude(Include.NON_NULL)
public class SystemStatusPayload implements Serializable {

    private String systemId;
    private String firmwareVersion;
    private Status status;
    private List<DetailedStatus> detailedStatus;
    private String offlineSince;

    public SystemStatusPayload() {
    }

    public static final String SYSTEM_ID = "system_id";

    @JsonProperty(SYSTEM_ID)
    @org.codehaus.jackson.annotate.JsonProperty(SYSTEM_ID)
    public String getSystemId() {
        return systemId;
    }

    @JsonProperty(SYSTEM_ID)
    @org.codehaus.jackson.annotate.JsonProperty(SYSTEM_ID)
    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }

    public static final String FIRMWARE_VERSION = "firmware_version";

    @JsonProperty(FIRMWARE_VERSION)
    @org.codehaus.jackson.annotate.JsonProperty(FIRMWARE_VERSION)
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    @JsonProperty(FIRMWARE_VERSION)
    @org.codehaus.jackson.annotate.JsonProperty(FIRMWARE_VERSION)
    public void setFirmwareVersion(final String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public static final String STATUS = "status";

    @JsonProperty(STATUS)
    @org.codehaus.jackson.annotate.JsonProperty(STATUS)
    public Status getStatus() {
        return status;
    }

    @JsonProperty(STATUS)
    @org.codehaus.jackson.annotate.JsonProperty(STATUS)
    public void setStatus(final Status status) {
        this.status = status;
    }

    public static final String DETAILED_STATUS = "detailed_status";

    @JsonProperty(DETAILED_STATUS)
    @org.codehaus.jackson.annotate.JsonProperty(DETAILED_STATUS)
    public List<DetailedStatus> getDetailedStatus() {
        return detailedStatus;
    }

    @JsonProperty(DETAILED_STATUS)
    @org.codehaus.jackson.annotate.JsonProperty(DETAILED_STATUS)
    public void setDetailedStatus(final List<DetailedStatus> detailedStatus) {
        this.detailedStatus = detailedStatus;
    }

    /**
     * Indicates since when the system is offline.
     * Is only relevant when getStatus() is OFFLINE.
     *
     * @return lexical representation of xsd:dateTime
     */
    public static final String OFFLINE_SINCE = "offline_since";

    @JsonProperty(OFFLINE_SINCE)
    @org.codehaus.jackson.annotate.JsonProperty(OFFLINE_SINCE)
    public String getOfflineSince() {
        return offlineSince;
    }

    @JsonProperty(OFFLINE_SINCE)
    @org.codehaus.jackson.annotate.JsonProperty(OFFLINE_SINCE)
    public void setOfflineSince(final String offlineSince) {
        this.offlineSince = offlineSince;
    }
}
