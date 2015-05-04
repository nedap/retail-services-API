using Newtonsoft.Json;
using System;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    /// <summary>
    /// Business transaction
    /// </summary>
    public class BusinessTransaction : JsonPrintableObject
    {
        /// <summary>
        /// Optional. An identifier that indicates what kind of business transaction this BusinessTransaction denotes. Standard Vocabulary, however, EPCIS does not specify Master Data Attributes
        /// </summary>
        [JsonProperty("type")]
        public String Type { get; set; }

        /// <summary>
        /// An identifier that denotes a specific business transaction. User Vocabulary.
        /// </summary>
        [JsonProperty("biz_transaction")]
        public String BizTransaction { get; set; }

        /// <summary>
        /// Equals method for comparison of business transactions
        /// </summary>
        /// <param name="obj">Object to compare to</param>
        /// <returns>True if businesstransactions match</returns>
        public override bool Equals(object obj)
        {
            // If parameter is null return false.
            if (obj == null)
            {
                return false;
            }

            // If parameter cannot be cast to BusinessTransaction return false.
            BusinessTransaction p = obj as BusinessTransaction;
            if ((global::System.Object)p == null)
            {
                return false;
            }

            // Return true if the fields match:
            return Equals(p);
        }

        /// <summary>
        /// Equals method for comparison of business transactions
        /// </summary>
        /// <param name="p">BusinessTransaction to compare to</param>
        /// <returns>True if businesstransactions match</returns>
        public bool Equals(BusinessTransaction p)
        {
            // If parameter is null return false:
            if ((object)p == null)
            {
                return false;
            }

            // Return true if the fields match:
            return Type.Equals(p.Type) && BizTransaction.Equals(p.BizTransaction);
        }

        /// <summary>
        /// Override default Hashcode method
        /// </summary>
        /// <returns>Hashcode</returns>
        public override int GetHashCode()
        {
            string hashBase = "";
            if (Type != null)
            {
                hashBase += Type;
            }
            if (BizTransaction != null)
            {
                hashBase += BizTransaction;
            }
            return hashBase.GetHashCode();
        }
    }
}