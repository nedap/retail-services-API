using Nedap.Retail.Api.OAuth;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;

namespace Nedap.Retail.Api.Erp.V1
{
    /// <summary>
    /// Erp V1 API endpoint
    /// </summary>
    public class ErpV1Endpoint
    {
        private ApiCaller apiCaller;

        internal ErpV1Endpoint(ApiCaller apiCaller)
        {
            this.apiCaller = apiCaller;
        }

        /// <summary>
        /// Capture the ERP stock for a specific location on a specific time. The ERP stock is described in GTIN + quantity.
        /// </summary>
        /// <param name="location">The location for which the stock is defined</param>
        /// <param name="eventTime">Time of export from ERP</param>
        /// <param name="quantityList">Complete stock summary for this location at this eventTime</param>
        /// <param name="externRef">Optional, external reference that can be used to identify this ERP stock count</param>
        /// <returns>Stock ID of the saved ERP stock</returns>
        public string StockCapture(string location, DateTime eventTime, List<GtinQuantity> quantityList, String externRef)
        {
            CaptureRequest request = new CaptureRequest();
            request.Location = location;
            request.EventTime = eventTime;
            request.QuantityList = quantityList;
            request.ExternRef = externRef;
            CaptureResponse response = apiCaller.Post<CaptureResponse>("/erp/v1/stock.capture", request);
            return response.Id;
        }

        /// <summary>
        /// Retrieve ERP stock using stock ID. The stock is described in GTIN + quantity.
        /// </summary>
        /// <param name="stockId">ID of the ERP stock</param>
        /// <returns>ERP stock</returns>
        public Stock StockRetrieve(string stockId)
        {
            NameValueCollection parameters = new NameValueCollection();
            parameters.Add("id", stockId);
            return apiCaller.Get<Stock>("/erp/v1/stock.retrieve", parameters);
        }

        /// <summary>
        /// Retrieves stock summary for given stock ID
        /// </summary>
        /// <param name="stockId">ID of the ERP stock</param>
        /// <returns>Stock summary</returns>
        public StockSummary StockStatus(string stockId)
        {
            NameValueCollection parameters = new NameValueCollection();
            parameters.Add("id", stockId);
            return apiCaller.Get<StockSummary>("/erp/v1/stock.status", parameters);
        }

        /// <summary>
        /// Retrieves ERP stock summary list for given location
        /// </summary>
        /// <param name="location">The location for which the stock is defined</param>
        /// <param name="fromEventTime">Optional. Defines date+time since when the cycle counts are required. When null: returns summaries for all stocks for specified location since the epoch.</param>
        /// <param name="untilEventTime">Optional. Defines date+time until when the cycle counts are required. When null: returns summaries for all stocks for specified location up until now.</param>
        /// <returns>List of Stock summary</returns>
        public List<StockSummary> StockList(string location, DateTime? fromEventTime, DateTime? untilEventTime)
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
            return apiCaller.Get<List<StockSummary>>("/erp/v1/stock.list", parameters);
        }
    }
}