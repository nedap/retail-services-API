using Newtonsoft.Json;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    /// <summary>
    /// Quantity element
    /// </summary>
    public class QuantityElement : JsonPrintableObject
    {
        /// <summary>
        /// A class-level identifier for the class to which the specified quantity of objects belongs. EPCClass is a User Vocabulary.
        /// </summary>
        [JsonProperty("epc_class")]
        public string EpcClass { get; set; }

        /// <summary>
        /// A number that specifies how many or how much of the specified EPCClass is denoted by this QuantityElement.
        /// - If no uom is present, quantity must be an integer describing count.
        /// - If uom is present, quantity can be integer or float describing length, area, volume, or mass.
        /// Negative values are not allowed.
        /// </summary>
        [JsonProperty("quantity")]
        public float quantity { get; set; }

        /// <summary>
        /// Optional. Specifies a unit of measure by which the specified quantity is to be interpreted as a physical measure, specifying how much of the specified EPCClass is denoted by this QuantityElement.
        /// Is a 2- or 3-character code for a physical unit specified in the Common Code column of UN/CEFACT Recommendation 20.
        /// </summary>
        [JsonProperty("uom")]
        public string Uom { get; set; }
    }
}