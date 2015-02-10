using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    /// <summary>
    /// The event type AggregationEvent describes events that apply to objects that have been aggregated to one another. In such an event, there is a set of contained objects that have been aggregated within a containing entity that's meant to identify the aggregation itself.
    /// This event type is intended to be used for aggregations, meaning an association where there is a strong physical relationship between the containing and the contained objects such that they will all occupy the same location at the same time, until such time as they are disaggregated. An example of an aggregation is where cases are loaded onto a pallet and carried as a unit. The AggregationEvent type is not intended for weaker associations such as two pallets that are part of the same shipment, but where the pallets might not always be in exactly the same place at the same time.
    /// </summary>
    public class AggregationEvent : EpcisEvent
    {
        /// <summary>
        /// Constructor that sets type to object_event
        /// </summary>
        public AggregationEvent()
        {
            base.Type = "aggregation_event";
        }

        /// <summary>
        /// The identifier of the parent of the association. If the parentID is an EPC, the EPC's URI is required. This field is required, unless the action is OBSERVE.
        /// </summary>
        [JsonProperty("parent_id")]
        public string ParentId { get; set; }

        /// <summary>
        /// An unordered list of one or more EPCs naming specific objects to which the event pertained.
        /// </summary>
        [JsonProperty("child_epcs")]
        public string ChildEpcs { get; set; }

        /// <summary>
        /// An unordered list of one or more QuantityElements identifying (at the class level) objects to which the event pertained.
        /// </summary>
        [JsonProperty("child_quantity_list")]
        public string ChildQuantityList { get; set; }

        /// <summary>
        /// How this event relates to the lifecycle of the EPCs named in this event.
        /// </summary>
        [JsonProperty("action")]
        [JsonConverter(typeof(StringEnumConverter))]
        public Action Action { get; set; }

        /// <summary>
        /// Optional. The business step of which this event was a part.
        /// </summary>
        [JsonProperty("biz_step")]
        public string BizStep { get; set; }

        /// <summary>
        /// The business condition of the objects associated with the EPCs, presumed to hold true until contradicted by a subsequent event.
        /// </summary>
        [JsonProperty("disposition")]
        public string Disposition { get; set; }

        /// <summary>
        /// The read point at which the event took place.
        /// </summary>
        [JsonProperty("read_point")]
        public string ReadPoint { get; set; }

        /// <summary>
        /// The business location where the objects associated with the EPCs may be found, until contradicted by a subsequent event.
        /// </summary>
        [JsonProperty("biz_location")]
        public string BizLocation { get; set; }

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
        public string DestinationList { get; set; }
    }
}