package com.nedap.retail.messages.system;

import com.nedap.retail.messages.metrics.DetailedStatus;
import com.nedap.retail.messages.metrics.Status;
import java.io.Serializable;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Contains the status_response as described here:
 * http://nvs0272/confluence/display/bus/System+API#SystemAPI-Systemstatus
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SystemStatusPayload implements Serializable {

    private String systemId;
    private String firmwareVersion;
    private Status status;
    private List<DetailedStatus> detailedStatus;
    private String offlineSince;

    public SystemStatusPayload() {
    }

    @JsonProperty("system_id")
    public String getSystemId() {
        return systemId;
    }

    @JsonProperty("system_id")
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @JsonProperty("firmware_version")
    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    @JsonProperty("firmware_version")
    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonProperty("detailed_status")
    public List<DetailedStatus> getDetailedStatus() {
        return detailedStatus;
    }

    @JsonProperty("detailed_status")
    public void setDetailedStatus(List<DetailedStatus> detailedStatus) {
        this.detailedStatus = detailedStatus;
    }

    /**
     * Indicates since when the system is offline.
     * Is only relevant when getStatus() is OFFLINE.
     *
     * @return lexical representation of xsd:dateTime
     */
    @JsonProperty("offline_since")
    public String getOfflineSince() {
        return offlineSince;
    }

    @JsonProperty("offline_since")
    public void setOfflineSince(String offlineSince) {
        this.offlineSince = offlineSince;
    }
}
