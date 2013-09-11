package com.nedap.retail.messages;

public class InvalidMessage extends Exception {

    private final int status;

    public InvalidMessage(String string, Throwable thrwbl) {
        this(ApiResponse.BadRequest, string, thrwbl);
    }

    public InvalidMessage(String string) {
        this(ApiResponse.BadRequest, string);
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
