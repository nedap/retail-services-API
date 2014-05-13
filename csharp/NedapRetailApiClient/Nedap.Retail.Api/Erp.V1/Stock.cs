using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Erp.V1
{
    /// <summary>
    /// Stock information from ERP
    /// </summary>
    public class Stock
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
        /// Complete stock summary for this Location at this EventTime
        /// </summary>
        [JsonProperty("quantity_list")]
        public List<GtinQuantity> QuantityList { get; set; }

        /// <summary>
        /// Returns string representation of the object
        /// </summary>
        /// <returns>Formatted JSON string</returns>
        public override string ToString() {
            return JsonConvert.SerializeObject(this, Formatting.Indented);
        }
    }
}
