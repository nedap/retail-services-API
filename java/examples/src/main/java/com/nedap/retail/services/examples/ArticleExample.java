package com.nedap.retail.services.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.Barcode;
import com.nedap.retail.messages.article.Price;
import com.nedap.retail.messages.article.Size;
import com.sun.jersey.api.client.UniformInterfaceException;

public class ArticleExample {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String TAB = "\t";
    private static final String DOUBLE_TAB = "\t\t";
    private static final String COLON = ": ";
    private static final String WHITESPACE = " ";
    private static final String DOT = ". ";
    private static final String COMMA = ", ";

    public static void runExample(final Client client) {
        System.out.println(NEW_LINE + "*** Article API example ***");

        // this is our example data
        final List<Article> exampleArticles = new ArrayList<>();
        exampleArticles.add(createExampleArticle());

        try {
            // send articles
            System.out.println(NEW_LINE + "Uploading articles...");
            client.captureArticles(exampleArticles);
            System.out.println(printCapturedArticles(exampleArticles));

            // get quantity
            System.out.println(NEW_LINE + "Retrieving article quantity...");
            final Long quantity = client.articleQuantity();
            System.out.println("Article quantity = " + quantity);

            // article details by gtins
            System.out.println(NEW_LINE + "Retrieving article details by gtins...");
            final List<String> gtins = Arrays.asList("02011200000019", "02011200000057");
            final List<Article> articlesWithGtins = client.articleDetailsByGtins(gtins, null);
            System.out.println(printRetrievedArticlesByGtins(articlesWithGtins, gtins));

            // article datails by barcodes with name field
            System.out.println(NEW_LINE + "Retrieving article names by barcodes...");
            final List<String> barcodes = Arrays.asList("2011200000019", "2011200000057");
            final List<String> fields = new ArrayList<>();
            fields.add("name");
            final List<Article> articlesWithBarcodes = client.articleDetailsByBarcodes(barcodes, fields);
            System.out.println(printRetrievedArticlesByBarcodes(articlesWithBarcodes, barcodes));

            // retrieve articles with name and prices fields
            System.out.println(NEW_LINE + "Retrieving up to 50 articles with gtins and names...");
            fields.add("gtin");
            final List<Article> articles = client.retrieveArticles(null, 0, 50, fields);
            System.out.println(printRetrievedArticles(articles));

            System.out.println(NEW_LINE + "--- Article API example finished ---");

        } catch (final UniformInterfaceException e) {
            System.err.println("Server responded with an error: " + e.getResponse().getEntity(String.class));
        }
    }

    private static Article createExampleArticle() {
        final Article article = new Article();

        // required fields
        article.setGtin("02011200000019");

        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode("EAN13", "2011200000019"));
        article.setBarcodes(barcodes);

        article.setName("T-shirt with V-neck and short sleeves");
        article.setColor("Black");
        article.setSizes(setArticleSizes());

        // optional fields
        article.setCode("02011200000019");
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
            sb.append(gtin + COMMA);
        }
        sb.append("founded articles: ");
        sb.append(printArticles(articles));
        return sb.toString();
    }

    private static String printArticles(final List<Article> articles) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < articles.size(); i++) {
            sb.append(NEW_LINE);
            sb.append(TAB + "Article " + i + COLON + NEW_LINE);
            sb.append(TAB + "gtin: " + articles.get(i).getGtin() + NEW_LINE);
            sb.append(TAB + "barcodes: " + printBarcodes(articles.get(i).getBarcodes()) + NEW_LINE);
            sb.append(TAB + "code: " + articles.get(i).getCode() + NEW_LINE);
            sb.append(TAB + "brand: " + articles.get(i).getBrand() + NEW_LINE);
            sb.append(TAB + "season: " + articles.get(i).getSeason() + NEW_LINE);
            sb.append(TAB + "name: " + articles.get(i).getName() + NEW_LINE);
            sb.append(TAB + "option: " + articles.get(i).getOption() + NEW_LINE);
            sb.append(TAB + "style: " + articles.get(i).getStyle() + NEW_LINE);
            sb.append(TAB + "color: " + articles.get(i).getColor() + NEW_LINE);
            sb.append(TAB + "sizes: " + printSizes(articles.get(i).getSizes()) + NEW_LINE);
            sb.append(TAB + "supplier: " + articles.get(i).getSupplier() + NEW_LINE);
            sb.append(TAB + "category: " + articles.get(i).getCategory() + NEW_LINE);
            sb.append(TAB + "prices: " + printPrices(articles.get(i).getPrices()) + NEW_LINE);
            sb.append(TAB + "markdown: " + articles.get(i).getMarkdown());
        }
        return sb.toString();
    }

    private static String printBarcodes(final List<Barcode> barcodes) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < barcodes.size(); i++) {
            sb.append(NEW_LINE);
            sb.append(DOUBLE_TAB + "Barcode " + i + COLON);
            sb.append(barcodes.get(i).getType() + WHITESPACE);
            sb.append(barcodes.get(i).getValue());
        }
        return sb.toString();
    }

    private static String printSizes(final List<Size> sizes) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sizes.size(); i++) {
            sb.append(NEW_LINE);
            sb.append(DOUBLE_TAB + "Size " + i + COLON);
            sb.append(sizes.get(i).getDescription() + WHITESPACE);
            sb.append(sizes.get(i).getRegion());
        }
        return sb.toString();
    }

    private static String printPrices(final List<Price> prices) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < prices.size(); i++) {
            sb.append(NEW_LINE);
            sb.append(DOUBLE_TAB + "Price " + i + COLON);
            sb.append(prices.get(i).getCurrency() + WHITESPACE);
            sb.append(prices.get(i).getRegion() + WHITESPACE);
            sb.append(prices.get(i).getAmount());
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
            sb.append(NEW_LINE + TAB + (i + 1) + DOT + articles.get(i).getName());
        }
        return sb.toString();
    }

    private static String printRetrievedArticles(final List<Article> articles) {
        final StringBuilder sb = new StringBuilder("Retrieved articles with gtins and names: ");
        for (int i = 0; i < articles.size(); i++) {
            sb.append(NEW_LINE + TAB + (i + 1) + DOT);
            sb.append(articles.get(i).getGtin() + WHITESPACE);
            sb.append(articles.get(i).getName());
        }
        return sb.toString();
    }
}
