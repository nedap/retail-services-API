using System;
using System.Configuration;

namespace Nedap.Retail.Api.Example
{
    internal class Program
    {
        private static void Main(string[] args)
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

            using (Client client = new Client(oauthClientId, oauthSecret))
            {
                //client.SetApiBaseUrl(new Uri("https://api-test.nedapretail.com/")); // use test server, remove this line to use production server

                Console.WriteLine("Nedap Retail API examples");
                Console.WriteLine("-------------------------");
                Console.WriteLine("Which example do you want to run?");
                Console.WriteLine("1. System API");
                Console.WriteLine("2. ERP API");
                Console.WriteLine("3. EPC API");
                Console.WriteLine("4. Article API");
                Console.WriteLine("5. Epcis API");
                Console.WriteLine("6. Workflow API");
                Console.Write("Your choice: ");
                ConsoleKeyInfo key = Console.ReadKey();
                Console.WriteLine();

                switch (key.Key)
                {
                    case ConsoleKey.D1:
                        SystemExample.RunExample(client);
                        break;
                    case ConsoleKey.D2:
                        ErpExample.RunExample(client);
                        break;
                    case ConsoleKey.D3:
                        EpcExample.RunExample(client);
                        break;
                    case ConsoleKey.D4:
                        ArticleExample.RunExample(client);
                        break;
                    case ConsoleKey.D5:
                        EpcisExample.RunExample(client);
                        break;
                    case ConsoleKey.D6:
                        WorkflowExample.RunExample(client);
                        break;
                }
            }
        }
    }
}