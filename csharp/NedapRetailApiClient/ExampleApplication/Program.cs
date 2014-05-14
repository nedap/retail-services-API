using System;
using System.Collections.Generic;
using System.Configuration;
using System.IO;
using System.Net;

namespace Nedap.Retail.Api.Example
{
    class Program
    {
        static void Main(string[] args)
        {
            // OAuth client ID and secret should be included in oauth.config. See oauth.config.sample for the layout of this file.
            string oauthClientId = ConfigurationManager.AppSettings.Get("OauthClientId");
            string oauthSecret = ConfigurationManager.AppSettings.Get("OauthSecret");

            if ((oauthClientId == null) || (oauthSecret == null))
            {
                Console.WriteLine("OAuth is not configured!");
                Console.ReadKey();
                return;
            }

            Client client = new Client(oauthClientId, oauthSecret);
            client.SetApiBaseUrl(new Uri("https://api-test.nedapretail.com/")); // use test server, remove this line to use production server

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
                string stockId = client.ErpV1StockCapture(location, DateTime.Now, qList, "testing");
                Console.Write("stock ID = " + stockId);

                // request stock status
                Console.WriteLine("------------- Getting stock status");
                Erp.V1.StockSummary t = client.ErpV1StockStatus(stockId);
                Console.WriteLine(t.ToString());

                // request stock
                Console.WriteLine("------------- Retrieving stock");
                Erp.V1.Stock s = client.ErpV1StockRetrieve(stockId);
                Console.WriteLine(s.ToString());

                // request stock list
                Console.WriteLine("------------- Retrieving list of available stocks");
                List<Erp.V1.StockSummary> u = client.ErpV1StockList(location, null, null);
                Console.WriteLine("Got " + u.Count + " stocks:");
                foreach (Erp.V1.StockSummary z in u)
                {
                    Console.WriteLine(z.ToString());
                }
            }
            catch (WebException e)
            {
                Console.WriteLine("Exception occured:");
                Console.WriteLine(e.Status);
                HttpWebResponse r = (HttpWebResponse) e.Response;
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
    }
}
