using Nedap.Retail.Api.Api;
using Nedap.Retail.Api.Client;
using Nedap.Retail.Api.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Nedap.Retail.Api.Example
{
    internal class ArticleExample
{
    public static void RunExample(Configuration configuration)
    {
        try
        {
            ArticleApi articleApi = new ArticleApi(configuration);
            // create or replace
            Console.WriteLine("------------- Create or replace articles");
            List<Article> articlesList = new List<Article>();
            articlesList.Add(CreateArticle());
            Articles articles = new Articles(articlesList);
            articleApi.CreateOrReplaceArticles(articles);

            // get quantity
            Console.WriteLine("------------- Retrieving article quantity");
            long? quantity = articleApi.GetArticleQuantity().Quantity;
            Console.WriteLine("article quantity = " + quantity);
        }
        catch (ApiException e)
        {
                Console.WriteLine(e);
        }

        Console.ReadKey();
    }

    private static Article CreateArticle()
    {
        var article = new Article();
        article.Gtin = "03327009483366";
        article.Barcodes = new List<Barcode>()
            {
                new Barcode() { Type = "ean_13", Value = "3327009483366" },
                new Barcode() { Type = "ean_13", Value = "3327009483427" }
            };
        article.Code = "74478-94565";
        article.Brand = "Nedap";
        article.Season = "Summer 2014";
        article.Name = "T-shirt short sleeve, v-neck";
        article.Option = "T-shirt short sleeve, v-neck, Black";
        article.Style = "T-shirt short sleeve, v-neck";
        article.Color = "Black";
        article.Sizes = new List<Size>()
            {
                new Size() { Region = "NL", Description = "32/34" },
                new Size() { Region = "DE", Description = "33/32" }
            };
        article.Supplier = "Nedap Retail";
        article.Prices = new List<Price>()
            {
                new Price() { Currency = "EUR", Region = "NL", Amount = 32.95 },
                new Price() { Currency = "NOK", Region = "NO", Amount = 3000 }
            };
        return article;
    }
}
}