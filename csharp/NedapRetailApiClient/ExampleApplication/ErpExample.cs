using System;
using System.Collections.Generic;
using System.IO;
using System.Net;

namespace Nedap.Retail.Api.Example
{
    internal class ErpExample
    {
        public static void RunExample(Client client)
        {
            string location = "http://location-testing";

            List<Erp.V1.GtinQuantity> qList = new List<Erp.V1.GtinQuantity>();
            qList.Add(new Erp.V1.GtinQuantity("12345678901231", 23));
            qList.Add(new Erp.V1.GtinQuantity("12345678901248", 3));
            qList.Add(new Erp.V1.GtinQuantity("12345678901255", -3));
            qList.Add(new Erp.V1.GtinQuantity("12345678901262", 17));

            try
            {
                // send stock
                Console.WriteLine("------------- Uploading stock");
                string stockId = client.ErpV1.StockCapture(location, DateTime.Now, qList, "testing");
                Console.Write("stock ID = " + stockId);

                // request stock status
                Console.WriteLine("------------- Getting stock status");
                Erp.V1.StockSummary t = client.ErpV1.StockStatus(stockId);
                Console.WriteLine(t.ToString());

                // request stock
                Console.WriteLine("------------- Retrieving stock");
                Erp.V1.Stock s = client.ErpV1.StockRetrieve(stockId);
                Console.WriteLine(s.ToString());

                // request stock list
                Console.WriteLine("------------- Retrieving list of available stocks");
                List<Erp.V1.StockSummary> u = client.ErpV1.StockList(location, null, null);
                Console.WriteLine("Got " + u.Count + " stocks:");
                foreach (Erp.V1.StockSummary z in u)
                {
                    Console.WriteLine(z.ToString());
                }
            }
            catch (WebException e)
            {
                e.Print();
            }

            Console.ReadKey();
        }
    }
}