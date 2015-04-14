package com.nedap.retail.services.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.Barcode;
import com.nedap.retail.messages.article.Size;
import com.nedap.retail.messages.epc.v2.approved_difference_list.ApprovedDifferenceListSummary;
import com.nedap.retail.messages.epc.v2.approved_difference_list.request.ApprovedDifferenceListCaptureRequest;
import com.nedap.retail.messages.epc.v2.approved_difference_list.response.ApprovedDifferenceListExportResponse;
import com.nedap.retail.messages.epc.v2.approved_difference_list.response.ApprovedDifferenceListResponse;
import com.nedap.retail.messages.epc.v2.difference_list.DifferenceListResponse;
import com.nedap.retail.messages.epc.v2.stock.NotOnShelfResponse;
import com.nedap.retail.messages.epc.v2.stock.StockResponse;
import com.nedap.retail.messages.stock.GtinQuantity;
import com.nedap.retail.messages.stock.Stock;

public class EpcExample {

    private static final DateTime EVENT_TIME = DateTime.now();
    private static final String EXTERNAL_REFERENCE = "generated_import";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String TAB = "\t";
    private static final String DOUBLE_TAB = "\t\t";
    private static final String GTIN_1 = "02011200000019";
    private static final String GTIN_2 = "02011200000064";
    private static final String GTIN_3 = "02011200000163";
    private static final String COMMA = ", ";
    private static final String WHITESPACE = " ";

    public static void runExample(final Client client) {
        System.out.println(NEW_LINE + "*** EPC API example ***");

        final String location = client.getSites().get(0).getId();

        try {
            // Create some articles
            System.out.println(NEW_LINE + "Creating some articles...");
            client.captureArticles(Arrays.asList(createArticle1(), createArticle2(), createArticle3()));
            System.out.println("Created test articles with gtins: " + GTIN_1 + COMMA + GTIN_2 + COMMA + GTIN_3);

            // Create some erp stock
            System.out.println(NEW_LINE + "Creating some ERP stock...");
            final String erpStockId = client.captureErpStock(createErpStock(location));
            System.out.println("Created ERP stock with id: " + erpStockId);

            // Create some rfid stock
            System.out.println(NEW_LINE + "Creating some RFID stock...");
            final String rfidStockId = client.captureRfidStock(createRfidStock(location));
            System.out.println("Created RFID stock with id: " + rfidStockId);

            // Stock (GTIN based)
            System.out.println(NEW_LINE + "Retrieving stock gtin...");
            final StockResponse stockResponse = client.stockGtin(location, null, null, null, null);
            System.out.println(printStockResponse(stockResponse));

            // Not on shelf
            // System.out.println(NEW_LINE + "Retrieving not on shelf items list...");
            // final NotOnShelfResponse notOnShelfResponse = client.notOnShelf(new NotOnShelfRequest(location, false));
            // System.out.println(printNotOnShelfResponse(notOnShelfResponse));

            // Difference list
            System.out.println(NEW_LINE + "Retrieving difference list...");
            final DifferenceListResponse differenceList = client.differenceList(erpStockId, EVENT_TIME, null, null);
            System.out.println(printDifferenceList(differenceList));

            // Capture approved difference list
            System.out.println(NEW_LINE + "Capturing difference list...");
            final String approvedDifferenceListId = client
                    .captureApprovedDifferenceList(makeApprovedDifferenceListCaptureRequest(erpStockId, location));
            System.out.println("Captured approved difference list with id: " + approvedDifferenceListId);

            // Retrieve approved difference list
            System.out.println(NEW_LINE + "Retrieving approved difference list...");
            final ApprovedDifferenceListResponse approvedDifferenceList = client
                    .getApprovedDifferenceList(approvedDifferenceListId);
            System.out.println(printApprovedDifferenceList(approvedDifferenceList));

            // Export approved difference list
            System.out.println(NEW_LINE + "Exporting approved difference list...");
            final ApprovedDifferenceListExportResponse approvedDifferenceListExportResponse = client
                    .approvedDifferenceListExport(approvedDifferenceListId);
            System.out.println(printApprovedDifferenceListExportResponse(approvedDifferenceListExportResponse));

            // List of approved difference list summaries
            System.out.println(NEW_LINE + "Getting list of approved difference list summaries...");
            final List<ApprovedDifferenceListSummary> approvedDifferenceListSummaries = client
                    .getApprovedDifferenceListSummaries(location, null, null);
            System.out.println(printListOfApprovedDifferenceListSummaries(approvedDifferenceListSummaries, location));

            // Status of approved difference list
            System.out.println(NEW_LINE + "Retrieving status of approved difference list...");
            final ApprovedDifferenceListSummary approvedDifferenceListStatus = client.getApprovedDifferenceListStatus(
                    location, EVENT_TIME.toString());
            System.out.println(printApprovedDifferenceListStatus(approvedDifferenceListStatus));

            // Delete approved difference list
            System.out.println(NEW_LINE + "Deleting approved difference list...");
            client.deleteApprovedDifferenceList(UUID.fromString(approvedDifferenceListId));
            System.out.println("Approved difference list with id: " + approvedDifferenceListId + " has been deleted");

            System.out.println(NEW_LINE + "--- EPC API example finished ---");

        } catch (final ClientException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
        }
    }

    private static Article createArticle1() {
        final Article article = new Article();

        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode("EAN13", "2011200000019"));
        article.setBarcodes(barcodes);

        article.setGtin(GTIN_1);
        article.setName("T-shirt with V-neck and short sleeves");
        article.setColor("Black");
        article.setSizes(setArticleSizes1());

        return article;
    }

    private static List<Size> setArticleSizes1() {
        final List<Size> sizes = new ArrayList<>();
        sizes.add(new Size("32/35", "NL"));
        sizes.add(new Size("32/33", "DE"));
        sizes.add(new Size("31/35", "GB"));
        return sizes;
    }

    private static Article createArticle2() {
        final Article article = new Article();

        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode("EAN13", "2011200000064"));
        article.setBarcodes(barcodes);

        article.setGtin(GTIN_2);
        article.setName("Straight regular jeans");
        article.setColor("Black");
        article.setSizes(setArticleSizes2());

        return article;
    }

    private static List<Size> setArticleSizes2() {
        final List<Size> sizes = new ArrayList<>();
        sizes.add(new Size("31/35", "NL"));
        sizes.add(new Size("31/33", "DE"));
        sizes.add(new Size("31/35", "GB"));
        return sizes;
    }

    private static Article createArticle3() {
        final Article article = new Article();

        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode("EAN13", "2011200000163"));
        article.setBarcodes(barcodes);

        article.setGtin(GTIN_3);
        article.setName("Washed leather in a classic maritime style shoes");
        article.setColor("Black");
        article.setSizes(setArticleSizes3());

        return article;
    }

    private static List<Size> setArticleSizes3() {
        final List<Size> sizes = new ArrayList<>();
        sizes.add(new Size("41-46", "NL"));
        sizes.add(new Size("40-45", "DE"));
        sizes.add(new Size("40-45", "GB"));
        return sizes;
    }

    private static Stock createErpStock(final String location) {
        final Stock erpStock = new Stock();
        erpStock.location = location;
        erpStock.eventTime = EVENT_TIME;
        erpStock.externRef = EXTERNAL_REFERENCE;
        erpStock.quantityList = Arrays.asList(new GtinQuantity(GTIN_1, 5), new GtinQuantity(GTIN_2, 10),
                new GtinQuantity(GTIN_3, 20));
        return erpStock;
    }

    private static Stock createRfidStock(final String location) {
        final Stock erpStock = new Stock();
        erpStock.location = location;
        erpStock.eventTime = EVENT_TIME;
        erpStock.externRef = EXTERNAL_REFERENCE;
        erpStock.quantityList = Arrays.asList(new GtinQuantity(GTIN_1, 7), new GtinQuantity(GTIN_2, 15),
                new GtinQuantity(GTIN_3, 17));
        return erpStock;
    }

    private static String printStockResponse(final StockResponse stockResponse) {
        final StringBuilder sb = new StringBuilder("Quantities for a given gtins:");
        for (int i = 0; i < stockResponse.gtins.size(); i++) {
            sb.append(NEW_LINE + TAB);
            sb.append(stockResponse.gtins.get(i) + " " + stockResponse.quantities.get(i));
        }
        return sb.toString();
    }

    private static String printNotOnShelfResponse(final NotOnShelfResponse notOnShelfResponse) {
        final StringBuilder sb = new StringBuilder("Not on shelf response:" + NEW_LINE);
        for (int i = 0; i < notOnShelfResponse.gtins.size(); i++) {
            sb.append(TAB + "For gtin " + notOnShelfResponse.gtins.get(i) + ":" + NEW_LINE);
            sb.append(DOUBLE_TAB + "Stock room quantuty: " + notOnShelfResponse.stockRoomStock.get(i) + NEW_LINE);
            sb.append(DOUBLE_TAB + "Sales floor quantity: " + notOnShelfResponse.salesFloorStock.get(i) + NEW_LINE);
        }
        sb.append(TAB + "Total difference: " + notOnShelfResponse.notOnShelfPercentage);
        return sb.toString();
    }

    private static String printDifferenceList(final DifferenceListResponse differenceList) {
        final StringBuilder sb = new StringBuilder("Differences for a given gtins are:");
        for (int i = 0; i < differenceList.getGtins().size(); i++) {
            sb.append(NEW_LINE + TAB);
            sb.append("For gtin: " + differenceList.getGtins().get(i) + WHITESPACE);
            sb.append("ERP stock quantity is: " + differenceList.getErpStock().get(i) + COMMA);
            sb.append("RFID stock quantity is: " + differenceList.getRfidStock().get(i));
        }
        return sb.toString();
    }

    private static ApprovedDifferenceListCaptureRequest makeApprovedDifferenceListCaptureRequest(
            final String erpStockId, final String location) {

        final ApprovedDifferenceListCaptureRequest approvedDifferenceListCaptureRequest = new ApprovedDifferenceListCaptureRequest();
        approvedDifferenceListCaptureRequest.rfidTime = EVENT_TIME;
        approvedDifferenceListCaptureRequest.erpStockId = erpStockId;
        approvedDifferenceListCaptureRequest.location = location;
        approvedDifferenceListCaptureRequest.approvedGtins = Arrays.asList(GTIN_1, GTIN_2, GTIN_3);
        return approvedDifferenceListCaptureRequest;
    }

    private static String printApprovedDifferenceList(final ApprovedDifferenceListResponse approvedDifferenceList) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Absolute difference: " + approvedDifferenceList.absoluteDifference + NEW_LINE);
        sb.append("Plus difference: " + approvedDifferenceList.plusDifference + NEW_LINE);
        sb.append("Minus difference: " + approvedDifferenceList.minusDifference);
        return sb.toString();
    }

    private static String printApprovedDifferenceListExportResponse(final ApprovedDifferenceListExportResponse response) {
        final StringBuilder sb = new StringBuilder("Approved difference list export status:");
        for (int i = 0; i < response.gtins.size(); i++) {
            sb.append(NEW_LINE + TAB);
            sb.append("for gtin: " + response.gtins.get(i));
            sb.append(" approved quantity is: " + response.approvedQuantity.get(i));
        }
        return sb.toString();
    }

    private static String printListOfApprovedDifferenceListSummaries(
            final List<ApprovedDifferenceListSummary> response, final String location) {

        final StringBuilder sb = new StringBuilder("Approved difference lists ids for location: " + location + " are:");
        for (final ApprovedDifferenceListSummary list : response) {
            sb.append(NEW_LINE);
            sb.append(TAB + list.id);
        }
        return sb.toString();
    }

    private static String printApprovedDifferenceListStatus(
            final ApprovedDifferenceListSummary approvedDifferenceListStatus) {

        final StringBuilder sb = new StringBuilder("Approved difference list status:");
        sb.append(NEW_LINE + TAB + "Id: " + approvedDifferenceListStatus.id);
        sb.append(NEW_LINE + TAB + "Approved on: " + approvedDifferenceListStatus.approvedOn);
        sb.append(NEW_LINE + TAB + "Location: " + approvedDifferenceListStatus.location);
        sb.append(NEW_LINE + TAB + "Export status: " + approvedDifferenceListStatus.exportStatus);
        return sb.toString();
    }
}
