using Newtonsoft.Json;
using System;

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
        public String SourceDestTypeId { get; set; }

        /// <summary>
        /// An identifier that denotes a specific source or destination. User Vocabulary.
        /// </summary>
        [JsonProperty("destination")]
        public String SourceDestId { get; set; }

        /// <summary>
        /// Equals method for comparison of destination identifiers
        /// </summary>
        /// <param name="obj">Object to compare to</param>
        /// <returns>True if destination identifiers match</returns>
        public override bool Equals(object obj)
        {
            // If parameter is null return false.
            if (obj == null)
            {
                return false;
            }

            // If parameter cannot be cast to Destination return false.
            Destination p = obj as Destination;
            if ((global::System.Object)p == null)
            {
                return false;
            }

            // Return true if the fields match:
            return Equals(p);
        }

        /// <summary>
        /// Equals method for comparison of destination identifiers
        /// </summary>
        /// <param name="p">Destination identifier to compare to</param>
        /// <returns>True if destination identifiers match</returns>
        public bool Equals(Destination p)
        {
            // If parameter is null return false:
            if ((object)p == null)
            {
                return false;
            }

            // Return true if the fields match:
            return SourceDestTypeId.Equals(p.SourceDestTypeId) && SourceDestId.Equals(p.SourceDestId);
        }

        /// <summary>
        /// Override default Hashcode method
        /// </summary>
        /// <returns>Hashcode</returns>
        public override int GetHashCode()
        {
            string hashBase = "";
            if (SourceDestTypeId != null)
            {
                hashBase += SourceDestTypeId;
            }
            if (SourceDestId != null)
            {
                hashBase += SourceDestId;
            }
            return hashBase.GetHashCode();
        }
    }
}