using Newtonsoft.Json;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    /// <summary>
    /// Source identifier
    /// </summary>
    public class Source : JsonPrintableObject
    {
        /// <summary>
        /// An identifier that indicates what kind of source or destination this Source denotes.
        /// Standard Vocabulary, however, EPCIS does not specify Master Data Attributes.
        /// </summary>
        [JsonProperty("type")]
        public string SourceDestTypeId { get; set; }

        /// <summary>
        /// An identifier that denotes a specific source or destination. User Vocabulary.
        /// </summary>
        [JsonProperty("source")]
        public string SourceDestId { get; set; }
    }
}