using Nedap.Retail.Api.OAuth;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Epcis.V1._1
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
        /// <returns>Http status code</returns>
        public string Capture(List<EpcisEvent> events)
        {
            events.Required(() => events);

            CaptureEpcisRequest request = new CaptureEpcisRequest();
            request.Events = events;
            return apiCaller.Post<string>("/epcis/v2/capture", request);
        }
    }
}