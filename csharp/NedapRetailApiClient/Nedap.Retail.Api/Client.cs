using Nedap.Retail.Api.Article.V2;
using Nedap.Retail.Api.Epc.V2;
using Nedap.Retail.Api.Epcis.V1_1;
using Nedap.Retail.Api.Erp.V1;
using Nedap.Retail.Api.OAuth;
using Nedap.Retail.Api.System.V1;
using Nedap.Retail.Api.Workflow.V2;
using System;

namespace Nedap.Retail.Api
{
    /// <summary>
    /// Nedap Retail API client
    /// </summary>
    public class Client : IDisposable
    {
        private bool disposed;
        private ApiCaller apiCaller;

        /// <summary>
        /// Create a API client instance
        /// </summary>
        /// <param name="clientId">OAuth 2.0 client ID</param>
        /// <param name="secret">OAuth 2.0 secret</param>
        public Client(string clientId, string secret)
        {
            apiCaller = new ApiCaller(clientId, secret);

            ArticleV2 = new ArticleV2Endpoint(apiCaller);
            EpcV2 = new EpcV2Endpoint(apiCaller);
            EpcisV1_1 = new EpcisV1_1Endpoint(apiCaller);
            ErpV1 = new ErpV1Endpoint(apiCaller);
            SystemV1 = new SystemV1Endpoint(apiCaller);
            WorkflowV2 = new WorkflowV2Endpoint(apiCaller);
        }

        /// <summary>
        /// Article V2 API endpoint
        /// </summary>
        public ArticleV2Endpoint ArticleV2 { get; private set; }

        /// <summary>
        /// Epcis V1.1 API endpoint
        /// </summary>
        public EpcisV1_1Endpoint EpcisV1_1 { get; private set; }

        /// <summary>
        /// Epc V2 API endpoint
        /// </summary>
        public EpcV2Endpoint EpcV2 { get; private set; }

        /// <summary>
        /// Erp V1 API endpoint
        /// </summary>
        public ErpV1Endpoint ErpV1 { get; private set; }

        /// <summary>
        /// System V1 API endpoint
        /// </summary>
        public SystemV1Endpoint SystemV1 { get; private set; }

        /// <summary>
        /// Workflow V2 API endpoint
        /// </summary>
        public WorkflowV2Endpoint WorkflowV2 { get; private set; }

        /// <summary>
        /// Set base URL for the API calls (default: https://api.nedapretail.com/)
        /// </summary>
        /// <param name="ApiBaseUrl">The URL to use to resolve partial and relative URLs</param>
        public void SetApiBaseUrl(Uri ApiBaseUrl)
        {
            apiCaller.ApiBaseUrl = ApiBaseUrl;
        }

        /// <summary>
        /// Dispose client
        /// </summary>
        public void Dispose()
        {
            Dispose(true);
            GC.SuppressFinalize(this);
        }

        private void Dispose(bool disposing)
        {
            if (disposed)
            {
                return;
            }

            if (disposing)
            {
                if (apiCaller != null)
                {
                    apiCaller.Dispose();
                    apiCaller = null;
                }
            }

            disposed = true;
        }
    }
}