package com.nedap.retail.services.examples;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.stock.GtinQuantity;
import com.nedap.retail.messages.stock.Stock;
import com.nedap.retail.messages.stock.StockSummary;
import com.sun.jersey.api.client.UniformInterfaceException;

public class ErpExample {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String TAB = "\t";
    private static final String DOUBLE_TAB = "\t\t";
    private static final String GTIN_1 = "02011200000019";
    private static final String GTIN_2 = "02011200000064";
    private static final String GTIN_3 = "02011200000163";

    public static void runExample(final Client client) {
        System.out.println(NEW_LINE + "*** ERP API example ***");

        final String location = client.getSites().get(0).getId();

        try {
            // Capture ERP stock
            System.out.println(NEW_LINE + "Capturing ERP stock...");
            final Stock erpStock = createErpStock(location);
            final String stockId = client.captureErpStock(erpStock);
            System.out.println("Captured ERP stock id: " + stockId);

            // Retrieve ERP stock
            System.out.println(NEW_LINE + "Retrieving ERP stock...");
            final Stock retrievedStock = client.retrieveErpStock(stockId);
            System.out.println(printStock(retrievedStock));

            // Get ERP stock summary status
            System.out.println(NEW_LINE + "Getting ERP stock status...");
            final StockSummary summary = client.getErpStockStatus(stockId);
            final Stock stock = castStockSummaryToStock(summary);
            System.out.println(printStock(stock));

            // Get ERP stock summary list
            System.out.println("Retrieving list of available stocks...");
            final List<StockSummary> availableStocks = client.getErpStockList(location);
            System.out.println("Got " + availableStocks.size() + " stocks:");
            for (final StockSummary availableStock : availableStocks) {
                System.out.println(availableStock);
            }

            System.out.println(NEW_LINE + "--- ERP API example finished ---");

        } catch (final UniformInterfaceException e) {
            System.err.println("Server responded with an error: " + e.getResponse().getEntity(String.class));
        }
    }

    private static Stock createErpStock(final String locationId) {
        final Stock stock = new Stock();
        stock.location = locationId;
        stock.eventTime = DateTime.now();
        stock.externRef = "generated_import";
        stock.quantityList = Arrays.asList(new GtinQuantity(GTIN_1, 5), new GtinQuantity(GTIN_2, 10), new GtinQuantity(
                GTIN_3, 20));
        return stock;
    }

    private static String printStock(final Stock stock) {
        final StringBuilder sb = new StringBuilder("Retrieved ERP stock with:");
        sb.append(NEW_LINE + TAB + "Id: " + stock.id);
        sb.append(NEW_LINE + TAB + "Location: " + stock.location);
        sb.append(NEW_LINE + TAB + "Event time: " + stock.eventTime);
        sb.append(NEW_LINE + TAB + "External reference: " + stock.externRef);
        sb.append(NEW_LINE + TAB + "Status: " + stock.status);
        sb.append(NEW_LINE + TAB + "Quantity: " + stock.quantity);
        sb.append(NEW_LINE + TAB + "Gtin quantity: " + stock.gtinQuantity);
        sb.append(NEW_LINE + TAB + "In use: " + stock.inUse);
        sb.append(NEW_LINE + TAB + "Quantity list:");
        if (!CollectionUtils.isEmpty(stock.quantityList)) {
            sb.append(printQuantityList(stock.quantityList));
        }
        return sb.toString();
    }

    private static String printQuantityList(final List<GtinQuantity> quantityList) {
        final StringBuilder sb = new StringBuilder();
        for (final GtinQuantity gtinQuantity : quantityList) {
            sb.append(NEW_LINE + DOUBLE_TAB + gtinQuantity.gtin + " " + gtinQuantity.quantity);
        }
        return sb.toString();
    }

    private static Stock castStockSummaryToStock(final StockSummary summary) {
        final Stock stock = new Stock();
        stock.id = summary.id;
        stock.location = summary.location;
        stock.eventTime = summary.eventTime;

        return stock;
    }
}
