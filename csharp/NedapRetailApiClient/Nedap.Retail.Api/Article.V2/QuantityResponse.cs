using Newtonsoft.Json;

namespace Nedap.Retail.Api.Article.V2
{
    /// <summary>
    /// Article quantity
    /// </summary>
    internal class QuantityResponse : JsonPrintableObject
    {
        /// <summary>
        /// Article quantity
        /// </summary>
        [JsonProperty("quantity")]
        public long Quantity { get; set; }
    }
}