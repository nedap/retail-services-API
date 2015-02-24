package com.nedap.retail.services.examples;

import java.util.ArrayList;
import java.util.List;

import com.nedap.retail.messages.Client;
import com.nedap.retail.messages.article.Article;
import com.nedap.retail.messages.article.Barcode;
import com.nedap.retail.messages.article.Price;
import com.nedap.retail.messages.article.Size;
import com.sun.jersey.api.client.UniformInterfaceException;

public class ArticleExample {
    public static void runExample(final Client client) {
        System.out.println("*** Article API example ***");

        // this is our example data
        final List<Article> exampleArticles = new ArrayList<>();
        exampleArticles.add(createExampleArticle());
        // we could add more articles here

        try {
            // send articles
            System.out.println("--- Uploading articles");
            client.captureArticles(exampleArticles);

            // get quantity
            System.out.println("--- Retrieving article quantity");
            final Long quantity = client.articleQuantity();
            System.out.println("article quantity = " + quantity);

        } catch (final UniformInterfaceException e) {
            System.err.println("Server responded with an error:");
            System.err.println(e.getResponse().getEntity(String.class));
        }
        System.out.println("--- Done");
    }

    private static Article createExampleArticle() {
        final Article article = new Article();

        // GTIN is the identifier of a product. Most barcodes can be translated to GTIN.
        article.setGtin("08701231234562");

        // at least one linear barcode is required
        final List<Barcode> barcodes = new ArrayList<>();
        barcodes.add(new Barcode("EAN13", "8701231234562"));
        article.setBarcodes(barcodes);

        // name is required
        article.setName("Test article");

        // color is required
        article.setColor("blue");

        // size is required
        final List<Size> sizes = new ArrayList<>();
        sizes.add(new Size("42", "EU"));
        sizes.add(new Size("40", "US"));
        article.setSizes(sizes);

        // the rest is optional
        article.setCode("some retailer specific article code");
        article.setBrand("example brand");
        article.setSeason("Summer 2014");
        article.setOption("Test article blue");
        article.setStyle("example style");
        article.setSupplier("example supplier");
        article.setCategory("Sports");
        article.setMarkdown(true);
        article.setPrices(setArticlePrices());

        return article;
    }

    private static List<Price> setArticlePrices() {
        final List<Price> prices = new ArrayList<>();
        prices.add(new Price("EUR", "NL", 19.95));
        prices.add(new Price("EUR", "BE", 18.95));
        prices.add(new Price("EUR", "DE", 19.95));
        prices.add(new Price("EUR", "FR", 20.95));
        prices.add(new Price("NOK", "NO", 160.0));
        prices.add(new Price("USD", "US", 19.95));
        return prices;
    }
}