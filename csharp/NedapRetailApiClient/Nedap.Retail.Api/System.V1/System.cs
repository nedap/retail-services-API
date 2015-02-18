using Newtonsoft.Json;
using System;

namespace Nedap.Retail.Api.System.V1
{
    /// <summary>
    /// A system, installed at a certain location
    /// </summary>
    public class System : JsonPrintableObject
    {
        /// <summary>
        /// System ID that uniquely identifies the system
        /// </summary>
        [JsonProperty("system_id")]
        public String SystemId { get; private set; }

        /// <summary>
        /// Human readable name of the system
        /// </summary>
        [JsonProperty("name")]
        public String Name { get; private set; }

        /// <summary>
        /// Description of the location of the system
        /// </summary>
        [JsonProperty("location")]
        public String Location { get; private set; }
    }
}