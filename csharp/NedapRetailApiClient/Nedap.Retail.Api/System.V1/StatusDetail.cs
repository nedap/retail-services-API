using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

namespace Nedap.Retail.Api.System.V1
{
    /// <summary>
    /// Contains status of a detailed status type
    /// </summary>
    public class StatusDetail : JsonPrintableObject
    {
        /// <summary>
        /// Status type
        /// </summary>
        [JsonProperty("type")]
        public StatusType Type { get; private set; }

        /// <summary>
        /// The status
        /// </summary>
        [JsonProperty("status")]
        [JsonConverter(typeof(StringEnumConverter))]
        public Status Status { get; private set; }
    }
}