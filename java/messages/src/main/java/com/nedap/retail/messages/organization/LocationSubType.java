package com.nedap.retail.messages.organization;

public enum LocationSubType {

    // For location type SITE the following subtypes are available:
    DEMO,
    DISTRIBUTION_CENTRE,
    DIY_SHOP,
    ELECTRONICS,
    FASHION,
    HEALTH_BEAUTY,
    OFFICE,
    PRINT_SHOP,
    PRODUCTION_FACILITY,
    SHOPPING_MALL,
    SPORT,
    SUPERMARKET,
    OTHER,

    // For location type LOCATION, LOCATION_INTERNAL and LOCATION_EXTERNAL the following subtypes are available:
    GOODS_RECEIVING_AREA, 
    STOCK_ROOM, 
    SALES_FLOOR, 
    SHOP_WINDOW, 
    MANNEQUIN, 
    FITTING_ROOM, 
    POINT_OF_SALE, 
    CUSTOMER_ENTRANCE, 
    EMPLOYEE_ENTRANCE, 
    STOCK_ROOM_TO_SALES_FLOOR, 
    GOODS_RECEIVING_ENTRANCE
}
