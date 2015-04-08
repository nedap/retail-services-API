package com.nedap.retail.services.examples;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.messages.epc.v2.difference_list.DifferenceListResponse;
import com.nedap.retail.messages.epc.v2.stock.StockResponse;

public class EpcExample {

    private static final String LOCATION = "http://retailer.com/loc/123";
    private static final String ERP_STOCK_ID = "72c49313dc6c0f55500974fbe6968606cd0076bdf004884dbb4b6a85291712f5";

    public static void runExample(final Client client) {
        System.out.println("*** EPC API example ***");

        try {
            // get difference list
            System.out.println("Retrieving difference list...");
            final DifferenceListResponse dl = client.differenceList(ERP_STOCK_ID, null, null, null);
            System.out.println(dl.toString());

            // get stock gtin
            System.out.println("Retrieving stock gtin...");
            final StockResponse sg = client.stockGtin(LOCATION, null, null, null, null);
            System.out.println(sg.toString());
            System.out.println("--- Done ---");

        } catch (final ClientException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
        }
    }
}
