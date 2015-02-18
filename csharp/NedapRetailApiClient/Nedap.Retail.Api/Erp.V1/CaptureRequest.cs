using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Erp.V1
{
    internal class CaptureRequest : JsonPrintableObject
    {
        [JsonProperty("location")]
        public string Location { get; set; }

        [JsonProperty("event_time")]
        public DateTime EventTime { get; set; }

        [JsonProperty("extern_ref")]
        public string ExternRef { get; set; }

        [JsonProperty("quantity_list")]
        public List<GtinQuantity> QuantityList { get; set; }
    }
}