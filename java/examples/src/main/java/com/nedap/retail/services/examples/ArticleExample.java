package com.nedap.retail.services.examples;

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
        System.out.println(PrintHelper.NEW_LINE + "*** Article API example ***");

        try {
            // Create or replace articles
            System.out.println(PrintHelper.NEW_LINE + "Uploading articles...");
            final List<Article> exampleArticles = Arrays.asList(createExampleArticle());
            client.captureArticles(exampleArticles);
            System.out.println(printCapturedArticles(exampleArticles));

            // Article quantity
            System.out.println(PrintHelper.NEW_LINE + "Retrieving article quantity...");
            final Long quantity = client.articleQuantity();
            System.out.println("Article quantity = " + quantity);

            // Article details by gtins
            System.out.println(PrintHelper.NEW_LINE + "Retrieving article details by gtins...");
            final List<String> gtins = Arrays.asList(PrintHelper.GTIN_1, PrintHelper.GTIN_2);
            final List<Article> articlesWithGtins = client.articleDetailsByGtins(gtins, null);
            System.out.println(printRetrievedArticlesByGtins(articlesWithGtins, gtins));

            // Article datails by barcodes with name field
            System.out.println(PrintHelper.NEW_LINE + "Retrieving article names by barcodes...");
            final List<String> barcodes = Arrays.asList("2011200000019", "2011200000057");
            final List<String> fields = new ArrayList<>();
            fields.add("name");
            final List<Article> articlesWithBarcodes = client.articleDetailsByBarcodes(barcodes, fields);
            System.out.println(printRetrievedArticlesByBarcodes(articlesWithBarcodes, barcodes));

            // Retrieve articles with gtin and name fields
            System.out.println(PrintHelper.NEW_LINE + "Retrieving up to 50 articles with gtins and names...");
            fields.add("gtin");
            final List<Article> articles = client.retrieveArticles(null, 0, 50, fields);
            System.out.println(printRetrievedArticles(articles));

            System.out.println(PrintHelper.NEW_LINE + "--- Article API example finished ---");

        } catch (final ClientException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
        }
    }

    private static Article createExampleArticle() {
        final Article article = new Article();

        // required fields
        article.setGtin(PrintHelper.GTIN_1);
        article.setName("T-shirt with V-neck and short sleeves");
        article.setColor("Black");
        article.setSizes(setArticleSizes());

        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode("EAN13", "2011200000019"));
        article.setBarcodes(barcodes);

        // optional fields
        article.setCode(PrintHelper.GTIN_1);
        article.setBrand("River Island");
        article.setSeason("Summer 2015");
        article.setOption("T-shirt with V-neck and short sleeves, Black");
        article.setStyle("T-shirt with V-neck and short sleeves");
        article.setSupplier("River Island");
        article.setCategory("T-shirt");
        article.setMarkdown(false);
        article.setPrices(setArticlePrices());

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
            sb.append(gtin + PrintHelper.COMMA);
        }
        sb.append("founded articles: ");
        sb.append(printArticles(articles));
        return sb.toString();
    }

    private static String printArticles(final List<Article> articles) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < articles.size(); i++) {
            sb.append(PrintHelper.NEW_LINE + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Article " + (i + 1) + PrintHelper.COLON + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Gtin: " + articles.get(i).getGtin() + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Barcodes: " + printBarcodes(articles.get(i).getBarcodes())
                    + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Code: " + articles.get(i).getCode() + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Brand: " + articles.get(i).getBrand() + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Season: " + articles.get(i).getSeason() + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Name: " + articles.get(i).getName() + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Option: " + articles.get(i).getOption() + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Style: " + articles.get(i).getStyle() + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Color: " + articles.get(i).getColor() + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Sizes: " + printSizes(articles.get(i).getSizes()) + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Supplier: " + articles.get(i).getSupplier() + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Category: " + articles.get(i).getCategory() + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Prices: " + printPrices(articles.get(i).getPrices()) + PrintHelper.NEW_LINE);
            sb.append(PrintHelper.TAB + "Markdown: " + articles.get(i).getMarkdown());
        }
        return sb.toString();
    }

    private static String printBarcodes(final List<Barcode> barcodes) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < barcodes.size(); i++) {
            sb.append(PrintHelper.NEW_LINE);
            sb.append(PrintHelper.DOUBLE_TAB + "Barcode " + i + PrintHelper.COLON);
            sb.append(barcodes.get(i).getType() + PrintHelper.WHITESPACE);
            sb.append(barcodes.get(i).getValue());
        }
        return sb.toString();
    }

    private static String printSizes(final List<Size> sizes) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sizes.size(); i++) {
            sb.append(PrintHelper.NEW_LINE);
            sb.append(PrintHelper.DOUBLE_TAB + "Size " + i + PrintHelper.COLON);
            sb.append(sizes.get(i).getDescription() + PrintHelper.WHITESPACE);
            sb.append(sizes.get(i).getRegion());
        }
        return sb.toString();
    }

    private static String printPrices(final List<Price> prices) {
        if (CollectionUtils.isEmpty(prices)) {
            return null;
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prices.size(); i++) {
            sb.append(PrintHelper.NEW_LINE);
            sb.append(PrintHelper.DOUBLE_TAB + "Price " + i + PrintHelper.COLON);
            sb.append(prices.get(i).getCurrency() + PrintHelper.WHITESPACE);
            sb.append(prices.get(i).getRegion() + PrintHelper.WHITESPACE);
            sb.append(prices.get(i).getAmount());
        }
        return sb.toString();
    }

    private static String printRetrievedArticlesByBarcodes(final List<Article> articles, final List<String> barcodes) {
        final StringBuilder sb = new StringBuilder("For barcodes: ");
        for (final String barcode : barcodes) {
            sb.append(barcode + PrintHelper.COMMA);
        }
        sb.append(" founded articles with names: ");
        for (int i = 0; i < articles.size(); i++) {
            sb.append(PrintHelper.NEW_LINE + PrintHelper.TAB + (i + 1) + PrintHelper.DOT + articles.get(i).getName());
        }
        return sb.toString();
    }

    private static String printRetrievedArticles(final List<Article> articles) {
        final StringBuilder sb = new StringBuilder("Retrieved articles with gtins and names: ");
        for (int i = 0; i < articles.size(); i++) {
            sb.append(PrintHelper.NEW_LINE + PrintHelper.TAB + (i + 1) + PrintHelper.DOT);
            sb.append(articles.get(i).getGtin() + PrintHelper.WHITESPACE);
            sb.append(articles.get(i).getName());
        }
        return sb.toString();
    }
}
