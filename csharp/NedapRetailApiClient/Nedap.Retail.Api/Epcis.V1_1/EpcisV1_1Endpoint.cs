using Nedap.Retail.Api.OAuth;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    /// <summary>
    /// EPCIS V1.1 API endpoint
    /// </summary>
    public class EpcisV1_1Endpoint
    {
        private ApiCaller apiCaller;

        internal EpcisV1_1Endpoint(ApiCaller apiCaller)
        {
            this.apiCaller = apiCaller;
        }

        /// <summary>
        /// The Capture Service captures one or more EPCIS events at a time.
        /// This does not imply any relationship between these EPCIS events.
        /// Each element of the argument list is accepted if it is a valid EPCIS event or subtype that conforms to the above EPCIS event types.
        /// </summary>
        /// <param name="events">The EPCIS Events to capture</param>
        /// <returns>Http status code</returns>
        public string Capture(List<EpcisEvent> events)
        {
            events.Required(() => events);

            CaptureEpcisRequest request = new CaptureEpcisRequest();
            request.Events = events;
            return apiCaller.Post<string>("/epcis/v2/capture", request);
        }

        /// <summary>
        /// Returns a set of EPCIS Events that matches the criteria specified in the query parameters.
        /// </summary>
        /// <returns>A list of EPCIS Events</returns>
        public List<EpcisEvent> Query(List<ParameterObject> parameters)
        {
            QueryEpcisRequest request = new QueryEpcisRequest();
            request.Parameters = parameters;
            dynamic dynamicEpcisEvents = apiCaller.Post<dynamic>("/epcis/v2/query", request);

            List<EpcisEvent> Events = new List<EpcisEvent>();
            foreach (dynamic dynamicEpcisEvent in dynamicEpcisEvents)
            {
                EpcisEvent epcisEvent;
                string eventType = dynamicEpcisEvent.type;
                String rawEpcisEvent = JsonConvert.SerializeObject(dynamicEpcisEvent);
                switch (eventType)
                {
                    case "object_event":
                        epcisEvent = JsonConvert.DeserializeObject<ObjectEvent>(rawEpcisEvent);
                        break;
                    case "aggregation_event":
                        epcisEvent = JsonConvert.DeserializeObject<AggregationEvent>(rawEpcisEvent);
                        break;
                    case "transaction_event":
                        epcisEvent = JsonConvert.DeserializeObject<TransactionEvent>(rawEpcisEvent);
                        break;
                    case "transformation_event":
                        epcisEvent = JsonConvert.DeserializeObject<TransformationEvent>(rawEpcisEvent);
                        break;
                    default:
                        // event type not supported
                        continue;
                }
                Events.Add(epcisEvent);
            }
            return Events;
        }
    }
}