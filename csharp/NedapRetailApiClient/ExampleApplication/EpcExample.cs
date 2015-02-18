using Nedap.Retail.Api.Epc.V2;
using System;
using System.Net;

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
                e.Print();
            }

            Console.ReadKey();
        }
    }
}