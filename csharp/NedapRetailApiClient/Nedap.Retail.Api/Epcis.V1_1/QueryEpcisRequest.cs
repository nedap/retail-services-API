using Newtonsoft.Json;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    internal class QueryEpcisRequest : JsonPrintableObject
    {
        [JsonProperty("parameters")]
        public List<ParameterObject> Parameters { get; set; }
    }
}