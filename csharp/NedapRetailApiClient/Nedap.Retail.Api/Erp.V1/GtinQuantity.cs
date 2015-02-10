using Newtonsoft.Json;

namespace Nedap.Retail.Api.Erp.V1
{
    /// <summary>
    /// Set of a GTIN and a quantity
    /// </summary>
    public class GtinQuantity : JsonPrintableObject
    {
        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="gtin"></param>
        /// <param name="quantity"></param>
        public GtinQuantity(string gtin, long quantity)
        {
            gtin.RequiredString(() => gtin);
            Gtin = gtin;
            Quantity = quantity;
        }

        /// <summary>
        /// GTIN
        /// </summary>
        [JsonProperty("gtin")]
        public string Gtin { get; set; }

        /// <summary>
        /// Quantity
        /// </summary>
        [JsonProperty("quantity")]
        public long Quantity { get; set; }
    }
}