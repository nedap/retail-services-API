using Newtonsoft.Json;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    /// <summary>
    /// Destination identifier
    /// </summary>
    public class Destination : JsonPrintableObject
    {
        /// <summary>
        /// An identifier that indicates what kind of source or destination this Destination denotes. Standard Vocabulary, however, EPCIS does not specify Master Data Attributes.
        /// </summary>
        [JsonProperty("type")]
        public string SourceDestTypeId { get; set; }

        /// <summary>
        /// An identifier that denotes a specific source or destination. User Vocabulary.
        /// </summary>
        [JsonProperty("destination")]
        public string SourceDestId { get; set; }
    }
}