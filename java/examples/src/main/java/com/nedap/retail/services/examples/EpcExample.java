package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.PrintHelper.BLACK;
import static com.nedap.retail.services.examples.PrintHelper.COLON;
import static com.nedap.retail.services.examples.PrintHelper.COMMA;
import static com.nedap.retail.services.examples.PrintHelper.DOUBLE_TAB;
import static com.nedap.retail.services.examples.PrintHelper.EAN13;
import static com.nedap.retail.services.examples.PrintHelper.GTIN_1;
import static com.nedap.retail.services.examples.PrintHelper.GTIN_2;
import static com.nedap.retail.services.examples.PrintHelper.GTIN_3;
import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;
import static com.nedap.retail.services.examples.PrintHelper.TAB;
import static com.nedap.retail.services.examples.PrintHelper.WHITESPACE;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.Barcode;
import com.nedap.retail.messages.article.Size;
import com.nedap.retail.messages.epc.v2.approved_difference_list.ApprovedDifferenceListSummary;
import com.nedap.retail.messages.epc.v2.approved_difference_list.ExportStatus;
import com.nedap.retail.messages.epc.v2.approved_difference_list.request.ApprovedDifferenceListCaptureRequest;
import com.nedap.retail.messages.epc.v2.approved_difference_list.response.ApprovedDifferenceListExportResponse;
import com.nedap.retail.messages.epc.v2.approved_difference_list.response.ApprovedDifferenceListResponse;
import com.nedap.retail.messages.epc.v2.difference_list.DifferenceListResponse;
import com.nedap.retail.messages.epc.v2.expected_stock.ExpectedStock;
import com.nedap.retail.messages.epc.v2.stock.NotOnShelfRequest;
import com.nedap.retail.messages.epc.v2.stock.NotOnShelfResponse;
import com.nedap.retail.messages.epc.v2.stock.StockResponse;
import com.nedap.retail.messages.organization.Location;
import com.nedap.retail.messages.organization.LocationSubType;
import com.nedap.retail.messages.stock.GtinQuantity;
import com.nedap.retail.messages.stock.Stock;

public class EpcExample {

    private static final DateTime EVENT_TIME = DateTime.now();
    private static final String EXTERNAL_REFERENCE = "generated_import";

    private EpcExample() {
    }

    public static void runExample(final Client client) {
        System.out.println(NEW_LINE + "*** EPC API example ***");

        final Location location = client.getSites().get(0);
        final String locationId = location.id;
        System.out.println("Using location " + location.name);

        try {
            // Create some articles
            System.out.println(NEW_LINE + "Creating some articles...");
            client.captureArticles(Arrays.asList(createArticle1(), createArticle2(), createArticle3()));
            System.out.println(printCreatedArticlesGtins());

            // Create some erp stock
            System.out.println(NEW_LINE + "Creating some ERP stock...");
            final String erpStockId = client.captureErpStock(createErpStock(locationId));
            System.out.println("Created ERP stock with id: " + erpStockId);

            // Create some rfid stock
            System.out.println(NEW_LINE + "Creating some RFID stock...");
            final String rfidStockId = client.captureRfidStock(createRfidStock(locationId));
            System.out.println("Created RFID stock with id: " + rfidStockId);

            // Check if location has stock room and sales floor sublocations
            final List<String> sublocationIds = getSublocationIds(locationId, client);
            if (sublocationIds.size() > 1) {

                // Create some stock differences between the two
                System.out.println(NEW_LINE + "Creating some RFID stock for sublocations...");
                final String salesFloorRfidStockHistoryId = client
                        .captureRfidStock(createSalesFloorRfidStockHistory(sublocationIds.get(0)));
                final String stockRoomRfidStockId = client
                        .captureRfidStock(createStockRoomRfidStock(sublocationIds.get(1)));
                final String salesFloorRfidStockPresentId = client
                        .captureRfidStock(createSalesFloorRfidStockPresent(sublocationIds.get(0)));
                final List<String> generatedStockIds = Arrays.asList(salesFloorRfidStockHistoryId, stockRoomRfidStockId,
                        salesFloorRfidStockPresentId);
                System.out.println(printSublocationStocks(sublocationIds, generatedStockIds));

                // Not on shelf
                System.out.println(NEW_LINE + "Retrieving not on shelf items list...");
                final NotOnShelfResponse notOnShelfResponse = client
                        .notOnShelf(new NotOnShelfRequest(locationId, false));
                System.out.println(printNotOnShelfResponse(notOnShelfResponse));
            }

            // Stock (GTIN based)
            System.out.println(NEW_LINE + "Retrieving stock gtin...");
            final StockResponse stockResponse = client.stockGtin(locationId, null, null, null, null);
            System.out.println(printStockResponse(stockResponse));

            // Difference list
            System.out.println(NEW_LINE + "Retrieving difference list...");
            final DifferenceListResponse differenceList = client.differenceList(erpStockId, EVENT_TIME, null, null);
            System.out.println(printDifferenceList(differenceList));

            // Capture approved difference list
            System.out.println(NEW_LINE + "Capturing difference list...");
            final String approvedDifferenceListId = client
                    .captureApprovedDifferenceList(makeApprovedDifferenceListCaptureRequest(erpStockId, locationId));
            System.out.println("Captured approved difference list with id: " + approvedDifferenceListId);

            // Retrieve approved difference list
            System.out.println(NEW_LINE + "Retrieving approved difference list...");
            final ApprovedDifferenceListResponse approvedDifferenceList = client
                    .getApprovedDifferenceList(approvedDifferenceListId);
            System.out.println(printApprovedDifferenceList(approvedDifferenceList));

            // Download approved difference list in CSV format
            System.out.println(NEW_LINE + "Downloading approved difference list in CSV format...");
            final byte[] csvData = client.getApprovedDifferenceListAsCsv(approvedDifferenceListId);
            try {
                final String csvString = new String(csvData, "UTF-8");
                System.out.println(csvString);
            } catch (UnsupportedEncodingException e) {
                System.out.println("Sorry, encoding not supported");
            }

            // Export approved difference list
            System.out.println(NEW_LINE + "Exporting approved difference list...");
            final ApprovedDifferenceListExportResponse approvedDifferenceListExportResponse = client
                    .approvedDifferenceListExport(approvedDifferenceListId);
            System.out.println(printApprovedDifferenceListExportResponse(approvedDifferenceListExportResponse));

            // Mark exported approved difference list as exported
            System.out.println(NEW_LINE + "Setting export status of approved difference list...");
            client.updateApprovedDifferenceListExportStatus(approvedDifferenceListId, ExportStatus.EXPORTED);

            // List of approved difference list summaries
            System.out.println(NEW_LINE + "Getting list of approved difference list summaries...");
            final List<ApprovedDifferenceListSummary> approvedDifferenceListSummaries = client
                    .getApprovedDifferenceListSummaries(locationId, null, null);
            System.out.println(printListOfApprovedDifferenceListSummaries(approvedDifferenceListSummaries, locationId));

            // Status of approved difference list
            System.out.println(NEW_LINE + "Retrieving status of approved difference list...");
            final ApprovedDifferenceListSummary approvedDifferenceListStatus = client
                    .getApprovedDifferenceListStatus(locationId, EVENT_TIME.toString());
            System.out.println(printApprovedDifferenceListStatus(approvedDifferenceListStatus));

            // Delete approved difference list
            System.out.println(NEW_LINE + "Deleting approved difference list...");
            client.deleteApprovedDifferenceList(UUID.fromString(approvedDifferenceListId));
            System.out.println("Approved difference list with id: " + approvedDifferenceListId + " has been deleted");

            // Check if we have expected stock
            System.out.println(NEW_LINE + "Checking for expected stock...");
            final ExpectedStock expectedStock = client.getExpectedStock(locationId);
            System.out.println("Found expected stock with " + expectedStock.gtinQuantity + " GTINs");

            System.out.println(NEW_LINE + "--- EPC API example finished ---");

        } catch (final ClientException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
        }
    }

    private static Article createArticle1() {
        final Article article = new Article();

        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode(EAN13, "2011200000019"));
        article.barcodes = barcodes;

        article.gtin = GTIN_1;
        article.name = "T-shirt with V-neck and short sleeves";
        article.color = BLACK;
        article.sizes = setArticleSizes1();

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
        barcodes.add(new Barcode(EAN13, "2011200000064"));
        article.barcodes = barcodes;

        article.gtin = GTIN_2;
        article.name = "Straight regular jeans";
        article.color = BLACK;
        article.sizes = setArticleSizes2();

        return article;
    }

    private static List<Size> setArticleSizes2() {
        final List<Size> sizes = new ArrayList<>();
        sizes.add(new Size("31/36", "NL"));
        sizes.add(new Size("31/33", "DE"));
        sizes.add(new Size("31/35", "GB"));
        return sizes;
    }

    private static Article createArticle3() {
        final Article article = new Article();

        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode(EAN13, "2011200000163"));
        article.barcodes = barcodes;

        article.gtin = GTIN_3;
        article.name = "Washed leather in a classic maritime style shoes";
        article.color = BLACK;
        article.sizes = setArticleSizes3();

        return article;
    }

    private static List<Size> setArticleSizes3() {
        final List<Size> sizes = new ArrayList<>();
        sizes.add(new Size("41-46", "NL"));
        sizes.add(new Size("40-45", "DE"));
        sizes.add(new Size("40-45", "GB"));
        return sizes;
    }

    private static String printCreatedArticlesGtins() {
        final StringBuilder sb = new StringBuilder("Created test articles with gtins: ");
        sb.append(GTIN_1).append(COMMA);
        sb.append(GTIN_2).append(COMMA);
        sb.append(GTIN_3);
        return sb.toString();
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
        final Stock rfidStock = new Stock();
        rfidStock.location = location;
        rfidStock.eventTime = EVENT_TIME;
        rfidStock.externRef = EXTERNAL_REFERENCE;
        rfidStock.quantityList = Arrays.asList(new GtinQuantity(GTIN_1, 7), new GtinQuantity(GTIN_2, 15),
                new GtinQuantity(GTIN_3, 17));
        return rfidStock;
    }

    private static List<String> getSublocationIds(final String locationId, final Client client) {
        final List<String> sublocationIds = new ArrayList<>();
        final Location location = client.getLocation(locationId);
        if (!CollectionUtils.isEmpty(location.children)) {
            sublocationIds.add(getSalesFloorSublocationId(location.children));
            sublocationIds.add(getStockRoomSublocationId(location.children));
        }
        return sublocationIds;
    }

    private static String getSalesFloorSublocationId(final List<Location> sublocations) {
        for (final Location sublocation : sublocations) {
            if (LocationSubType.SALES_FLOOR.equals(sublocation.subtype)) {
                return sublocation.id;
            }
        }
        return null;
    }

    private static String getStockRoomSublocationId(final List<Location> sublocations) {
        for (final Location sublocation : sublocations) {
            if (LocationSubType.STOCK_ROOM.equals(sublocation.subtype)) {
                return sublocation.id;
            }
        }
        return null;
    }

    private static Stock createSalesFloorRfidStockHistory(final String locationId) {
        final Stock rfidStock = new Stock();
        rfidStock.location = locationId;
        rfidStock.eventTime = EVENT_TIME.minusDays(3);
        rfidStock.externRef = EXTERNAL_REFERENCE;
        rfidStock.quantityList = Arrays.asList(new GtinQuantity(GTIN_1, 10), new GtinQuantity(GTIN_2, 10),
                new GtinQuantity(GTIN_3, 10));
        return rfidStock;
    }

    private static Stock createStockRoomRfidStock(final String locationId) {
        final Stock rfidStock = new Stock();
        rfidStock.location = locationId;
        rfidStock.eventTime = EVENT_TIME.minusDays(3);
        rfidStock.externRef = EXTERNAL_REFERENCE;
        rfidStock.quantityList = Arrays.asList(new GtinQuantity(GTIN_1, 20), new GtinQuantity(GTIN_2, 15),
                new GtinQuantity(GTIN_3, 10));
        return rfidStock;
    }

    private static Stock createSalesFloorRfidStockPresent(final String locationId) {
        final Stock rfidStock = new Stock();
        rfidStock.location = locationId;
        rfidStock.eventTime = EVENT_TIME;
        rfidStock.externRef = EXTERNAL_REFERENCE;
        rfidStock.quantityList = Arrays.asList(new GtinQuantity(GTIN_1, 1), new GtinQuantity(GTIN_2, 0),
                new GtinQuantity(GTIN_3, 0));
        return rfidStock;
    }

    private static String printSublocationStocks(final List<String> sublocationIds,
            final List<String> generatedStocks) {
        final StringBuilder sb = new StringBuilder();
        sb.append("For location: ").append(sublocationIds.get(0)).append(" created stocks with ids:");
        sb.append(NEW_LINE).append(TAB).append(generatedStocks.get(0));
        sb.append(NEW_LINE).append(TAB).append(generatedStocks.get(2));
        sb.append(NEW_LINE).append("And for location: ").append(sublocationIds.get(1)).append(COLON);
        sb.append(NEW_LINE).append(TAB).append(generatedStocks.get(1));
        return sb.toString();
    }

    private static String printStockResponse(final StockResponse stockResponse) {
        final StringBuilder sb = new StringBuilder("Quantities for a given gtins:");
        for (int i = 0; i < stockResponse.gtins.size(); i++) {
            sb.append(NEW_LINE).append(TAB);
            sb.append(stockResponse.gtins.get(i)).append(WHITESPACE).append(stockResponse.quantities.get(i));
        }
        return sb.toString();
    }

    private static String printNotOnShelfResponse(final NotOnShelfResponse notOnShelfResponse) {
        final StringBuilder sb = new StringBuilder("Not on shelf response:").append(NEW_LINE);
        for (int i = 0; i < notOnShelfResponse.gtins.size(); i++) {
            sb.append(TAB).append("For gtin ").append(notOnShelfResponse.gtins.get(i)).append(COLON).append(NEW_LINE);
            sb.append(DOUBLE_TAB).append("Stock room quantuty: ").append(notOnShelfResponse.stockRoomStock.get(i))
                    .append(NEW_LINE);
            sb.append(DOUBLE_TAB).append("Sales floor quantity: ").append(notOnShelfResponse.salesFloorStock.get(i))
                    .append(NEW_LINE);
        }
        sb.append(NEW_LINE).append(TAB).append("Total difference: ").append(notOnShelfResponse.notOnShelfPercentage);
        return sb.toString();
    }

    private static String printDifferenceList(final DifferenceListResponse differenceList) {
        final StringBuilder sb = new StringBuilder("Differences for a given gtins are:");
        for (int i = 0; i < differenceList.gtins.size(); i++) {
            sb.append(NEW_LINE).append(TAB);
            sb.append("For gtin: ").append(differenceList.gtins.get(i)).append(WHITESPACE);
            sb.append("ERP stock quantity is: ").append(differenceList.erpStock.get(i)).append(COMMA);
            sb.append("RFID stock quantity is: ").append(differenceList.rfidStock.get(i));
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
        final StringBuilder sb = new StringBuilder("Retrieved approved difference list with:");
        sb.append(NEW_LINE).append(TAB).append("Absolute difference: ")
                .append(approvedDifferenceList.absoluteDifference);
        sb.append(NEW_LINE).append(TAB).append("Plus difference: ").append(approvedDifferenceList.plusDifference);
        sb.append(NEW_LINE).append(TAB).append("Minus difference: ").append(approvedDifferenceList.minusDifference);
        return sb.toString();
    }

    private static String printApprovedDifferenceListExportResponse(
            final ApprovedDifferenceListExportResponse response) {
        final StringBuilder sb = new StringBuilder("Approved difference list export status:");
        for (int i = 0; i < response.gtins.size(); i++) {
            sb.append(NEW_LINE).append(TAB);
            sb.append("for gtin: ").append(response.gtins.get(i));
            sb.append(" approved quantity is: ").append(response.approvedQuantity.get(i));
        }
        return sb.toString();
    }

    private static String printListOfApprovedDifferenceListSummaries(final List<ApprovedDifferenceListSummary> response,
            final String location) {

        final StringBuilder sb = new StringBuilder("Approved difference lists ids for location: ").append(location)
                .append(" are:");
        for (final ApprovedDifferenceListSummary list : response) {
            sb.append(NEW_LINE);
            sb.append(TAB).append(list.id);
        }
        return sb.toString();
    }

    private static String printApprovedDifferenceListStatus(
            final ApprovedDifferenceListSummary approvedDifferenceListStatus) {

        final StringBuilder sb = new StringBuilder("Approved difference list status:");
        sb.append(NEW_LINE).append(TAB).append("Id: ").append(approvedDifferenceListStatus.id);
        sb.append(NEW_LINE).append(TAB).append("Approved on: ").append(approvedDifferenceListStatus.approvedOn);
        sb.append(NEW_LINE).append(TAB).append("Location: ").append(approvedDifferenceListStatus.location);
        sb.append(NEW_LINE).append(TAB).append("Export status: ").append(approvedDifferenceListStatus.exportStatus);
        return sb.toString();
    }
}
