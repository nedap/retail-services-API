using Nedap.Retail.Api.Article.V2;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;

namespace Nedap.Retail.Api.Example
{
    internal class ArticleExample
    {
        public static void RunExample(Client client)
        {
            var article = CreateArticle();

            try
            {
                // create or replace
                Console.WriteLine("------------- Create or replace articles");
                string cr = client.ArticleV2.CreateOrReplace(new List<Article.V2.Article>() { article });
                Console.WriteLine("create or replace response: " + cr);

                // get quantity
                Console.WriteLine("------------- Retrieving article quantity");
                long quantity = client.ArticleV2.Quantity();
                Console.WriteLine("article quantity = " + quantity);
            }
            catch (WebException e)
            {
                Console.WriteLine("Exception occured:");
                Console.WriteLine(e.Status);
                HttpWebResponse r = (HttpWebResponse)e.Response;
                Console.WriteLine(r.StatusCode);
                Stream responseStream = r.GetResponseStream();

                string responseText;
                if (responseStream != null)
                {
                    using (StreamReader reader = new StreamReader(responseStream))
                    {
                        responseText = reader.ReadToEnd();
                    }
                    Console.WriteLine(responseText);
                }
            }

            Console.ReadKey();
        }

        private static Article.V2.Article CreateArticle()
        {
            var article = new Article.V2.Article();
            article.Gtin = "03327009483366";
            article.Barcodes = new List<Barcode>()
            {
                new Barcode() { Type = "ean_13", Value = "3327009483366" },
                new Barcode() { Type = "ean_13", Value = "3327009483427" },
                new Barcode() { Type = "ean_13", Value = "(01)95012345678903(3103)000123" }
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