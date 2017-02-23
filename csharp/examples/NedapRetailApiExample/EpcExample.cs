using System;
using Nedap.Retail.Api.Client;
using Nedap.Retail.Api.Api;
using Nedap.Retail.Api.Model;

namespace Nedap.Retail.Api.Example
{
    internal class EpcExample
    {
        public static void RunExample(Configuration configuration)
        {
            string location = "http://location-testing";

            try
            {

                // get difference list
                Console.WriteLine("------------- Retrieving difference list");
                DifferenceListApi differenceListApi = new DifferenceListApi(configuration);
                DifferenceListResponse dl = differenceListApi.Retrieve("12345678901231", null, null, null);
                Console.WriteLine(dl.ToString());

                // get stock gtin
                Console.WriteLine("------------- Retrieving stock gtin");
                RfidCountApi rfidCountApi = new RfidCountApi(configuration);
                StockResponse sg = rfidCountApi.Retrieve(location);
                Console.WriteLine(sg.ToString());
    }
            catch (ApiException e)
            {
                Console.WriteLine(e.ToString());
            }

            Console.ReadKey();
        }
    }
}