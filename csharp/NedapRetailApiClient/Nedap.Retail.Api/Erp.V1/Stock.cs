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
        public string Id { get; private set; }

        /// <summary>
        /// Location the summary is about
        /// </summary>
        [JsonProperty("location")]
        public string Location { get; private set; }

        /// <summary>
        /// The date + time moment of export from the ERP
        /// </summary>
        [JsonProperty("event_time")]
        public DateTime EventTime { get; private set; }

        /// <summary>
        /// Optional, external reference that can be used to identify this ERP stock count
        /// </summary>
        [JsonProperty("extern_ref")]
        public string ExternRef { get; private set; }

        /// <summary>
        /// Complete stock summary for this Location at this EventTime
        /// </summary>
        [JsonProperty("quantity_list")]
        public List<GtinQuantity> QuantityList { get; private set; }
    }
}