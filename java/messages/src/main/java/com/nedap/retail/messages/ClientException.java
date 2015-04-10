package com.nedap.retail.messages;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

public class ClientException extends RuntimeException {

    private static final long serialVersionUID = -7956896457958967026L;

    private final int statusCode;

    private final String errorReason;

    public ClientException(final String message, final int statusCode, final String errorReason) {
        super(message);
        this.statusCode = statusCode;
        this.errorReason = errorReason;
    }

    public ClientException(final WebApplicationException webApplicationException) {
        super(webApplicationException);
        final Response response = webApplicationException.getResponse();
        statusCode = response.getStatus();
        errorReason = extractErrorReason(response);
    }

    private String extractErrorReason(final Response response) {
        try {
            return response.readEntity(String.class);
        } catch (final Exception ex) {
            return null;
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorReason() {
        return errorReason;
    }

    @Override
    public String getMessage() {
        String errorMessage = super.getMessage();
        if (StringUtils.isNotBlank(this.getErrorReason())) {
            errorMessage += " with payload: " + this.getErrorReason();
        }
        return errorMessage;
    }
}
