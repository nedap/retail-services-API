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

                Console.WriteLine("Nedap Retail !D Cloud API examples");
                Console.WriteLine("-------------------------");
                Console.WriteLine("Which example do you want to run?");
                Console.WriteLine("1 : Article API");
                Console.WriteLine("2 : EPCIS API");
                Console.WriteLine("3 : ERP API");
                Console.WriteLine("4 : Workflow API");
                Console.WriteLine("0 : Quit");

                Console.Write("Your choice: ");
                ConsoleKeyInfo key = Console.ReadKey();
                Console.WriteLine();

                switch (key.Key)
                {
                    case ConsoleKey.D1:
                        ArticleExample.RunExample(configuration);
                        break;
                    case ConsoleKey.D2:
                        EpcisExample.RunExample(configuration);
                        break;
                    case ConsoleKey.D3:
                        ErpExample.RunExample(configuration);
                        break;
                    case ConsoleKey.D4:
                        WorkflowExample.RunExample(configuration);
                        break;
                    case ConsoleKey.D0:
                        return;
                }

            } catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }          
        }
    }
}