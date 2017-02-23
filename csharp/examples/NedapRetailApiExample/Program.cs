using System;
using Nedap.Retail.Api.Client;
using Nedap.Retail.Api.Api;

namespace Nedap.Retail.Api.Example
{
    internal class Program
    {
        private static void Main(string[] args)
        {

            string oauthClientId = System.Configuration.ConfigurationManager.AppSettings.Get("OauthClientId");
            string oauthSecret = System.Configuration.ConfigurationManager.AppSettings.Get("OauthSecret");
            string apiUrl = System.Configuration.ConfigurationManager.AppSettings.Get("ApiUrl");
            string oauthUrl = System.Configuration.ConfigurationManager.AppSettings.Get("OauthUrl");
            
            if (oauthClientId == null || oauthSecret == null || apiUrl == null || oauthUrl == null)
            {
                Console.WriteLine("Invalid configuration!");
                Console.ReadKey();
                return;
            }

            try {
                AccessTokenResolverApi accessTokenResolverApi = new AccessTokenResolverApi(oauthUrl);
                string accessToken = accessTokenResolverApi.Resolve("client_credentials", oauthClientId, oauthSecret).AccessToken;

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

            // OAuth client ID and secret should be included in oauth.config. See oauth.config.sample for the layout of this file.
            /*
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
                        ErpExample.RunExample(client);
                        break;
                    case ConsoleKey.D2:
                        EpcExample.RunExample(client);
                        break;
                    case ConsoleKey.D3:
                        ArticleExample.RunExample(client);
                        break;
                    case ConsoleKey.D4:
                        EpcisExample.RunExample(client);
                        break;
                    case ConsoleKey.D5:
                        WorkflowExample.RunExample(client);
                        break;
                }
            }
        }
        */
        }
    }
}