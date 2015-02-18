using Newtonsoft.Json;

namespace Nedap.Retail.Api.Erp.V1
{
    internal class CaptureResponse : JsonPrintableObject
    {
        [JsonProperty("id")]
        public string Id { get; private set; }
    }
}