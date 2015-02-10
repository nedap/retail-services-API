using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Workflow.V2
{
    /// <summary>
    /// Workflow event
    /// </summary>
    public class WorkflowEvent : JsonPrintableObject
    {
        /// <summary>
        /// Type of the workflow. Valid values:
        /// - cycle_count_started
        /// - cycle_count_finished
        /// - in-store_replenishment_started
        /// </summary>
        [JsonProperty("type")]
        public string Type { get; set; }

        /// <summary>
        /// Client-side date+time of registering that the cycle count was finished. This is not the moment of sending the workflow event nor is it the moment when the last EPC was observed.
        /// </summary>
        [JsonProperty("event_time")]
        public DateTime EventTime { get; set; }

        /// <summary>
        /// The location for which the stock is defined.
        /// </summary>
        [JsonProperty("location")]
        public string Location { get; set; }

        /// <summary>
        /// The total number of EPCs that were counted in the referenced message_ids.
        /// </summary>
        [JsonProperty("epc_count")]
        public int EpcCount { get; set; }

        /// <summary>
        /// Array of EPCIS event message IDs. These events contain the actual EPCs that were observed in this cycle count.
        /// </summary>
        [JsonProperty("message_ids")]
        public List<string> MessageIds { get; set; }
    }
}