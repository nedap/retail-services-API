using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System;

namespace Nedap.Retail.Api.Erp.V1
{
    /// <summary>
    /// Summary of ERP stock
    /// </summary>
    public class StockSummary
    {
        /// <summary>
        /// Identifier of this stock count
        /// </summary>
        [JsonProperty("id")]
        public String Id { get; set; }

        /// <summary>
        /// Location the summary is about
        /// </summary>
        [JsonProperty("location")]
        public String Location { get; set; }

        /// <summary>
        /// The date + time moment of export from the ERP
        /// </summary>
        [JsonProperty("event_time")]
        public DateTime EventTime { get; set; }

        /// <summary>
        /// Optional, external reference that can be used to identify this ERP stock count
        /// </summary>
        [JsonProperty("extern_ref")]
        public String ExternRef { get; set; }

        /// <summary>
        /// Status of the ERP stock
        /// </summary>
        [JsonProperty("status")]
        [JsonConverter(typeof(StringEnumConverter))]
        public Status Status { get; set; }

        /// <summary>
        /// Returns string representation of the object
        /// </summary>
        /// <returns>Formatted JSON string</returns>
        public override string ToString()
        {
            return JsonConvert.SerializeObject(this, Formatting.Indented);
        }
    }

    /// <summary>
    /// Possible statuses of ERP stock
    /// </summary>
    public enum Status
    {
        /// <summary>
        /// Stock is still being validated on the server
        /// </summary>
        VALIDATING,
        /// <summary>
        /// Stock has been accepted by the server
        /// </summary>
        ACCEPTED
    }
}
