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

    public static StockType getStockTypeFromValue(int value) {
        for (StockType type : StockType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Value " + value + " cannot be converted to a StockType.");
    }
}
