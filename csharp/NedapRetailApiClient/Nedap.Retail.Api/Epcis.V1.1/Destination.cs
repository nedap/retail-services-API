using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Nedap.Retail.Api.Epcis.V1._1
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