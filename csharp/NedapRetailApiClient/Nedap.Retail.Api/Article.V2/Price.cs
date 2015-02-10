using Newtonsoft.Json;

namespace Nedap.Retail.Api.Article.V2
{
    /// <summary>
    /// Describes a price for a region. These prices are numeric with typically double precision.
    /// </summary>
    public class Price : JsonPrintableObject
    {
        /// <summary>
        /// The Currency for the specified region.
        /// Currency noted according to the ISO 4217:2008 standard.
        /// Example XEU for Euro.
        /// </summary>
        [JsonProperty("currency")]
        public string Currency { get; set; }

        /// <summary>
        /// The Country defining the region where this size description is used.
        /// Country noted according to the ISO 3166:2013 standard.
        /// Example NL for Netherlands.
        /// </summary>
        [JsonProperty("region")]
        public string Region { get; set; }

        /// <summary>
        /// Amount of monetary value.
        /// </summary>
        [JsonProperty("amount")]
        public double Amount { get; set; }
    }
}