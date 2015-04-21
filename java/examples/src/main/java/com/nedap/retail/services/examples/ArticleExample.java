package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.PrintHelper.COLON;
import static com.nedap.retail.services.examples.PrintHelper.COMMA;
import static com.nedap.retail.services.examples.PrintHelper.DOT;
import static com.nedap.retail.services.examples.PrintHelper.DOUBLE_TAB;
import static com.nedap.retail.services.examples.PrintHelper.GTIN_1;
import static com.nedap.retail.services.examples.PrintHelper.GTIN_2;
import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;
import static com.nedap.retail.services.examples.PrintHelper.TAB;
import static com.nedap.retail.services.examples.PrintHelper.WHITESPACE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.Barcode;
import com.nedap.retail.messages.article.Price;
import com.nedap.retail.messages.article.Size;

public class ArticleExample {

    public static void runExample(final Client client) {
        System.out.println(NEW_LINE + "*** Article API example ***");

        try {
            // Create or replace articles
            System.out.println(NEW_LINE + "Uploading articles...");
            final List<Article> exampleArticles = Arrays.asList(createExampleArticle());
            client.captureArticles(exampleArticles);
            System.out.println(printCapturedArticles(exampleArticles));

            // Article quantity
            System.out.println(NEW_LINE + "Retrieving article quantity...");
            final Long quantity = client.articleQuantity();
            System.out.println("Article quantity = " + quantity);

            // Article details by gtins
            System.out.println(NEW_LINE + "Retrieving article details by gtins...");
            final List<String> gtins = Arrays.asList(GTIN_1, GTIN_2);
            final List<Article> articlesWithGtins = client.articleDetailsByGtins(gtins, null);
            System.out.println(printRetrievedArticlesByGtins(articlesWithGtins, gtins));

            // Article datails by barcodes with name field
            System.out.println(NEW_LINE + "Retrieving article names by barcodes...");
            final List<String> barcodes = Arrays.asList("2011200000019", "2011200000057");
            final List<String> fields = new ArrayList<>();
            fields.add("name");
            final List<Article> articlesWithBarcodes = client.articleDetailsByBarcodes(barcodes, fields);
            System.out.println(printRetrievedArticlesByBarcodes(articlesWithBarcodes, barcodes));

            // Retrieve articles with gtin and name fields
            System.out.println(NEW_LINE + "Retrieving up to 50 articles with gtins and names...");
            fields.add("gtin");
            final List<Article> articles = client.retrieveArticles(null, 0, 50, fields);
            System.out.println(printRetrievedArticles(articles));

            System.out.println(NEW_LINE + "--- Article API example finished ---");

        } catch (final ClientException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
        }
    }

    private static Article createExampleArticle() {
        final Article article = new Article();

        // required fields
        article.gtin = GTIN_1;
        article.name = "T-shirt with V-neck and short sleeves";
        article.color = "Black";
        article.sizes = setArticleSizes();

        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode("EAN13", "2011200000019"));
        article.barcodes = barcodes;

        // optional fields
        article.code = GTIN_1;
        article.brand = "River Island";
        article.season = "Summer 2015";
        article.option = "T-shirt with V-neck and short sleeves, Black";
        article.style = "T-shirt with V-neck and short sleeves";
        article.supplier = "River Island";
        article.category = "T-shirt";
        article.markdown = false;
        article.prices = setArticlePrices();

        return article;
    }

    private static List<Size> setArticleSizes() {
        final List<Size> sizes = new ArrayList<>();
        sizes.add(new Size("32/35", "NL"));
        sizes.add(new Size("32/33", "DE"));
        sizes.add(new Size("31/35", "GB"));
        return sizes;
    }

    private static List<Price> setArticlePrices() {
        final List<Price> prices = new ArrayList<>();
        prices.add(new Price("EUR", "NL", 29.99));
        prices.add(new Price("EUR", "DE", 33.99));
        prices.add(new Price("GBP", "GB", 24.99));
        return prices;
    }

    private static String printCapturedArticles(final List<Article> articles) {
        final StringBuilder sb = new StringBuilder("Uploaded articles: ");
        sb.append(printArticles(articles));
        return sb.toString();
    }

    private static String printRetrievedArticlesByGtins(final List<Article> articles, final List<String> gtins) {
        final StringBuilder sb = new StringBuilder("For gtins: ");
        for (final String gtin : gtins) {
            sb.append(gtin + COMMA);
        }
        sb.append("founded articles: ");
        sb.append(printArticles(articles));
        return sb.toString();
    }

    private static String printArticles(final List<Article> articles) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < articles.size(); i++) {
            sb.append(NEW_LINE + NEW_LINE);
            sb.append(TAB + "Article " + (i + 1) + COLON + NEW_LINE);
            sb.append(TAB + "Gtin: " + articles.get(i).gtin + NEW_LINE);
            sb.append(TAB + "Barcodes: " + printBarcodes(articles.get(i).barcodes) + NEW_LINE);
            sb.append(TAB + "Code: " + articles.get(i).code + NEW_LINE);
            sb.append(TAB + "Brand: " + articles.get(i).brand + NEW_LINE);
            sb.append(TAB + "Season: " + articles.get(i).season + NEW_LINE);
            sb.append(TAB + "Name: " + articles.get(i).name + NEW_LINE);
            sb.append(TAB + "Option: " + articles.get(i).option + NEW_LINE);
            sb.append(TAB + "Style: " + articles.get(i).style + NEW_LINE);
            sb.append(TAB + "Color: " + articles.get(i).color + NEW_LINE);
            sb.append(TAB + "Sizes: " + printSizes(articles.get(i).sizes) + NEW_LINE);
            sb.append(TAB + "Supplier: " + articles.get(i).supplier + NEW_LINE);
            sb.append(TAB + "Category: " + articles.get(i).category + NEW_LINE);
            sb.append(TAB + "Prices: " + printPrices(articles.get(i).prices) + NEW_LINE);
            sb.append(TAB + "Markdown: " + articles.get(i).markdown);
        }
        return sb.toString();
    }

    private static String printBarcodes(final List<Barcode> barcodes) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < barcodes.size(); i++) {
            sb.append(NEW_LINE);
            sb.append(DOUBLE_TAB + "Barcode " + i + COLON);
            sb.append(barcodes.get(i).type + WHITESPACE);
            sb.append(barcodes.get(i).value);
        }
        return sb.toString();
    }

    private static String printSizes(final List<Size> sizes) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sizes.size(); i++) {
            sb.append(NEW_LINE);
            sb.append(DOUBLE_TAB + "Size " + i + COLON);
            sb.append(sizes.get(i).description + WHITESPACE);
            sb.append(sizes.get(i).region);
        }
        return sb.toString();
    }

    private static String printPrices(final List<Price> prices) {
        if (CollectionUtils.isEmpty(prices)) {
            return null;
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prices.size(); i++) {
            sb.append(NEW_LINE);
            sb.append(DOUBLE_TAB + "Price " + i + COLON);
            sb.append(prices.get(i).currency + WHITESPACE);
            sb.append(prices.get(i).region + WHITESPACE);
            sb.append(prices.get(i).amount);
        }
        return sb.toString();
    }

    private static String printRetrievedArticlesByBarcodes(final List<Article> articles, final List<String> barcodes) {
        final StringBuilder sb = new StringBuilder("For barcodes: ");
        for (final String barcode : barcodes) {
            sb.append(barcode + COMMA);
        }
        sb.append(" founded articles with names: ");
        for (int i = 0; i < articles.size(); i++) {
            sb.append(NEW_LINE + TAB + (i + 1) + DOT + articles.get(i).name);
        }
        return sb.toString();
    }

    private static String printRetrievedArticles(final List<Article> articles) {
        final StringBuilder sb = new StringBuilder("Retrieved articles with gtins and names: ");
        for (int i = 0; i < articles.size(); i++) {
            sb.append(NEW_LINE + TAB + (i + 1) + DOT);
            sb.append(articles.get(i).gtin + WHITESPACE);
            sb.append(articles.get(i).name);
        }
        return sb.toString();
    }
}
