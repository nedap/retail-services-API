using Newtonsoft.Json;
using System;
using System.Collections.Specialized;
using System.Net;
using System.Reflection;
using System.Web;

namespace Nedap.Retail.Api.OAuth
{
    /// <summary>
    /// Takes care of parameter encoding, sending the request and decoding the response
    /// </summary>
    internal class ApiCaller
    {
        /// <summary>
        /// OAuth 2.0 client ID
        /// </summary>
        private string ClientId;

        /// <summary>
        /// OAuth 2.0 secret
        /// </summary>
        private string Secret;

        /// <summary>
        /// URL of OAuth 2.0 server
        /// </summary>
        private string OauthTokenUrl = "/login/oauth/token";

        /// <summary>
        /// OAuth 2.0 Access token info
        /// </summary>
        private AccessTokenInfo AccessTokenInfo;

        /// <summary>
        /// Reusable WebClient instance
        /// </summary>
        private WebClient WebClient = new WebClient();

        /// <summary>
        /// Base URL for relative API calls
        /// </summary>
        public Uri ApiBaseUrl { get; set; }

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="clientId">OAuth 2.0 client ID</param>
        /// <param name="secret">OAuth 2.0 secret</param>
        public ApiCaller(string clientId, string secret)
        {
            ClientId = clientId;
            Secret = secret;
            ApiBaseUrl = new Uri("https://api.nedapretail.com/");
            string version = Assembly.GetExecutingAssembly().GetName().Version.ToString();
            WebClient.Headers.Add("user-agent", "Nedap.Retail.Api.Client/" + version);
        }

        /// <summary>
        /// Checks if we have (valid) access token and adds this token as a authorization header
        /// </summary>
        private void CheckOAuthToken()
        {
            if ((AccessTokenInfo == null) || AccessTokenInfo.IsExpired)
            {
                AccessTokenInfo = AccessTokenInfo.RequestNewToken(NormalizeUrl(OauthTokenUrl), ClientId, Secret);
                WebClient.Headers.Remove("Authorization");
                WebClient.Headers.Add("Authorization", "Bearer " + AccessTokenInfo.AccessToken);
            }
        }

        /// <summary>
        /// Adds base URL when using a relative URL
        /// </summary>
        /// <param name="url">Full (https://api.nedapretail.com/example/v1/url) or relative (/example/v1/url) URL</param>
        /// <returns>Full URL</returns>
        private Uri NormalizeUrl(string url)
        {
            return NormalizeUrl(url, null);
        }

        /// <summary>
        /// Adds base URL when using a relative URL
        /// </summary>
        /// <param name="url">Full (https://api.nedapretail.com/example/v1/url) or relative (/example/v1/url) URL</param>
        /// <param name="parameters">Extra parameters that need to be added</param>
        /// <returns>Full URL</returns>
        private Uri NormalizeUrl(string url, NameValueCollection parameters)
        {
            // first convert relative/partial URL to full URL
            Uri fullUrl;
            if (url.StartsWith("http", StringComparison.CurrentCultureIgnoreCase))
            {
                fullUrl = new Uri(url);
            }
            else
            {
                fullUrl = new Uri(ApiBaseUrl, url);
            }
            // add extra parameters
            UriBuilder uriBuilder = new UriBuilder(fullUrl);
            if (parameters != null)
            {
                NameValueCollection requestParameters = HttpUtility.ParseQueryString(uriBuilder.Query);
                foreach (string key in parameters)
                {
                    requestParameters.Add(key, parameters[key]);
                }
                uriBuilder.Query = requestParameters.ToString();
            }

            return uriBuilder.Uri;
        }

        /// <summary>
        /// Parses JSON string to object of specified class
        /// </summary>
        /// <typeparam name="T">Class to parse JSON to</typeparam>
        /// <param name="data">JSON formatted string</param>
        /// <returns>Object of specified class</returns>
        private T FromJson<T>(string data)
        {
            return JsonConvert.DeserializeObject<T>(data);
        }

        /// <summary>
        /// Parses object to JSON string
        /// </summary>
        /// <param name="data">Object to parse to JSON</param>
        /// <returns>JSON formatted string</returns>
        private string ToJson(object data)
        {
            return JsonConvert.SerializeObject(data);
        }

        /// <summary>
        /// Sends HTTP GET request to API endpoint
        /// </summary>
        /// <typeparam name="T">Class name of expected response</typeparam>
        /// <param name="url">Full (https://api.nedapretail.com/example/v1/url) or relative (/example/v1/url) URL</param>
        /// <param name="parameters">Extra parameters that need to be added</param>
        /// <returns>The response of the API call</returns>
        public T Get<T>(string url, NameValueCollection parameters)
        {
            Uri Url = NormalizeUrl(url, parameters);
            CheckOAuthToken();
            String result = WebClient.DownloadString(Url);
            return FromJson<T>(result);
        }

        /// <summary>
        /// Sends HTTP POST request to API endpoint
        /// </summary>
        /// <typeparam name="T">Class name of expected response</typeparam>
        /// <param name="url">Full (https://api.nedapretail.com/example/v1/url) or relative (/example/v1/url) URL</param>
        /// <param name="data">JSON formatted data</param>
        /// <returns>The response of the API call</returns>
        public T Post<T>(string url, object data)
        {
            Uri Url = NormalizeUrl(url);
            CheckOAuthToken();
            String result = WebClient.UploadString(Url, ToJson(data));
            return FromJson<T>(result);
        }
    }
}
