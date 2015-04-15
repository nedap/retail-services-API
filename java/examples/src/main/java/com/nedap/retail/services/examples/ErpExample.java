package com.nedap.retail.services.examples;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.messages.stock.GtinQuantity;
import com.nedap.retail.messages.stock.Stock;
import com.nedap.retail.messages.stock.StockSummary;
import com.nedap.retail.messages.stock.StockSummaryListRequest;

public class ErpExample {

    public static void runExample(final Client client) {
        System.out.println(PrintHelper.NEW_LINE + "*** ERP API example ***");

        final String locationId = client.getSites().get(0).getId();

        try {
            // Capture ERP stock
            System.out.println(PrintHelper.NEW_LINE + "Capturing ERP stock...");
            final Stock erpStock = createErpStock(locationId);
            final String stockId = client.captureErpStock(erpStock);
            System.out.println("Captured ERP stock id: " + stockId);

            // Retrieve ERP stock
            System.out.println(PrintHelper.NEW_LINE + "Retrieving ERP stock...");
            final Stock retrievedStock = client.retrieveErpStock(stockId);
            System.out.println("Retrieved ERP stock with:" + printStock(retrievedStock));

            // Get ERP stock summary status
            System.out.println(PrintHelper.NEW_LINE + "Retrieving ERP stock status...");
            final StockSummary summary = client.getErpStockStatus(stockId);
            final Stock stock = castStockSummaryToStock(summary);
            System.out.println("Retrieved ERP stock summary with:" + printStock(stock));

            // Get ERP stock summary list
            System.out.println(PrintHelper.NEW_LINE + "Retrieving ERP stock summary list...");
            final List<StockSummary> stocks = client.getErpStockList(makeStockSummaryListRequest(locationId));
            System.out.println(printStockSummaryList(stocks));

            System.out.println(PrintHelper.NEW_LINE + "--- ERP API example finished ---");

        } catch (final ClientException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
        }
    }

    private static Stock createErpStock(final String locationId) {
        final Stock stock = new Stock();
        stock.location = locationId;
        stock.eventTime = DateTime.now();
        stock.externRef = "generated_import";
        stock.quantityList = Arrays.asList(new GtinQuantity(PrintHelper.GTIN_1, 5), new GtinQuantity(
                PrintHelper.GTIN_2, 10), new GtinQuantity(PrintHelper.GTIN_3, 20));
        return stock;
    }

    private static String printStock(final Stock stock) {
        final StringBuilder sb = new StringBuilder();
        sb.append(PrintHelper.NEW_LINE + PrintHelper.TAB + "Id: " + stock.id);
        sb.append(PrintHelper.NEW_LINE + PrintHelper.TAB + "Location: " + stock.location);
        sb.append(PrintHelper.NEW_LINE + PrintHelper.TAB + "Event time: " + stock.eventTime);
        sb.append(PrintHelper.NEW_LINE + PrintHelper.TAB + "External reference: " + stock.externRef);
        sb.append(PrintHelper.NEW_LINE + PrintHelper.TAB + "Status: " + stock.status);
        sb.append(PrintHelper.NEW_LINE + PrintHelper.TAB + "Quantity: " + stock.quantity);
        sb.append(PrintHelper.NEW_LINE + PrintHelper.TAB + "Gtin quantity: " + stock.gtinQuantity);
        sb.append(PrintHelper.NEW_LINE + PrintHelper.TAB + "In use: " + stock.inUse);
        if (!CollectionUtils.isEmpty(stock.quantityList)) {
            sb.append(PrintHelper.NEW_LINE + PrintHelper.TAB + "Quantity list:");
            sb.append(printQuantityList(stock.quantityList));
        }
        return sb.toString();
    }

    private static String printQuantityList(final List<GtinQuantity> quantityList) {
        final StringBuilder sb = new StringBuilder();
        for (final GtinQuantity gtinQuantity : quantityList) {
            sb.append(PrintHelper.NEW_LINE + PrintHelper.DOUBLE_TAB);
            sb.append(gtinQuantity.gtin + PrintHelper.WHITESPACE + gtinQuantity.quantity);
        }
        return sb.toString();
    }

    private static Stock castStockSummaryToStock(final StockSummary summary) {
        final Stock stock = new Stock();
        stock.id = summary.id;
        stock.location = summary.location;
        stock.eventTime = summary.eventTime;
        stock.externRef = summary.externRef;
        stock.status = summary.status;
        stock.quantity = summary.quantity;
        stock.gtinQuantity = summary.gtinQuantity;
        stock.inUse = summary.inUse;
        return stock;
    }

    private static StockSummaryListRequest makeStockSummaryListRequest(final String location) {
        final StockSummaryListRequest request = new StockSummaryListRequest();
        request.location = location;
        request.fromEventTime = DateTime.now().minusMinutes(10);
        return request;
    }

    private static String printStockSummaryList(final List<StockSummary> stocks) {
        final StringBuilder sb = new StringBuilder("Retrieved " + stocks.size() + " ERP stock summaries");
        for (int i = 0; i < stocks.size(); i++) {
            sb.append(PrintHelper.NEW_LINE + PrintHelper.NEW_LINE + PrintHelper.TAB);
            sb.append("Stock summary " + (i + 1) + PrintHelper.COLON);
            final Stock stock = castStockSummaryToStock(stocks.get(i));
            sb.append(printStock(stock));
        }
        return sb.toString();
    }
}
