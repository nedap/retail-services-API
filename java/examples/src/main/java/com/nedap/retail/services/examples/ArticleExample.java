package com.nedap.retail.services.examples;

import static com.nedap.retail.services.examples.PrintHelper.BLACK;
import static com.nedap.retail.services.examples.PrintHelper.COLON;
import static com.nedap.retail.services.examples.PrintHelper.COMMA;
import static com.nedap.retail.services.examples.PrintHelper.DOT;
import static com.nedap.retail.services.examples.PrintHelper.DOUBLE_TAB;
import static com.nedap.retail.services.examples.PrintHelper.EAN13;
import static com.nedap.retail.services.examples.PrintHelper.GTIN_1;
import static com.nedap.retail.services.examples.PrintHelper.GTIN_2;
import static com.nedap.retail.services.examples.PrintHelper.NEW_LINE;
import static com.nedap.retail.services.examples.PrintHelper.TAB;
import static com.nedap.retail.services.examples.PrintHelper.WHITESPACE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.ClientException;
import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.ArticleFindResponse;
import com.nedap.retail.messages.article.Barcode;
import com.nedap.retail.messages.article.Price;
import com.nedap.retail.messages.article.Size;

public class ArticleExample {

    private ArticleExample() {
    }

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

            // Find articles with search query
            System.out.println(NEW_LINE + "Finding article with search query: 'summer'...");
            final ArticleFindResponse articleFindResponse = client.findArticles("summer", 0, 100, null);
            System.out.println(printArticleFindResponse(articleFindResponse));

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
        article.color = BLACK;
        article.sizes = setArticleSizes();

        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode(EAN13, "2011200000019"));
        article.barcodes = barcodes;

        // optional fields
        article.code = GTIN_1;
        article.brand = "Nedap Retail";
        article.season = "Summer 2015";
        article.option = "T-shirt with V-neck and short sleeves, Black";
        article.style = "T-shirt with V-neck and short sleeves";
        article.supplier = "Nedap Retail";
        article.category = "T-shirt";
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
            sb.append(gtin).append(COMMA);
        }
        sb.append("Found articles: ");
        sb.append(printArticles(articles));
        return sb.toString();
    }

    private static String printArticleFindResponse(final ArticleFindResponse articleFindResponse) {
        final StringBuilder sb = new StringBuilder();
        sb.append("Total records before filtering: ").append(articleFindResponse.recordsTotal).append(NEW_LINE);
        sb.append("Total records after filtering: ").append(articleFindResponse.recordsFiltered).append(NEW_LINE);
        sb.append("Found articles: ");
        sb.append(printArticles(articleFindResponse.articles));
        return sb.toString();
    }

    private static String printArticles(final List<Article> articles) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < articles.size(); i++) {
            sb.append(NEW_LINE).append(NEW_LINE);
            sb.append(TAB).append("Article ").append(i + 1).append(COLON).append(NEW_LINE);
            sb.append(TAB).append("Gtin: ").append(articles.get(i).gtin).append(NEW_LINE);
            sb.append(TAB).append("Barcodes: ").append(printBarcodes(articles.get(i).barcodes)).append(NEW_LINE);
            sb.append(TAB).append("Code: ").append(articles.get(i).code).append(NEW_LINE);
            sb.append(TAB).append("Brand: ").append(articles.get(i).brand).append(NEW_LINE);
            sb.append(TAB).append("Season: ").append(articles.get(i).season).append(NEW_LINE);
            sb.append(TAB).append("Name: ").append(articles.get(i).name).append(NEW_LINE);
            sb.append(TAB).append("Option: ").append(articles.get(i).option).append(NEW_LINE);
            sb.append(TAB).append("Style: ").append(articles.get(i).style).append(NEW_LINE);
            sb.append(TAB).append("Color: ").append(articles.get(i).color).append(NEW_LINE);
            sb.append(TAB).append("Sizes: ").append(printSizes(articles.get(i).sizes)).append(NEW_LINE);
            sb.append(TAB).append("Supplier: ").append(articles.get(i).supplier).append(NEW_LINE);
            sb.append(TAB).append("Category: ").append(articles.get(i).category).append(NEW_LINE);
            sb.append(TAB).append("Prices: ").append(printPrices(articles.get(i).prices)).append(NEW_LINE);
        }
        return sb.toString();
    }

    private static String printBarcodes(final List<Barcode> barcodes) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < barcodes.size(); i++) {
            sb.append(NEW_LINE);
            sb.append(DOUBLE_TAB).append("Barcode ").append(i + 1).append(COLON);
            sb.append(barcodes.get(i).type).append(WHITESPACE);
            sb.append(barcodes.get(i).value);
        }
        return sb.toString();
    }

    private static String printSizes(final List<Size> sizes) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sizes.size(); i++) {
            sb.append(NEW_LINE);
            sb.append(DOUBLE_TAB).append("Size ").append(i + 1).append(COLON);
            sb.append(sizes.get(i).description).append(WHITESPACE);
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
            sb.append(DOUBLE_TAB).append("Price ").append(i + 1).append(COLON);
            sb.append(prices.get(i).currency).append(WHITESPACE);
            sb.append(prices.get(i).region).append(WHITESPACE);
            sb.append(prices.get(i).amount);
        }
        return sb.toString();
    }

    private static String printRetrievedArticlesByBarcodes(final List<Article> articles, final List<String> barcodes) {
        final StringBuilder sb = new StringBuilder("For barcodes: ");
        for (final String barcode : barcodes) {
            sb.append(barcode).append(COMMA);
        }
        sb.append(" found articles with names: ");
        for (int i = 0; i < articles.size(); i++) {
            sb.append(NEW_LINE).append(TAB).append(i + 1).append(DOT).append(articles.get(i).name);
        }
        return sb.toString();
    }

    private static String printRetrievedArticles(final List<Article> articles) {
        final StringBuilder sb = new StringBuilder("Retrieved articles with gtins and names: ");
        for (int i = 0; i < articles.size(); i++) {
            sb.append(NEW_LINE).append(TAB).append(i + 1).append(DOT);
            sb.append(articles.get(i).gtin).append(WHITESPACE);
            sb.append(articles.get(i).name);
        }
        return sb.toString();
    }
}
