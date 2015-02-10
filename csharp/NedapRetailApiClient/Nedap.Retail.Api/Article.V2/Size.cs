using Newtonsoft.Json;

namespace Nedap.Retail.Api.Article.V2
{
    /// <summary>
    /// Describes a size for a region. Size can contain both width and length.
    /// </summary>
    public class Size : JsonPrintableObject
    {
        /// <summary>
        /// The Country defining the region where this size description is used.
        /// Country noted according to the ISO 3166:2013 standard.
        /// Example NL for Netherlands.
        /// </summary>
        [JsonProperty("region")]
        public string Region { get; set; }

        /// <summary>
        /// Size description according to region. Can contain both size and length.
        /// </summary>
        [JsonProperty("description")]
        public string Description { get; set; }
    }
}