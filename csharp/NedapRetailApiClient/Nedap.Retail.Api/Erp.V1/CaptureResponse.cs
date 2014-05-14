using Newtonsoft.Json;
using System;

namespace Nedap.Retail.Api.Erp.V1
{
    internal class CaptureResponse
    {
        [JsonProperty("id")]
        public String Id { get; set; }

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
