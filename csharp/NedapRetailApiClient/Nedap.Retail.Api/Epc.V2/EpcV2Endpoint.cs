using Nedap.Retail.Api.OAuth;
using System;
using System.Collections.Generic;
using System.Collections.Specialized;

namespace Nedap.Retail.Api.Epc.V2
{
    /// <summary>
    /// EPC V2 API endpoint
    /// </summary>
    public class EpcV2Endpoint
    {
        private ApiCaller apiCaller;

        internal EpcV2Endpoint(ApiCaller apiCaller)
        {
            this.apiCaller = apiCaller;
        }

        /// <summary>
        /// Retrieve GTIN-based difference list for a single location. By default, only differences are returned and items where there is no difference are omitted.
        /// When a difference list for multiple locations are required, this call should be used repeatedly.
        /// How it works:
        /// Get ERP stock defined by erp_stock_id.
        /// Get RFID count at time.
        /// </summary>
        /// <param name="erpStockId">ERP stock ID. A list of ERP stock ID's can be retrieved with the ERP API. Implicitly defines LocationIdentifier for the location for which the difference list is generated.</param>
        /// <param name="time">The date and time you would like to have the RFID stock information from. When omitted: the report will contain the stock information at the current server time.</param>
        /// <param name="onlyDifferences">Optional. When set to TRUE (default) this will return only differences. When set to FALSE, this will return all entries in ERP stock or RFID stock. When omitted: default value is TRUE, returning only differences.</param>
        /// <param name="includeArticles">Optional. When set to TRUE this will return an array of articles. When omitted: default value is FALSE.</param>
        /// <returns>DifferenceList response object</returns>
        public DifferenceListResponse DifferenceList(string erpStockId, Nullable<DateTime> time, Nullable<bool> onlyDifferences, Nullable<bool> includeArticles)
        {
            erpStockId.RequiredString(() => erpStockId);

            NameValueCollection parameters = new NameValueCollection();
            parameters.Add("erp_stock_id", erpStockId);
            if (time.HasValue) parameters.Add("time", time.Value.ToString());
            if (onlyDifferences.HasValue) parameters.Add("only_differences", onlyDifferences.Value.ToString());
            if (includeArticles.HasValue) parameters.Add("include_articles", includeArticles.Value.ToString());
            return apiCaller.Get<DifferenceListResponse>("/epc/v2/difference_list", parameters);
        }

        /// <summary>
        /// Requests the current stock status at a certain location at the GTIN level.
        /// This can be used to evaluate the total stock in a store along with stock summary information for that store, or calculate options for in-store replenishment by querying the stock in any of sublocations.
        /// If site stock is requested stock summary for store is included in response.
        /// On !D Cloud we apply logic that filters out EPCs based on their location as defined by the EPC's bizLocation and the status at that location as defined by the EPC's disposition.
        /// </summary>
        /// <param name="location">Contains the location that we are interested in. Based on GLN + extension number; see also EPCIS master data.</param>
        /// <param name="gtins">Optional. Filter by GTIN. When omitted: returns all available GTINs, so no filtering based on GTIN is applied.</param>
        /// <param name="dispositions">Optional. Contains a list of dispositions that we consider in stock. All GTINs that match any of the the given dispositions are returned. When omitted: the report will be based on the dispositions urn:epcglobal:cbv:disp:sellable_accessible, urn:epcglobal:cbv:disp:sellable_not_accessible, urn:epcglobal:cbv:disp:non_sellable_other, http://nedapretail.com/disp/maybe_stolen.</param>
        /// <param name="time">Optional. The date and time you would like to have the stock information from. When omitted: the report will contain the latest available stock information.</param>
        /// <param name="includeArticles">Optional. When set to TRUE this will return an array of articles. When omitted: default value is FALSE.</param>
        /// <returns>StockGtin response object</returns>
        public StockGtinResponse StockGtin(string location, List<string> gtins, List<string> dispositions, Nullable<DateTime> time, Nullable<bool> includeArticles)
        {
            location.RequiredString(() => location);

            NameValueCollection parameters = new NameValueCollection();
            parameters.Add("location", location);
            if (gtins != null) gtins.ForEach(g => parameters.Add("gtins[]", g));
            if (dispositions != null) dispositions.ForEach(g => parameters.Add("dispositions[]", g));
            if (time.HasValue) parameters.Add("time", time.Value.ToString());
            if (includeArticles.HasValue) parameters.Add("include_articles", includeArticles.Value.ToString());
            return apiCaller.Get<StockGtinResponse>("/epc/v3/stock.gtin14", parameters);
        }
    }
}