package com.nedap.retail.services.examples;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.epc.v2.difference_list.DifferenceListResponse;
import com.nedap.retail.messages.epc.v2.stock.StockResponse;
import com.sun.jersey.api.client.UniformInterfaceException;

public class EpcExample {

    public static void runExample(final Client client) {
        System.out.println("-------------");
        System.out.println("EPC API example");
        System.out.println("-------------");

        final String location = "http://location-testing";

        try {
            // get difference list
            System.out.println("------------- Retrieving difference list");
            final DifferenceListResponse dl = client.differenceList("12345678901231", null, null, null);
            System.out.println(dl.toString());

            // get stock gtin
            System.out.println("------------- Retrieving stock gtin");
            final StockResponse sg = client.stockGtin(location, null, null, null, null);
            System.out.println(sg.toString());

        } catch (final UniformInterfaceException e) {
            System.err.println("Server responded with an error:");
            System.err.println(e.getResponse().getEntity(String.class));
        }

        System.out.println("------------- Done");
    }
}