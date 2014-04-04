package com.nedap.retail.messages;

import java.io.Serializable;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public abstract class AbstractApiResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Success! Message is successfully processed.
     */
    public final static int OK = 200;
    /**
     * The request cannot be fulfilled due to bad syntax. An accompanying error message will explain why.
     * For example invalid JSON format.
     */
    public final static int BadRequest = 400;
    /**
     * Unauthorized access.
     * Access token is expired or is invalid. A new access token must be aquired.
     */
    public final static int Unauthorized = 401;
    /**
     * Forbidden to access resource (invalid client-scopes and/or user-roles).
     * Authorization will not help and the request SHOULD NOT be repeated.
     */
    public final static int Forbidden = 403;
    /**
     * Something is broken. Please post to the group so the team can investigate.
     */
    public final static int InternalError = 500;
    /**
     * Signature in message is not correct. This normally indicates a bug in the client-software.
     */
    public final static int SignatureError = 800;
    protected String corr_id;
    protected String version;
    protected String namespace;
    protected String type;
    protected String error;
    protected Integer status;

    public AbstractApiResponse() {
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
