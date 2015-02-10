using Nedap.Retail.Api.Epc.V2;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;

namespace Nedap.Retail.Api.Example
{
    internal class EpcExample
    {
        public static void RunExample(Client client)
        {
            string location = "http://location-testing";

            try
            {
                // get difference list
                Console.WriteLine("------------- Retrieving difference list");
                DifferenceListResponse dl = client.EpcV2.DifferenceList("12345678901231", null, null, null);
                Console.WriteLine(dl.ToString());

                // get stock gtin
                Console.WriteLine("------------- Retrieving stock gtin");
                StockGtinResponse sg = client.EpcV2.StockGtin(location, null, null, null, null);
                Console.WriteLine(sg.ToString());
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
    }
}