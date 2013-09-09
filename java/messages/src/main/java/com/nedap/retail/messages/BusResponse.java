package com.nedap.retail.messages;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Response message
 */
@XmlRootElement
public class BusResponse extends AbstractBusResponse {

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
     * Client/system has been successfully authenticated (i.e. valid OAuth access token)
     * but is not authorized to access resource (invalid client-scopes and/or user-roles).
     */
    public final static int Unauthorized = 401;
    /**
     * Indicates that the device is not signed-up in Cube-BUS.
     * The devices needs to send a sign-up message first.
     */
    public final static int Forbidded = 403;
    /**
     * Signature in message is not correct. This normally indicates a bug in the client-software.
     */
    public final static int SignatureError = 800;
    /**
     * Something is broken. Please post to the group so the Cube-BUS team can investigate.
     */
    public final static int InternalError = 500;
    final static int PAYLOAD_MAX_STRING = 140;
    final static String PAYLOAD_MAX_TRAIL = "...";
    protected Object result;
    protected Object payload;

    /**
     * Default constructor.
     */
    public BusResponse() {
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(namespace).append("/").append(type).append("/").append(version);
        sb.append("\n|   corr_id = ").append(corr_id);
        if (payload != null) {
            sb.append("\n|   payload = ").append(limit(payload));
        }
        if (result != null) {
            sb.append("\n|   result = ").append(limit(result));
        }
        if (status != null) {
            sb.append("\n|   status = ").append(status);
        }
        if (error != null) {
            sb.append("\n|   error = ").append(error);
        }
        return sb.toString();
    }

    private static String limit(Object value) {
        String resultS = value.toString();
        if (resultS.length() > PAYLOAD_MAX_STRING) {
            return resultS.substring(0, PAYLOAD_MAX_STRING) + PAYLOAD_MAX_TRAIL;
        } else {
            return resultS;
        }
    }
}
