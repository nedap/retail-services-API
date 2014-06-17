package com.nedap.retail.messages;

public class InvalidMessage extends Exception {

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
     * Access token is expired or is invalid. A new access token must be acquired.
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

    private final int status;

    public InvalidMessage(String string, Throwable thrwbl) {
        this(BadRequest, string, thrwbl);
    }

    public InvalidMessage(String string) {
        this(BadRequest, string);
    }

    public InvalidMessage(int status, String string, Throwable thrwbl) {
        super(string, thrwbl);
        this.status = status;
    }

    public InvalidMessage(int status, String string) {
        super(string);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
