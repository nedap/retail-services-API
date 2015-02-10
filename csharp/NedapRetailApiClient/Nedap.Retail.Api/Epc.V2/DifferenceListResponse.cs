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
        /// <summary>
        /// The GTINs in the difference list.
        /// </summary>
        [JsonProperty("gtins")]
        public List<string> Gtins { get; set; }

        /// <summary>
        /// ERP stock as defined in the request. Values are signed.
        /// </summary>
        [JsonProperty("erp_stock")]
        public List<int> ErpStock { get; set; }

        /// <summary>
        /// RFID stock as defined in the request. Values are unsigned.
        /// </summary>
        [JsonProperty("rfid_stock")]
        public List<int> RfidStock { get; set; }

        /// <summary>
        /// Article data (only if include_articles is true).
        /// If no article data for a GTIN is known, a null object is included in the array for that GTIN.
        /// </summary>
        [JsonProperty("articles")]
        public List<Article.V2.Article> Articles { get; set; }

        /// <summary>
        /// Time of ERP stock used to generate the difference list.
        /// </summary>
        [JsonProperty("erp_stock_time")]
        public DateTime ErpStockTime { get; set; }

        /// <summary>
        /// Time of RFID stock used to generate the difference list.
        /// </summary>
        [JsonProperty("rfid_stock_time")]
        public DateTime RfidStockTime { get; set; }

        /// <summary>
        /// Number of GTINs in ERP stock.
        /// </summary>
        [JsonProperty("erp_gtin_quantity")]
        public int ErpGtinQuantity { get; set; }

        /// <summary>
        /// Number of GTINs in RFID stock.
        /// </summary>
        [JsonProperty("rfid_gtin_quantity")]
        public int RfidGtinQuantity { get; set; }
    }
}