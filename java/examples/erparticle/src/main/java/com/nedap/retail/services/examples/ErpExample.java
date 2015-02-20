package com.nedap.retail.services.examples;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.stock.GtinQuantity;
import com.nedap.retail.messages.stock.Stock;
import com.nedap.retail.messages.stock.StockSummary;
import com.sun.jersey.api.client.UniformInterfaceException;

public class ErpExample {
    public static void runExample(final Client client) {
        System.out.println("-------------");
        System.out.println("ERP API example");
        System.out.println("-------------");

        // this is our example data
        final String location = "http://nedapretail.com/loc/test";
        final List<GtinQuantity> exampleStock = new ArrayList<>();
        exampleStock.add(new GtinQuantity("12345678901231", 23));
        exampleStock.add(new GtinQuantity("12345678901248", 3));
        exampleStock.add(new GtinQuantity("12345678901255", -3));
        exampleStock.add(new GtinQuantity("12345678901262", 17));
        final String myReference = "testing";
        final DateTime timeOfStock = new DateTime();

        try {
            // send stock
            System.out.println("------------- Uploading stock");
            final Stock stock = new Stock(location, timeOfStock, myReference, exampleStock);
            final String stockId = client.captureErpStock(stock);
            System.out.println("stock ID = " + stockId);

            // request stock status
            System.out.println("------------- Getting stock status");
            final StockSummary summary = client.getErpStockStatus(stockId);
            System.out.println(summary);

            // request stock
            System.out.println("------------- Retrieving stock");
            final Stock retrievedStock = client.retrieveErpStock(stockId);
            System.out.println(retrievedStock);

            // request stock list
            System.out.println("------------- Retrieving list of available stocks");
            final List<StockSummary> availableStocks = client.getErpStockList(location);
            System.out.println("Got " + availableStocks.size() + " stocks:");
            for (final StockSummary availableStock : availableStocks) {
                System.out.println(availableStock);
            }

        } catch (final UniformInterfaceException e) {
            System.err.println("Server responded with an error:");
            System.err.println(e.getResponse().getEntity(String.class));
        }
        System.out.println("------------- Done");
    }
}