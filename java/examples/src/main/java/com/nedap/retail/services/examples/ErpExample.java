package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.PrintHelper.COLON;
import static com.nedap.retail.services.examples.PrintHelper.DOUBLE_TAB;
import static com.nedap.retail.services.examples.PrintHelper.GTIN_1;
import static com.nedap.retail.services.examples.PrintHelper.GTIN_2;
import static com.nedap.retail.services.examples.PrintHelper.GTIN_3;
import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;
import static com.nedap.retail.services.examples.PrintHelper.TAB;
import static com.nedap.retail.services.examples.PrintHelper.WHITESPACE;

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

    private ErpExample() {
    }

    public static void runExample(final Client client) {
        System.out.println(NEW_LINE + "*** ERP API example ***");

        final String locationId = client.getSites().get(0).id;

        try {
            // Capture ERP stock
            System.out.println(NEW_LINE + "Capturing ERP stock...");
            final Stock erpStock = createErpStock(locationId);
            final String stockId = client.captureErpStock(erpStock);
            System.out.println("Captured ERP stock id: " + stockId);

            // Retrieve ERP stock
            System.out.println(NEW_LINE + "Retrieving ERP stock...");
            final Stock retrievedStock = client.retrieveErpStock(stockId);
            System.out.println("Retrieved ERP stock with:" + printStock(retrievedStock));

            // Get ERP stock summary status
            System.out.println(NEW_LINE + "Retrieving ERP stock status...");
            final StockSummary summary = client.getErpStockStatus(stockId);
            final Stock stock = castStockSummaryToStock(summary);
            System.out.println("Retrieved ERP stock summary with:" + printStock(stock));

            // Get ERP stock summary list
            System.out.println(NEW_LINE + "Retrieving ERP stock summary list...");
            final List<StockSummary> stocks = client.getErpStockList(makeStockSummaryListRequest(locationId));
            System.out.println(printStockSummaryList(stocks));

            System.out.println(NEW_LINE + "--- ERP API example finished ---");

        } catch (final ClientException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
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
        final StringBuilder sb = new StringBuilder();
        sb.append(NEW_LINE).append(TAB).append("Id: ").append(stock.id);
        sb.append(NEW_LINE).append(TAB).append("Location: ").append(stock.location);
        sb.append(NEW_LINE).append(TAB).append("Event time: ").append(stock.eventTime);
        sb.append(NEW_LINE).append(TAB).append("External reference: ").append(stock.externRef);
        sb.append(NEW_LINE).append(TAB).append("Status: ").append(stock.status);
        sb.append(NEW_LINE).append(TAB).append("Quantity: ").append(stock.quantity);
        sb.append(NEW_LINE).append(TAB).append("Excluded quantity: ").append(stock.excludedQuantity);
        sb.append(NEW_LINE).append(TAB).append("Gtin quantity: ").append(stock.gtinQuantity);
        sb.append(NEW_LINE).append(TAB).append("Excluded gtin quantity: ").append(stock.excludedGtinQuantity);
        sb.append(NEW_LINE).append(TAB).append("In use: ").append(stock.inUse);
        if (!CollectionUtils.isEmpty(stock.quantityList)) {
            sb.append(NEW_LINE).append(TAB).append("Quantity list:");
            sb.append(printQuantityList(stock.quantityList));
        }
        return sb.toString();
    }

    private static String printQuantityList(final List<GtinQuantity> quantityList) {
        final StringBuilder sb = new StringBuilder();
        for (final GtinQuantity gtinQuantity : quantityList) {
            sb.append(NEW_LINE).append(DOUBLE_TAB);
            sb.append(gtinQuantity.gtin).append(WHITESPACE).append(gtinQuantity.quantity);
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
        stock.excludedQuantity = summary.excludedQuantity;
        stock.gtinQuantity = summary.gtinQuantity;
        stock.excludedGtinQuantity = summary.excludedGtinQuantity;
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
        final StringBuilder sb = new StringBuilder("Retrieved ").append(stocks.size()).append(" ERP stock summaries");
        for (int i = 0; i < stocks.size(); i++) {
            sb.append(NEW_LINE).append(NEW_LINE).append(TAB);
            sb.append("Stock summary ").append(i + 1).append(COLON);
            final Stock stock = castStockSummaryToStock(stocks.get(i));
            sb.append(printStock(stock));
        }
        return sb.toString();
    }
}
