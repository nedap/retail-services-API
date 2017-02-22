package com.nedap.retail.services.examples;

import com.nedap.retail.client.ApiClient;
import com.nedap.retail.client.ApiException;
import com.nedap.retail.client.api.ErpStockApi;
import com.nedap.retail.client.model.GtinQuantity;
import com.nedap.retail.client.model.Stock;
import com.nedap.retail.client.model.StockSummary;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

import static com.nedap.retail.services.examples.PrintHelper.*;

public class ErpExample {

    private ErpExample() {
    }

    public static void runExample(final ApiClient client) {
        System.out.println(NEW_LINE + "*** ERP API example ***");

        final String locationId = "http://nedapretail.com/loc/testing";
        final ErpStockApi erpStockApi = new ErpStockApi(client);

        try {
            // Capture ERP stock
            System.out.println(NEW_LINE + "Capturing ERP stock...");
            final Stock erpStock = createErpStock(locationId);
            final String stockId = erpStockApi.erpStockCreate(erpStock).getId();
            System.out.println("Captured ERP stock id: " + stockId);

            // Retrieve ERP stock
            System.out.println(NEW_LINE + "Retrieving ERP stock...");
            final Stock retrievedStock = erpStockApi.erpStockRetrieve(stockId, true);
            System.out.println("Retrieved ERP stock with:" + printStock(retrievedStock));

            // Get ERP stock summary status
            System.out.println(NEW_LINE + "Retrieving ERP stock status...");
            final StockSummary summary = erpStockApi.erpStockRetrieveSummary(stockId);
            final Stock stock = castStockSummaryToStock(summary);
            System.out.println("Retrieved ERP stock summary with:" + printStock(stock));

            // Get ERP stock summary list
            System.out.println(NEW_LINE + "Retrieving ERP stock summary list...");
            final List<StockSummary> stocks = erpStockApi.erpStockList(locationId,
                    DateTime.now().minusMinutes(10),null);
            System.out.println(printStockSummaryList(stocks));

            System.out.println(NEW_LINE + "--- ERP API example finished ---");

        } catch (final ApiException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
        }
    }

    private static Stock createErpStock(final String locationId) {
        final Stock stock = new Stock();
        stock.location(locationId);
        stock.eventTime(DateTime.now());
        stock.externRef("generated_import");
        stock.quantityList(Arrays.asList(new GtinQuantity().gtin(GTIN_1).quantity(5), new GtinQuantity().gtin(GTIN_2)
                        .quantity(10),new GtinQuantity().gtin(GTIN_3).quantity(20)));
        return stock;
    }

    private static String printStock(final Stock stock) {
        final StringBuilder sb = new StringBuilder();
        sb.append(NEW_LINE).append(TAB).append("Id: ").append(stock.getId());
        sb.append(NEW_LINE).append(TAB).append("Location: ").append(stock.getLocation());
        sb.append(NEW_LINE).append(TAB).append("Event time: ").append(stock.getEventTime());
        sb.append(NEW_LINE).append(TAB).append("External reference: ").append(stock.getExternRef());
        sb.append(NEW_LINE).append(TAB).append("Status: ").append(stock.getStatus());
        sb.append(NEW_LINE).append(TAB).append("Quantity: ").append(stock.getQuantity());
        sb.append(NEW_LINE).append(TAB).append("Excluded quantity: ").append(stock.getExcludedQuantity());
        sb.append(NEW_LINE).append(TAB).append("Gtin quantity: ").append(stock.getGtinQuantity());
        sb.append(NEW_LINE).append(TAB).append("Excluded gtin quantity: ").append(stock.getExcludedGtinQuantity());
        sb.append(NEW_LINE).append(TAB).append("In use: ").append(stock.getInUse());
        if (!CollectionUtils.isEmpty(stock.getQuantityList())) {
            sb.append(NEW_LINE).append(TAB).append("Quantity list:");
            sb.append(printQuantityList(stock.getQuantityList()));
        }
        return sb.toString();
    }

    private static String printQuantityList(final List<GtinQuantity> quantityList) {
        final StringBuilder sb = new StringBuilder();
        for (final GtinQuantity gtinQuantity : quantityList) {
            sb.append(NEW_LINE).append(DOUBLE_TAB);
            sb.append(gtinQuantity.getGtin()).append(WHITESPACE).append(gtinQuantity.getQuantity());
        }
        return sb.toString();
    }

    private static Stock castStockSummaryToStock(final StockSummary summary) {
        final Stock stock = new Stock();
        stock.id(summary.getId());
        stock.location(summary.getLocation());
        stock.eventTime(summary.getEventTime());
        stock.externRef(summary.getExternRef());
        stock.status(Stock.StatusEnum.fromValue(summary.getStatus().toString()));
        stock.quantity(summary.getQuantity());
        stock.excludedQuantity(summary.getExcludedQuantity());
        stock.gtinQuantity(summary.getGtinQuantity());
        stock.excludedGtinQuantity(summary.getExcludedGtinQuantity());
        stock.inUse(summary.getInUse());
        return stock;
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
