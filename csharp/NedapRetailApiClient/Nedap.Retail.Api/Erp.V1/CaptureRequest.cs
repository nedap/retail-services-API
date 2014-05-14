using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Erp.V1
{
    internal class CaptureRequest
    {
        [JsonProperty("location")]
        public String Location { get; set; }

        [JsonProperty("event_time")]
        public DateTime EventTime { get; set; }

        [JsonProperty("extern_ref")]
        public String ExternRef { get; set; }

        [JsonProperty("quantity_list")]
        public List<GtinQuantity> QuantityList { get; set; }

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
