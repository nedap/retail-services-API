package com.nedap.retail.messages.stock;

public enum StockType {

    ERP(1), RFID_COUNT(2), REALTIME_SNAPSHOT(3);

    private final int type;

    StockType(final int type) {
        this.type = type;
    }

    public int getValue() {
        return type;
    }
}
