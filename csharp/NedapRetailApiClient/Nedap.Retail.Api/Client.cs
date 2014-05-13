using Nedap.Retail.Api.OAuth;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;

namespace Nedap.Retail.Api
{
    /// <summary>
    /// Nedap Retail API client
    /// </summary>
    public class Client
    {
        private ApiCaller ApiCaller;

        /// <summary>
        /// Create a API client instance
        /// </summary>
        /// <param name="clientId">OAuth 2.0 client ID</param>
        /// <param name="secret">OAuth 2.0 secret</param>
        public Client(string clientId, string secret)
        {
            ApiCaller = new ApiCaller(clientId, secret);
        }

        /// <summary>
        /// Set base URL for the API calls (default: https://api.nedapretail.com/)
        /// </summary>
        /// <param name="ApiBaseUrl">The URL to use to resolve partial and relative URLs</param>
        public void SetApiBaseUrl(Uri ApiBaseUrl)
        {
            ApiCaller.ApiBaseUrl = ApiBaseUrl;
        }

        /// <summary>
        /// Capture the ERP stock for a specific location on a specific time. The ERP stock is described in GTIN + quantity.
        /// </summary>
        /// <param name="location">The location for which the stock is defined</param>
        /// <param name="eventTime">Time of export from ERP</param>
        /// <param name="quantityList">Complete stock summary for this location at this eventTime</param>
        /// <param name="externRef">Optional, external reference that can be used to identify this ERP stock count</param>
        /// <returns>Stock ID of the saved ERP stock</returns>
        public string ErpV1StockCapture(string location, DateTime eventTime, List<Erp.V1.GtinQuantity> quantityList, String externRef)
        {
            Erp.V1.CaptureRequest request = new Erp.V1.CaptureRequest();
            request.Location = location;
            request.EventTime = eventTime;
            request.QuantityList = quantityList;
            request.ExternRef = externRef;
            Erp.V1.CaptureResponse response = ApiCaller.Post<Erp.V1.CaptureResponse>("/erp/v1/stock.capture", request);
            return response.Id;
        }

        /// <summary>
        /// Retrieve ERP stock using stock ID. The stock is described in GTIN + quantity.
        /// </summary>
        /// <param name="stockId">ID of the ERP stock</param>
        /// <returns>ERP stock</returns>
        public Erp.V1.Stock ErpV1StockRetrieve(string stockId)
        {
            NameValueCollection parameters = new NameValueCollection();
            parameters.Add("id", stockId);
            return ApiCaller.Get<Erp.V1.Stock>("/erp/v1/stock.retrieve", parameters);
        }

        /// <summary>
        /// Retrieves stock summary for given stock ID
        /// </summary>
        /// <param name="stockId">ID of the ERP stock</param>
        /// <returns>Stock summary</returns>
        public Erp.V1.StockSummary ErpV1StockStatus(string stockId)
        {
            NameValueCollection parameters = new NameValueCollection();
            parameters.Add("id", stockId);
            return ApiCaller.Get<Erp.V1.StockSummary>("/erp/v1/stock.status", parameters);
        }

        /// <summary>
        /// Retrieves ERP stock summary list for given location
        /// </summary>
        /// <param name="location">The location for which the stock is defined</param>
        /// <param name="fromEventTime">Optional. Defines date+time since when the cycle counts are required. When null: returns summaries for all stocks for specified location since the epoch.</param>
        /// <param name="untilEventTime">Optional. Defines date+time until when the cycle counts are required. When null: returns summaries for all stocks for specified location up until now.</param>
        /// <returns>List of Stock summary</returns>
        public List<Erp.V1.StockSummary> ErpV1StockList(string location, DateTime? fromEventTime, DateTime? untilEventTime)
        {
            NameValueCollection parameters = new NameValueCollection();
            parameters.Add("location", location);
            if (fromEventTime != null)
            {
                DateTime from = (DateTime)fromEventTime;
                parameters.Add("from_event_time", from.ToString("o"));
            }
            if (untilEventTime != null)
            {
                DateTime until = (DateTime)untilEventTime;
                parameters.Add("until_event_time", until.ToString("o"));
            }
            return ApiCaller.Get<List<Erp.V1.StockSummary>>("/erp/v1/stock.list", parameters);
        }



    }
}
