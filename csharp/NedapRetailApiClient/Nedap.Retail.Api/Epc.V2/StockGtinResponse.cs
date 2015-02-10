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
        internal StockGtinResponse()
        {

        }

        /// <summary>
        /// The GTINs in the difference list.
        /// </summary>
        [JsonProperty("gtins")]
        public List<string> Gtins { get; private set; }

        /// <summary>
        /// Locations of difference list
        /// </summary>
        [JsonProperty("locations")]
        public List<string> Locations { get; private set; }

        /// <summary>
        /// Quantities of difference list
        /// </summary>
        [JsonProperty("quantities")]
        public List<int> Quantities { get; private set; }

        /// <summary>
        /// Time of stock information
        /// </summary>
        [JsonProperty("time")]
        public DateTime Time { get; private set; }

        /// <summary>
        /// Article data (only if include_articles is true).
        /// If no article data for a GTIN is known, a null object is included in the array for that GTIN.
        /// </summary>
        [JsonProperty("articles")]
        public List<Article.V2.Article> Articles { get; private set; }

        /// <summary>
        /// The time of RFID stock used to generate the summary.
        /// </summary>
        [JsonProperty("rfid_stock_time")]
        public DateTime RfidStockTime { get; private set; }

        /// <summary>
        /// The number of items in all stock rooms.
        /// </summary>
        [JsonProperty("stock_rooms_quantity")]
        public long StockRoomsQuantity { get; private set; }

        /// <summary>
        /// The number of items in all sales floors.
        /// </summary>
        [JsonProperty("sales_floors_quantity")]
        public long SalesFloorsQuantity { get; private set; }

        /// <summary>
        /// The number of items in the entire store.
        /// </summary>
        [JsonProperty("store_quantity")]
        public long StoreQuantity { get; private set; }

        /// <summary>
        /// Percentage of items in the entire store that are in stock rooms.
        /// </summary>
        [JsonProperty("stock_ratio")]
        public double StockRatio { get; private set; }
    }
}