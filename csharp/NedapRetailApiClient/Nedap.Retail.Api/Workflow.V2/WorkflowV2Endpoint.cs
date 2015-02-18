using Nedap.Retail.Api.OAuth;

namespace Nedap.Retail.Api.Workflow.V2
{
    /// <summary>
    /// Workflow V2 API endpoint
    /// </summary>
    public class WorkflowV2Endpoint
    {
        private ApiCaller apiCaller;

        internal WorkflowV2Endpoint(ApiCaller apiCaller)
        {
            this.apiCaller = apiCaller;
        }

        /// <summary>
        /// Stores the workflow event.
        /// </summary>
        /// <param name="workflowEvent">Workflow event object</param>
        /// <returns>Http status code</returns>
        public string Capture(WorkflowEvent workflowEvent)
        {
            workflowEvent.Required(() => workflowEvent);
            return apiCaller.Post<string>("/workflow/v2/capture", workflowEvent);
        }
    }
}