package com.nedap.retail.messages;

import java.io.Serializable;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public abstract class AbstractBusResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String corr_id;
    protected String version;
    protected String namespace;
    protected String type;
    protected String error;
    protected Integer status;

    public AbstractBusResponse() {
    }

    public String getCorr_id() {
        return corr_id;
    }

    public void setCorr_id(String corr_id) {
        this.corr_id = corr_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    abstract public Object getPayload();
}
