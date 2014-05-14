using Newtonsoft.Json;
using System;
using System.Collections.Specialized;
using System.Net;
using System.Reflection;

namespace Nedap.Retail.Api.OAuth
{
    /// <summary>
    /// An OAuth access token and token information
    /// </summary>
    internal class AccessTokenInfo
    {
        /// <summary>
        /// The access token to include in every request
        /// </summary>
        [JsonProperty("access_token")]
        public String AccessToken { get; private set; }

        /// <summary>
        /// Token type
        /// </summary>
        [JsonProperty("token_type")]
        public String TokenType { get; private set; }

        /// <summary>
        /// Remaining lifetime of the token in seconds
        /// </summary>
        [JsonProperty("expires_in")]
        public int ExpiresIn { get; private set; }

        /// <summary>
        /// Scope of token
        /// </summary>
        [JsonProperty("scope")]
        public String Scope { get; private set; }

        /// <summary>
        /// Date/time the access token expires
        /// </summary>
        public DateTime ExpiresAt { get; private set; }

        /// <summary>
        /// If token is expired you need to request a new token
        /// </summary>
        public bool IsExpired
        {
            get
            {
                return (ExpiresAt < DateTime.Now);
            }
            private set
            {
            }
        }

        /// <summary>
        /// Returns string representation of the object
        /// </summary>
        /// <returns>Formatted JSON string</returns>
        public override string ToString()
        {
            return JsonConvert.SerializeObject(this, Formatting.Indented);
        }

        /// <summary>
        /// Request a new access token
        /// </summary>
        /// <param name="oAuthTokenUri">Full URL to the OAuth 2.0 login server</param>
        /// <param name="clientId">OAuth 2.0 client ID (supplied by Nedap)</param>
        /// <param name="secret">OAuth 2.0 secret (supplied by Nedap)</param>
        /// <returns>An OAuth access token and token information</returns>
        public static AccessTokenInfo RequestNewToken(Uri oAuthTokenUri, string clientId, string secret)
        {
            WebClient client = new WebClient();
            string version = Assembly.GetExecutingAssembly().GetName().Version.ToString();
            client.Headers.Add("user-agent", "Nedap.Retail.Api.Client/" + version);
            NameValueCollection requestParameters = new NameValueCollection();
            requestParameters.Add("grant_type", "client_credentials");
            requestParameters.Add("client_id", clientId);
            requestParameters.Add("client_secret", secret);
            byte[] responsebytes = client.UploadValues(oAuthTokenUri, requestParameters);
            string response = System.Text.Encoding.UTF8.GetString(responsebytes);

            // parse response
            AccessTokenInfo token = JsonConvert.DeserializeObject<AccessTokenInfo>(response);
            token.ExpiresAt = DateTime.Now.AddSeconds(token.ExpiresIn - 10);    // expire 10 seconds earlier
            return token;
        }
    }
}
