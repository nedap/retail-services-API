using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Nedap.Retail.Api.Epcis.V1._1
{
    public class BusinessTransaction : JsonPrintableObject
    {
        /// <summary>
        /// Optional. An identifier that indicates what kind of business transaction this BusinessTransaction denotes. Standard Vocabulary, however, EPCIS does not specify Master Data Attributes
        /// </summary>
        [JsonProperty("type")]
        public string Type { get; set; }

        /// <summary>
        /// An identifier that denotes a specific business transaction. User Vocabulary.
        /// </summary>
        [JsonProperty("biz_transaction")]
        public string BizTransaction { get; set; }
    }
}