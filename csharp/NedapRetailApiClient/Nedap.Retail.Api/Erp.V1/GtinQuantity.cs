using Newtonsoft.Json;
using System;

namespace Nedap.Retail.Api.Erp.V1
{
    /// <summary>
    /// Set of a GTIN and a quantity
    /// </summary>
    public class GtinQuantity
    {
        /// <summary>
        /// GTIN
        /// </summary>
        [JsonProperty("gtin")]
        public String Gtin { get; set; }

        /// <summary>
        /// Quantity
        /// </summary>
        [JsonProperty("quantity")]
        public long Quantity { get; set; }

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="gtin">GTIN</param>
        /// <param name="quantity">Quantity</param>
        public GtinQuantity(string gtin, long quantity)
        {
            Gtin = gtin;
            Quantity = quantity;
        }

        /// <summary>
        /// Returns string representation of the object
        /// </summary>
        /// <returns>Formatted JSON string</returns>
        public override string ToString()
        {
            return JsonConvert.SerializeObject(this, Formatting.Indented);
        }
    }
}
