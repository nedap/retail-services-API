using Newtonsoft.Json;
using System.Collections.Generic;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    /// <summary>
    /// Object representing a parameter in an EPCIS query
    /// </summary>
    public class ParameterObject : JsonPrintableObject
    {
        /// <summary>
        /// Constructor for EPCIS query parameter
        /// </summary>
        /// <param name="name">Name of parameter</param>
        /// <param name="value">Single value for parameter</param>
        public ParameterObject(string name, string value)
        {
            Name = name;
            Value = value;
        }

        /// <summary>
        /// Constructor for EPCIS query parameter
        /// </summary>
        /// <param name="name">Name of parameter</param>
        /// <param name="type">Type as specified in the query parameter specification</param>
        /// <param name="value">Single value for parameter</param>
        public ParameterObject(string name, string type, string value)
        {
            Name = name;
            Type = type;
            Value = value;
        }

        /// <summary>
        /// Constructor for EPCIS query parameter
        /// </summary>
        /// <param name="name">Name of parameter</param>
        /// <param name="values">Multiple values for parameter</param>
        public ParameterObject(string name, List<string> values)
        {
            Name = name;
            Values = values;
        }

        /// <summary>
        /// Constructor for EPCIS query parameter
        /// </summary>
        /// <param name="name">Name of parameter</param>
        /// <param name="type">Type as specified in the query parameter specification</param>
        /// <param name="values">Multiple values for parameter</param>
        public ParameterObject(string name, string type, List<string> values)
        {
            Name = name;
            Type = type;
            Values = values;
        }

        /// <summary>
        /// Name
        /// </summary>
        [JsonProperty("name")]
        public string Name { get; private set; }

        /// <summary>
        /// Type
        /// </summary>
        [JsonProperty("type")]
        public string Type { get; private set; }

        /// <summary>
        /// Value
        /// </summary>
        [JsonProperty("value")]
        public string Value { get; private set; }

        /// <summary>
        /// Values
        /// </summary>
        [JsonProperty("values")]
        public List<string> Values { get; private set; }

    }
}
