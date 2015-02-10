using Newtonsoft.Json;
using Newtonsoft.Json.Converters;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    /// <summary>
    /// The event type TransactionEvent describes the association or disassociation of physical or digital objects to one or more business transactions.
    /// While other event types have an optional biz_transaction_list parameter that may be used to provide context for an event, the TransactionEvent is used to declare in an unequivocal way that certain objects have been associated or disassociated with one or more business transactions as part of the event.
    /// </summary>
    public class TransactionEvent : EpcisEvent
    {
        /// <summary>
        /// Constructor that sets type to object_event
        /// </summary>
        public TransactionEvent()
        {
            base.Type = "transaction_event";
        }

        /// <summary>
        /// An unordered list of business transactions that define the context of this event.
        /// </summary>
        [JsonProperty("biz_transaction_list")]
        public List<BusinessTransaction> BizTransactionList { get; set; }

        /// <summary>
        /// Optional. The identifier of the parent of the objects given in epcList and quantityList.
        /// </summary>
        [JsonProperty("parent_id")]
        public string ParentId { get; set; }

        /// <summary>
        /// An unordered list of one or more EPCs naming specific objects to which the event pertained. A TransactionEvent must contain either an epc_list or a quantity_list.
        /// </summary>
        [JsonProperty("epc_list")]
        public List<string> EpcList { get; set; }

        /// <summary>
        /// An unordered list of one or more QuantityElements identifying (at the class level) objects to which the event pertained. A TransactionEvent must contain either an epc_list or a quantity_list .
        /// </summary>
        [JsonProperty("quantity_list")]
        public string QuantityList { get; set; }

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
        /// Optional. An unordered list of Source elements that provide context about the originating endpoint of a business transfer of which this event is a part.
        /// </summary>
        [JsonProperty("source_list")]
        public List<Source> SourceList { get; set; }

        /// <summary>
        /// Optional. An unordered list of Destination elements that provide context about the terminating endpoint of a business transfer of which this event is a part.
        /// </summary>
        [JsonProperty("destination_list")]
        public List<Destination> DestinationList { get; set; }
    }
}