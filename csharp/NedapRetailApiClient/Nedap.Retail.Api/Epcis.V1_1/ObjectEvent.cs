using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    /// <summary>
    /// An ObjectEvent captures information about an event pertaining to one or more physical or digital objects identified by instance-level (EPC) or class-level (EPC Class) identifiers. Most ObjectEvents are envisioned to represent actual observations of objects, but strictly speaking it can be used for any event a Capturing Application wants to assert about objects, including for example capturing the fact that an expected observation failed to occur.
    /// While more than one EPC may appear in an ObjectEvent, no relationship or association between those EPCs is implied other than the coincidence of having experienced identical events in the real world.The Action parameter of an ObjectEvent describes the event's relationship to the lifecycle of the objects and their identifiers named in the event.
    /// </summary>
    public class ObjectEvent : EpcisEvent
    {
        /// <summary>
        /// Constructor that sets type to object_event
        /// </summary>
        public ObjectEvent()
        {
            base.Type = "object_event";
        }

        /// <summary>
        /// How this event relates to the lifecycle of the EPCs named in this event.
        /// </summary>
        [JsonProperty("action")]
        [JsonConverter(typeof(StringEnumConverter))]
        public Action Action { get; set; }

        /// <summary>
        /// The business condition of the objects associated with the EPCs, presumed to hold true until contradicted by a subsequent event.
        /// </summary>
        [JsonProperty("disposition")]
        public string Disposition { get; set; }

        /// <summary>
        /// The business location where the objects associated with the EPCs may be found, until contradicted by a subsequent event.
        /// </summary>
        [JsonProperty("biz_location")]
        public string BizLocation { get; set; }

        /// <summary>
        /// An unordered list of one or more EPCs naming specific objects to which the event pertained. An ObjectEvent must contain either an epc_list or a quantity_list.
        /// </summary>
        [JsonProperty("epc_list")]
        public List<string> EpcList { get; set; }

        /// <summary>
        /// An unordered list of one or more QuantityElements identifying (at the class level) objects to which the event pertained. An ObjectEvent must contain either an epc_list or a quantity_list.
        /// </summary>
        [JsonProperty("quantity_list")]
        public List<QuantityElement> QuantityList { get; set; }

        /// <summary>
        /// Optional. The business step of which this event was a part.
        /// </summary>
        [JsonProperty("biz_step")]
        public string BizStep { get; set; }

        /// <summary>
        /// The read point at which the event took place.
        /// </summary>
        [JsonProperty("read_point")]
        public string ReadPoint { get; set; }

        /// <summary>
        /// Optional. An unordered list of business transactions that define the context of this event.
        /// </summary>
        [JsonProperty("biz_transaction_list")]
        public List<BusinessTransaction> BizTransactionList { get; set; }

        /// <summary>
        /// Optional. An unordered list of Source elements that provide context about the originating endpoint of a business transfer of which this event is a part.
        /// </summary>
        [JsonProperty("source_list")]
        public List<Source> SourceList { get; set; }

        /// <summary>
        /// Optional. An unordered list of Destination elements that provide context about the terminating endpoint of a business transfer of which this event is a part.
        /// </summary>
        [JsonProperty("destination_list")]
        public List<Destination> DestinationList { get; set; }

        /// <summary>
        /// Optional. Instance/Lot Master Data that describes the objects created during this event.
        /// </summary>
        [JsonProperty("ilmd")]
        public string Ilmd { get; set; }
    }
}