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

import com.nedap.retail.client.ApiClient;
import com.nedap.retail.client.ApiException;
import com.nedap.retail.client.api.ArticleApi;
import com.nedap.retail.client.model.*;
import com.nedap.retail.messages.ClientException;
import org.apache.commons.collections4.CollectionUtils;

public class ArticleExample {

    private ArticleExample() {
    }

    public static void runExample(final ApiClient client) {
        System.out.println(NEW_LINE + "*** Article API example ***");

        ArticleApi api = new ArticleApi(client);

        try {
            // Create or replace articles
            System.out.println(NEW_LINE + "Uploading articles...");
            final Articles exampleArticles = new Articles().addArticlesItem(createExampleArticle());

            api.articleCreateOrReplace(exampleArticles);
            System.out.println(printCapturedArticles(exampleArticles.getArticles()));

            // Article quantity
            System.out.println(NEW_LINE + "Retrieving article quantity...");
            final Long quantity = api.articleQuantity().getQuantity();
            System.out.println("Article quantity = " + quantity);

            // Article details by gtins
            System.out.println(NEW_LINE + "Retrieving article details by gtins...");
            final List<String> gtins = Arrays.asList(GTIN_1, GTIN_2);
            final List<Article> articlesWithGtins = api.articleRetrieve(gtins, null, null, null, null, null);
            System.out.println(printRetrievedArticlesByGtins(articlesWithGtins, gtins));

            // Find articles with search query
            System.out.println(NEW_LINE + "Finding article with search query: 'summer'...");
            final ArticleFindResponse articleFindResponse = api.articleFind("summer", 100, 0, null, null);
            System.out.println(printArticleFindResponse(articleFindResponse));

            // Article datails by barcodes with name field
            System.out.println(NEW_LINE + "Retrieving article names by barcodes...");
            final List<String> barcodes = Arrays.asList("2011200000019", "2011200000057");
            final List<String> fields = new ArrayList<>();
            fields.add("name");
            final List<Article> articlesWithBarcodes = api.articleRetrieve(null, barcodes, null ,null, null,
                    fields);
            System.out.println(printRetrievedArticlesByBarcodes(articlesWithBarcodes, barcodes));

            // Retrieve articles with gtin and name fields
            System.out.println(NEW_LINE + "Retrieving up to 50 articles with gtins and names...");
            fields.add("gtin");
            final List<Article> articles = api.articleRetrieve(null, null, null, 0, 50, fields);
            System.out.println(printRetrievedArticles(articles));

            System.out.println(NEW_LINE + "--- Article API example finished ---");

        } catch (final ApiException ex) {
            System.err.println("Server responded with an error: " + ex.getMessage());
        }
    }

    private static Article createExampleArticle() {
        final Article article = new Article();

        // required fields
        article.gtin(GTIN_1);
        article.name("T-shirt with V-neck and short sleeves");
        article.color(BLACK);
        article.sizes(setArticleSizes());

        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode().type(EAN13).value("2011200000019"));
        article.barcodes(barcodes);

        // optional fields
        article.code(GTIN_1);
        article.brand("Nedap Retail");
        article.season("Summer 2015");
        article.option("T-shirt with V-neck and short sleeves, Black");
        article.style("T-shirt with V-neck and short sleeves");
        article.supplier("Nedap Retail");
        article.category("T-shirt");
        article.prices(setArticlePrices());

        return article;
    }

    private static List<Size> setArticleSizes() {
        final List<Size> sizes = new ArrayList<>();
        sizes.add(new Size().description("32/35").region("NL"));
        sizes.add(new Size().description("32/33").region("DE"));
        sizes.add(new Size().description("31/35").region("GB"));
        return sizes;
    }

    private static List<Price> setArticlePrices() {
        final List<Price> prices = new ArrayList<>();
        prices.add(new Price().currency("EUR").region("NL").amount(29.99));
        prices.add(new Price().currency("EUR").region("DE").amount(33.99));
        prices.add(new Price().currency("GBP").region("GB").amount(24.99));
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
        sb.append("Total records before filtering: ").append(articleFindResponse.getRecordsTotal()).append(NEW_LINE);
        sb.append("Total records after filtering: ").append(articleFindResponse.getRecordsFiltered()).append(NEW_LINE);
        sb.append("Found articles: ");
        sb.append(printArticles(articleFindResponse.getArticles()));
        return sb.toString();
    }

    private static String printArticles(final List<Article> articles) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < articles.size(); i++) {
            sb.append(NEW_LINE).append(NEW_LINE);
            sb.append(TAB).append("Article ").append(i + 1).append(COLON).append(NEW_LINE);
            sb.append(TAB).append("Gtin: ").append(articles.get(i).getGtin()).append(NEW_LINE);
            sb.append(TAB).append("Barcodes: ").append(printBarcodes(articles.get(i).getBarcodes())).append(NEW_LINE);
            sb.append(TAB).append("Code: ").append(articles.get(i).getCode()).append(NEW_LINE);
            sb.append(TAB).append("Brand: ").append(articles.get(i).getBrand()).append(NEW_LINE);
            sb.append(TAB).append("Season: ").append(articles.get(i).getSeason()).append(NEW_LINE);
            sb.append(TAB).append("Name: ").append(articles.get(i).getName()).append(NEW_LINE);
            sb.append(TAB).append("Option: ").append(articles.get(i).getOption()).append(NEW_LINE);
            sb.append(TAB).append("Style: ").append(articles.get(i).getStyle()).append(NEW_LINE);
            sb.append(TAB).append("Color: ").append(articles.get(i).getColor()).append(NEW_LINE);
            sb.append(TAB).append("Sizes: ").append(printSizes(articles.get(i).getSizes())).append(NEW_LINE);
            sb.append(TAB).append("Supplier: ").append(articles.get(i).getSupplier()).append(NEW_LINE);
            sb.append(TAB).append("Category: ").append(articles.get(i).getCategory()).append(NEW_LINE);
            sb.append(TAB).append("Prices: ").append(printPrices(articles.get(i).getPrices())).append(NEW_LINE);
        }
        return sb.toString();
    }

    private static String printBarcodes(final List<Barcode> barcodes) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < barcodes.size(); i++) {
            sb.append(NEW_LINE);
            sb.append(DOUBLE_TAB).append("Barcode ").append(i + 1).append(COLON);
            sb.append(barcodes.get(i).getType()).append(WHITESPACE);
            sb.append(barcodes.get(i).getValue());
        }
        return sb.toString();
    }

    private static String printSizes(final List<Size> sizes) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sizes.size(); i++) {
            sb.append(NEW_LINE);
            sb.append(DOUBLE_TAB).append("Size ").append(i + 1).append(COLON);
            sb.append(sizes.get(i).getDescription()).append(WHITESPACE);
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
            sb.append(NEW_LINE);
            sb.append(DOUBLE_TAB).append("Price ").append(i + 1).append(COLON);
            sb.append(prices.get(i).getCurrency()).append(WHITESPACE);
            sb.append(prices.get(i).getRegion()).append(WHITESPACE);
            sb.append(prices.get(i).getAmount());
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
            sb.append(NEW_LINE).append(TAB).append(i + 1).append(DOT).append(articles.get(i).getName());
        }
        return sb.toString();
    }

    private static String printRetrievedArticles(final List<Article> articles) {
        final StringBuilder sb = new StringBuilder("Retrieved articles with gtins and names: ");
        for (int i = 0; i < articles.size(); i++) {
            sb.append(NEW_LINE).append(TAB).append(i + 1).append(DOT);
            sb.append(articles.get(i).getGtin()).append(WHITESPACE);
            sb.append(articles.get(i).getName());
        }
        return sb.toString();
    }
}
