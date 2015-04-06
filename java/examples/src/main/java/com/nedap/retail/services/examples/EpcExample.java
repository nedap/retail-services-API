package com.nedap.retail.services.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.Barcode;
import com.nedap.retail.messages.article.Size;
import com.nedap.retail.messages.epc.v2.approved_difference_list.request.ApprovedDifferenceListCaptureRequest;
import com.nedap.retail.messages.epc.v2.difference_list.DifferenceListResponse;
import com.nedap.retail.messages.epc.v2.stock.NotOnShelfRequest;
import com.nedap.retail.messages.epc.v2.stock.NotOnShelfResponse;
import com.nedap.retail.messages.epc.v2.stock.StockResponse;
import com.nedap.retail.messages.stock.GtinQuantity;
import com.nedap.retail.messages.stock.Stock;
import com.sun.jersey.api.client.UniformInterfaceException;

public class EpcExample {

    private static final String GTIN_1 = "02011200000019";
    private static final String GTIN_2 = "02011200000064";
    private static final String GTIN_3 = "02011200000163";
    private static final String LOCATION = "http://retailer.com/loc/123";
    private static final DateTime EVENT_TIME = DateTime.now();
    private static final String EXTERNAL_REFERENCE = "generated_import";
    private static final String ERP_STOCK_ID = "72c49313dc6c0f55500974fbe6968606cd0076bdf004884dbb4b6a85291712f5";

    public static void runExample(final Client client) {
        System.out.println("*** EPC API example ***");

        try {
            // create some articles
            System.out.println("Creating some articles...");
            client.captureArticles(Arrays.asList(createArticle1(), createArticle2(), createArticle3()));
            System.out.println("Captured some test articles");

            // create some erp stock
            System.out.println("Creating some ERP stock...");
            final String erpStockId = client.captureErpStock(createErpStock());
            System.out.println("ERP stock created");

            // create some rfid stock
            System.out.println("Creating some RFID stock...");
            final String RfidStockId = client.captureRfidStock(createRfidStock());
            System.out.println("RFID stock created");

            // capture approved difference list
            System.out.println("Capturing difference list...");
            final String approvedDifferenceListId = client
                    .captureApprovedDifferenceList(makeApprovedDifferenceListCaptureRequest(erpStockId));
            System.out.println("Approved difference list captured");

            // not on shelf
            System.out.println("Retrieving not on shelf items list...");
            final NotOnShelfResponse notOnShelfResponse = client.notOnShelf(new NotOnShelfRequest(LOCATION, true));
            System.out.println(printNotOnShelfResponse(notOnShelfResponse));

            // retrieve approved difference list
            System.out.println("Retrieving difference list...");
            final DifferenceListResponse dl = client.differenceList(ERP_STOCK_ID, null, null, null);
            System.out.println(dl.toString());

            // get stock gtin
            System.out.println("Retrieving stock gtin...");
            final StockResponse sg = client.stockGtin(LOCATION, null, null, null, null);
            System.out.println(sg.toString());

            System.out.println("--- Done ---");

        } catch (final UniformInterfaceException e) {
            System.err.println("Server responded with an error: " + e.getResponse().getEntity(String.class));
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

    private static Stock createErpStock() {
        final Stock erpStock = new Stock();
        erpStock.location = LOCATION;
        erpStock.eventTime = EVENT_TIME;
        erpStock.externRef = EXTERNAL_REFERENCE;
        erpStock.quantityList = Arrays.asList(new GtinQuantity(GTIN_1, 5), new GtinQuantity(GTIN_2, 10),
                new GtinQuantity(GTIN_3, 20));
        return erpStock;
    }

    private static Stock createRfidStock() {
        final Stock erpStock = new Stock();
        erpStock.location = LOCATION;
        erpStock.eventTime = EVENT_TIME;
        erpStock.externRef = EXTERNAL_REFERENCE;
        erpStock.quantityList = Arrays.asList(new GtinQuantity(GTIN_1, 7), new GtinQuantity(GTIN_2, 15),
                new GtinQuantity(GTIN_3, 17));
        return erpStock;
    }

    private static ApprovedDifferenceListCaptureRequest makeApprovedDifferenceListCaptureRequest(final String erpStockId) {
        final ApprovedDifferenceListCaptureRequest approvedDifferenceListCaptureRequest = new ApprovedDifferenceListCaptureRequest();
        approvedDifferenceListCaptureRequest.rfidTime = EVENT_TIME;
        approvedDifferenceListCaptureRequest.erpStockId = erpStockId;
        approvedDifferenceListCaptureRequest.location = LOCATION;
        approvedDifferenceListCaptureRequest.approvedGtins = Arrays.asList(GTIN_1, GTIN_2, GTIN_3);
        return approvedDifferenceListCaptureRequest;
    }

    private static String printNotOnShelfResponse(final NotOnShelfResponse notOnShelfResponse) {
        final StringBuilder sb = new StringBuilder();
        sb.append(notOnShelfResponse.gtins);
        return sb.toString();
    }
}
