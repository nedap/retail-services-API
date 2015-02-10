using Newtonsoft.Json;

namespace Nedap.Retail.Api.Article.V2
{
    /// <summary>
    /// Describes a barcode. These barcodes do not have to be numeric-only and/or that cannot be automatically converted to GTIN, for example, Code-39 (alpha numeric), Code-128 (alpha numeric), GS1-128 (numeric only).
    /// </summary>
    public class Barcode : JsonPrintableObject
    {
        /// <summary>
        /// Barcode type. Typically EAN-13. Values are described in lowercase, digits and underscores.
        /// Examples: ean_8, ean_13, upc_12, gs1_128, code_39, code_128, qr_code
        /// </summary>
        [JsonProperty("type")]
        public string Type { get; set; }

        /// <summary>
        /// Value of barcode. Barcode validity conform defined type is not ensured.
        /// </summary>
        [JsonProperty("value")]
        public string Value { get; set; }
    }
}