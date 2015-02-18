using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Article.V2
{
    /// <summary>
    /// A description of an article that is visibly the same in terms of name, size, color, texture.
    /// GTIN is the leading identifier for each article description.
    /// </summary>
    public class Article : JsonPrintableObject
    {
        /// <summary>
        /// Optional. GTIN-14, length of 14 digits. Primary key to identify an article.
        /// GTIN-14 value is validated against length and can contain only digits.
        /// When no GTIN-14 is provided, it will be generated. It will be unique for this organization.
        /// </summary>
        [JsonProperty("gtin")]
        public string Gtin { get; set; }

        /// <summary>
        /// Contains all barcodes for this article, typically including EAN-13.
        /// Minimum of 1 linear barcode is required.
        /// </summary>
        [JsonProperty("barcodes")]
        public List<Barcode> Barcodes { get; set; }

        /// <summary>
        /// Optional. Article code as assigned by the retailer. No relation to GTIN or other barcodes is assumed.
        /// </summary>
        [JsonProperty("code")]
        public string Code { get; set; }

        /// <summary>
        /// Optional. The brand of the product, e.g. Nike or Adidas.
        /// </summary>
        [JsonProperty("brand")]
        public string Brand { get; set; }

        /// <summary>
        /// Optional. The season the product belongs to.
        /// Examples: 'Summer 2013' or 'Autumn 2014'.
        /// </summary>
        [JsonProperty("season")]
        public string Season { get; set; }

        /// <summary>
        /// Name of the article.
        /// </summary>
        [JsonProperty("name")]
        public string Name { get; set; }

        /// <summary>
        /// Identifies article in color.
        /// When omitted: option is automatically filled with name and color fields.
        /// </summary>
        [JsonProperty("option")]
        public string Option { get; set; }

        /// <summary>
        /// Identifies article, regardless of color.
        /// When omitted: style is automatically filled with name.
        /// </summary>
        [JsonProperty("style")]
        public string Style { get; set; }

        /// <summary>
        /// Contains color description.
        /// </summary>
        [JsonProperty("color")]
        public string Color { get; set; }

        /// <summary>
        /// Contains region dependent size descriptions.
        /// </summary>
        [JsonProperty("sizes")]
        public List<Size> Sizes { get; set; }

        /// <summary>
        /// Optional. Supplier name. Can also be factory name.
        /// </summary>
        [JsonProperty("supplier")]
        public string Supplier { get; set; }

        /// <summary>
        /// Optional. Can contain various prices that are country or region specific.
        /// </summary>
        [JsonProperty("prices")]
        public List<Price> Prices { get; set; }

        /// <summary>
        /// The date + time moment when article was last updated.
        /// </summary>
        [JsonProperty("last_updated")]
        public DateTime LastUpdated { get; set; }
    }
}