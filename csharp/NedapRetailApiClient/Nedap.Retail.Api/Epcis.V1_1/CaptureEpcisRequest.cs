using Newtonsoft.Json;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    internal class CaptureEpcisRequest : JsonPrintableObject
    {
        [JsonProperty("events")]
        public List<EpcisEvent> Events { get; set; }
    }
}