using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Erp.V1
{
    /// <summary>
    /// Stock information from ERP
    /// </summary>
    public class Stock : JsonPrintableObject
    {
        /// <summary>
        /// Identifier of this stock count
        /// </summary>
        [JsonProperty("id")]
        public string Id { get; set; }

        /// <summary>
        /// Location the summary is about
        /// </summary>
        [JsonProperty("location")]
        public string Location { get; set; }

        /// <summary>
        /// The date + time moment of export from the ERP
        /// </summary>
        [JsonProperty("event_time")]
        public DateTime EventTime { get; set; }

        /// <summary>
        /// Optional, external reference that can be used to identify this ERP stock count
        /// </summary>
        [JsonProperty("extern_ref")]
        public string ExternRef { get; set; }

        /// <summary>
        /// Complete stock summary for this Location at this EventTime
        /// </summary>
        [JsonProperty("quantity_list")]
        public List<GtinQuantity> QuantityList { get; set; }
    }
}