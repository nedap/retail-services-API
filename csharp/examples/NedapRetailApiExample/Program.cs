using System;
using Nedap.Retail.Api.Client;
using Nedap.Retail.Api.Api;

namespace Nedap.Retail.Api.Example
{
    internal class Program
    {
        private static void Main(string[] args)
        {

            string accessToken = System.Configuration.ConfigurationManager.AppSettings.Get("AccessToken");
            string apiUrl = System.Configuration.ConfigurationManager.AppSettings.Get("ApiUrl");
            
            if (apiUrl == null || accessToken == null)
            {
                Console.WriteLine("Invalid configuration!");
                Console.ReadKey();
                return;
            }

            try {
              
                Configuration configuration = new Configuration(new ApiClient(apiUrl));
                configuration.AccessToken = accessToken;

                // by default use production server, uncomment this line to use test server
                //client.SetApiBaseUrl(new Uri("https://api-test.nedapretail.com/"));

                Console.WriteLine("Nedap Retail API examples");
                Console.WriteLine("-------------------------");
                Console.WriteLine("Which example do you want to run?");
                Console.WriteLine("1. ERP API");
                Console.WriteLine("2. EPC API");
                Console.WriteLine("3. Article API");
                Console.WriteLine("4. Epcis API");
                Console.WriteLine("5. Workflow API");
                Console.Write("Your choice: ");
                ConsoleKeyInfo key = Console.ReadKey();
                Console.WriteLine();

                switch (key.Key)
                {
                    case ConsoleKey.D1:
                        ErpExample.RunExample(configuration);
                        break;
                    case ConsoleKey.D2:
                        EpcExample.RunExample(configuration);
                        break;
                    case ConsoleKey.D3:
                        ArticleExample.RunExample(configuration);
                        break;
                    case ConsoleKey.D4:
                        EpcisExample.RunExample(configuration);
                        break;
                    case ConsoleKey.D5:
                        WorkflowExample.RunExample(configuration);
                        break;
                }

            } catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }          
        }
    }
}