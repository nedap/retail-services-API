using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Nedap.Retail.Api.Epcis.V1._1
{
    /// <summary>
    /// A TransformationEvent captures information about an event in which one or more physical or digital objects identified by instance-level (EPC) or class-level (EPC Class) identifiers are consumed as inputs and one or more objects identified by instance-level (EPC) or class-level (EPC Class) identifiers are produced as outputs. The TransformationEvent captures the relationship between the inputs and the outputs, such that any of the inputs may have contributed in some way to each of the outputs.
    /// A TransformationEvent marks the end of the lifecycle for the input objects, and so is similar in effect to an ObjectEvent with action DELETE for the inputs.Likewise, a TransformationEvent marks the beginning of the lifecycle for the output objects, and so is similar in effect to an ObjectEvent with action ADD for the outputs.
    /// </summary>
    public class TransformationEvent : EpcisEvent
    {
        /// <summary>
        /// Constructor that sets type to object_event
        /// </summary>
        public TransformationEvent()
        {
            base.Type = "transformation_event";
        }

        /// <summary>
        /// Optional (see below). An unordered list of one or more EPCs naming specific objects to which the event pertained.
        /// </summary>
        [JsonProperty("input_epc_list")]
        public List<string> InputEpcList { get; set; }

        /// <summary>
        /// Optional (see below). An unordered list of one or more QuantityElements identifying (at the class level) objects to which the event pertained.
        /// </summary>
        [JsonProperty("input_quantity_list")]
        public List<QuantityElement> InputQuantityList { get; set; }

        /// <summary>
        /// Optional (see below). An unordered list of one or more EPCs naming specific objects to which the event pertained.
        /// </summary>
        [JsonProperty("output_epc_list")]
        public List<string> OutputEpcList { get; set; }

        /// <summary>
        /// Optional (see below). An unordered list of one or more QuantityElements identifying (at the class level) objects to which the event pertained.
        /// </summary>
        [JsonProperty("ioutput_quantity_listlmd")]
        public List<QuantityElement> OutputQuantityList { get; set; }

        /// <summary>
        /// Optional. A unique identifier that links this event to other TransformationEvents having an identical value of transformationID.
        /// </summary>
        [JsonProperty("transformation_id")]
        public string TransformationId { get; set; }

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
        public List<Destination> DestinationList { get; set; }

        /// <summary>
        /// Optional. Instance/Lot Master Data that describes the objects created during this event.
        /// </summary>
        [JsonProperty("ilmd")]
        public string Ilmd { get; set; }
    }
}