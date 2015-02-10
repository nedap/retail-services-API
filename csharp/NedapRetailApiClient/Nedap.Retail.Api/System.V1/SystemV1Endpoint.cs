using Nedap.Retail.Api.OAuth;
using System.Collections.Generic;
using System.Collections.Specialized;

namespace Nedap.Retail.Api.System.V1
{
    /// <summary>
    /// System V1 API endpoint
    /// </summary>
    public class SystemV1Endpoint
    {
        private ApiCaller apiCaller;

        internal SystemV1Endpoint(ApiCaller apiCaller)
        {
            this.apiCaller = apiCaller;
        }

        /// <summary>
        /// Retrieves a list of systems you have access to
        /// </summary>
        /// <returns>List of systems</returns>
        public List<System> List()
        {
            return apiCaller.Get<List<System>>("/system/1.0/list");
        }

        /// <summary>
        /// Retrieves detailed system status information about all systems you have access to
        /// </summary>
        /// <returns>A list of system statuses</returns>
        public List<SystemStatus> Status()
        {
            return apiCaller.Get<List<SystemStatus>>("/system/1.0/status");
        }

        /// <summary>
        /// Retrieves detailed system status information about a specific system
        /// </summary>
        /// <param name="systemId">The system ID of a system</param>
        /// <returns>The detailed system status of the specified system</returns>
        public SystemStatus Status(string systemId)
        {
            NameValueCollection parameters = new NameValueCollection();
            parameters.Add("system_id", systemId);
            return apiCaller.Get<SystemStatus>("/system/1.0/status", parameters);
        }
    }
}