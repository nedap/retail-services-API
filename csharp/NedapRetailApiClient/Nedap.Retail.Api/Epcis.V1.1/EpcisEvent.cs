using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Nedap.Retail.Api.Epcis.V1._1
{
    /// <summary>
    /// Abstract, common base type for all EPCIS events.
    /// </summary>
    public class EpcisEvent : JsonPrintableObject
    {
        /// <summary>
        /// Identifier, unique per organization, that identifies the EPCIS event.
        /// </summary>
        [JsonProperty("id")]
        public Guid Id { get; set; }

        /// <summary>
        /// Epcis event type
        /// </summary>
        [JsonProperty("type")]
        public string Type { get; protected set; }

        /// <summary>
        /// The date and time at which the EPCIS Capturing Applications asserts the event occurred
        /// </summary>
        [JsonProperty("event_time")]
        public DateTime EventTime { get; set; }

        /// <summary>
        /// The date and time at which this event was recorded by an EPCIS Repository.
        /// The record_time is an optional parameter in the API that will be added by the server. If a client adds record_time, it will be overridden by the server.
        /// </summary>
        [JsonProperty("record_time")]
        public DateTime RecordTime { get; set; }

        /// <summary>
        /// The time zone offset in effect at the time and place the event occurred, expressed as an offset from UTC.
        /// Values range from "-14:59" to "+14:59"
        /// </summary>
        [JsonProperty("event_time_zone_offset")]
        public string EventTimeZoneOffset { get; set; }
    }
}