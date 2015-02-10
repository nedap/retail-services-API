using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Epc.V2
{
    /// <summary>
    /// Difference between ERP system stock and RFID stock
    /// </summary>
    public class DifferenceListResponse : JsonPrintableObject
    {
        internal DifferenceListResponse()
        {

        }

        /// <summary>
        /// The GTINs in the difference list.
        /// </summary>
        [JsonProperty("gtins")]
        public List<string> Gtins { get; private set; }

        /// <summary>
        /// ERP stock as defined in the request. Values are signed.
        /// </summary>
        [JsonProperty("erp_stock")]
        public List<int> ErpStock { get; private set; }

        /// <summary>
        /// RFID stock as defined in the request. Values are unsigned.
        /// </summary>
        [JsonProperty("rfid_stock")]
        public List<int> RfidStock { get; private set; }

        /// <summary>
        /// Article data (only if include_articles is true).
        /// If no article data for a GTIN is known, a null object is included in the array for that GTIN.
        /// </summary>
        [JsonProperty("articles")]
        public List<Article.V2.Article> Articles { get; private set; }

        /// <summary>
        /// Time of ERP stock used to generate the difference list.
        /// </summary>
        [JsonProperty("erp_stock_time")]
        public DateTime ErpStockTime { get; private set; }

        /// <summary>
        /// Time of RFID stock used to generate the difference list.
        /// </summary>
        [JsonProperty("rfid_stock_time")]
        public DateTime RfidStockTime { get; private set; }

        /// <summary>
        /// Number of GTINs in ERP stock.
        /// </summary>
        [JsonProperty("erp_gtin_quantity")]
        public int ErpGtinQuantity { get; private set; }

        /// <summary>
        /// Number of GTINs in RFID stock.
        /// </summary>
        [JsonProperty("rfid_gtin_quantity")]
        public int RfidGtinQuantity { get; private set; }
    }
}