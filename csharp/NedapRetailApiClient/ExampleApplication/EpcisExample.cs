using Nedap.Retail.Api.Epcis.V1._1;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;

namespace Nedap.Retail.Api.Example
{
    internal class EpcisExample
    {
        public static void RunExample(Client client)
        {
            List<EpcisEvent> events = new List<EpcisEvent>();
            events.Add(new ObjectEvent());
            events.Add(new AggregationEvent());
            events.Add(new ObjectEvent());

            try
            {
                // capture
                Console.WriteLine("------------- Capture epcis events");
                string cap = client.EpcisV1_1.Capture(events);
                Console.WriteLine("result = " + cap);
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