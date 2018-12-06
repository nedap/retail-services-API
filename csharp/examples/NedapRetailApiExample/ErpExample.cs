using Nedap.Retail.Api.Api;
using Nedap.Retail.Api.Client;
using Nedap.Retail.Api.Model;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Example
{
    internal class ErpExample
    {
        public static void RunExample(Configuration configuration)
        {
            string location = "http://nedapretail.com/loc/testing";

            List<GtinQuantity> qList = new List<GtinQuantity>();
            qList.Add(new GtinQuantity("12345678901231", 23));
            qList.Add(new GtinQuantity("12345678901248", 3));
            qList.Add(new GtinQuantity("12345678901255", -3));
            qList.Add(new GtinQuantity("12345678901262", 17));

            try
            {
                Console.WriteLine(configuration.AccessToken);
                ErpStockApi erpStockApi = new ErpStockApi(configuration);

                // send stock
                Console.WriteLine("------------- Uploading stock");
                ErpStock stock = new ErpStock();
                stock.Location = location;
                stock.EventTime = DateTime.Now;
                stock.QuantityList = qList;
                string stockId = erpStockApi.CreateErpStock(stock).Id;
                Console.Write("stock ID = " + stockId);

                // request stock status
                Console.WriteLine("------------- Getting stock status");
                ErpStockSummary stockSummary = erpStockApi.RetrieveErpStockSummary(stockId);
                Console.WriteLine(stockSummary.ToString());

                // request stock
                Console.WriteLine("------------- Retrieving stock");
                ErpStock retrievedStock = erpStockApi.RetrieveErpStock(stockId);
                Console.WriteLine(retrievedStock.ToString());

                // request stock list
                Console.WriteLine("------------- Retrieving list of available stocks");
                List<ErpStockSummary> stockSummaryList = erpStockApi.ListErpStocks(location);
                Console.WriteLine("Got " + stockSummaryList.Count + " stocks:");
                foreach (ErpStockSummary ss in stockSummaryList)
                {
                    Console.WriteLine(ss.ToString());
                }
            }
            catch (ApiException e)
            {
                Console.WriteLine(e);
            }

            Console.WriteLine("Press a key to continue...");
            Console.ReadKey();
        }
    }
}
