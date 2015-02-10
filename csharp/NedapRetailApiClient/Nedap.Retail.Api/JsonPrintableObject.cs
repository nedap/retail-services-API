using Newtonsoft.Json;

namespace Nedap.Retail.Api
{
    /// <summary>
    /// Abstract class that provides override of default ToString method with JsonCovert object serialization
    /// </summary>
    public abstract class JsonPrintableObject
    {
        /// <summary>
        /// Returns string representation of the object
        /// </summary>
        /// <returns>Formatted JSON string</returns>
        public override string ToString()
        {
            return JsonConvert.SerializeObject(this, Formatting.Indented);
        }
    }
}