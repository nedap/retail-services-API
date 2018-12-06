using Nedap.Retail.Api.Api;
using Nedap.Retail.Api.Client;
using Nedap.Retail.Api.Model;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Example
{
    internal class WorkflowExample
    {
        public static void RunExample(Configuration confgiration)
        {
            string location = "http://nedapretail.com/loc/testing";

            WorkflowEvent workflow = new WorkflowEvent("cycle_count_finished", location, DateTime.Now, null, 10, new List<string>() { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });

            try
            {
                // capture
                WorkflowApi workflowApi = new WorkflowApi(confgiration);
                Console.WriteLine("------------- Capture workflow event");
                workflowApi.CaptureWorkflowEvent(workflow);
                Console.WriteLine("captured workflow events");
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
