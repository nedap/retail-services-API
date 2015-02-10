using Newtonsoft.Json;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Epcis.V1._1
{
    internal class CaptureEpcisRequest
    {
        [JsonProperty("events")]
        public List<EpcisEvent> Events { get; set; }
    }
}