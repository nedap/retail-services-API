using Nedap.Retail.Api.Workflow.V2;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;

namespace Nedap.Retail.Api.Example
{
    internal class WorkflowExample
    {
        public static void RunExample(Client client)
        {
            string location = "http://location-testing";

            WorkflowEvent workflow = new WorkflowEvent();
            workflow.Type = "cycle_count_started";
            workflow.EventTime = DateTime.Now;
            workflow.Location = location;
            workflow.EpcCount = 10;
            workflow.MessageIds = new List<string>() { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

            try
            {
                // capture
                Console.WriteLine("------------- Capture workflow event");
                string cap = client.WorkflowV2.Capture(workflow);
                Console.WriteLine("captured workflow events");
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