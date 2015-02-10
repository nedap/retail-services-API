using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Epc.V2
{
    /// <summary>
    /// GTINs at the specified Business Location
    /// </summary>
    public class StockGtinResponse : JsonPrintableObject
    {
        /// <summary>
        /// The GTINs in the difference list.
        /// </summary>
        [JsonProperty("gtins")]
        public List<string> Gtins { get; set; }

        /// <summary>
        /// Locations of difference list
        /// </summary>
        [JsonProperty("locations")]
        public List<string> Locations { get; set; }

        /// <summary>
        /// Quantities of difference list
        /// </summary>
        [JsonProperty("quantities")]
        public List<int> Quantities { get; set; }

        /// <summary>
        /// Time of stock information
        /// </summary>
        [JsonProperty("time")]
        public DateTime Time { get; set; }

        /// <summary>
        /// Article data (only if include_articles is true).
        /// If no article data for a GTIN is known, a null object is included in the array for that GTIN.
        /// </summary>
        [JsonProperty("articles")]
        public List<Article.V2.Article> Articles { get; set; }

        /// <summary>
        /// The time of RFID stock used to generate the summary.
        /// </summary>
        [JsonProperty("rfid_stock_time")]
        public DateTime RfidStockTime { get; set; }

        /// <summary>
        /// The number of items in all stock rooms.
        /// </summary>
        [JsonProperty("stock_rooms_quantity")]
        public long StockRoomsQuantity { get; set; }

        /// <summary>
        /// The number of items in all sales floors.
        /// </summary>
        [JsonProperty("sales_floors_quantity")]
        public long SalesFloorsQuantity { get; set; }

        /// <summary>
        /// The number of items in the entire store.
        /// </summary>
        [JsonProperty("store_quantity")]
        public long StoreQuantity { get; set; }

        /// <summary>
        /// Percentage of items in the entire store that are in stock rooms.
        /// </summary>
        [JsonProperty("stock_ratio")]
        public double StockRatio { get; set; }
    }
}