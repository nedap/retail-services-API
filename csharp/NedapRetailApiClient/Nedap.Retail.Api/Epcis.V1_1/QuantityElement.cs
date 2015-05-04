using Newtonsoft.Json;
using System;

namespace Nedap.Retail.Api.Epcis.V1_1
{
    /// <summary>
    /// Quantity element
    /// </summary>
    public class QuantityElement : JsonPrintableObject
    {
        /// <summary>
        /// A class-level identifier for the class to which the specified quantity of objects belongs. EPCClass is a User Vocabulary.
        /// </summary>
        [JsonProperty("epc_class")]
        public String EpcClass { get; set; }

        /// <summary>
        /// A number that specifies how many or how much of the specified EPCClass is denoted by this QuantityElement.
        /// - If no uom is present, quantity must be an integer describing count.
        /// - If uom is present, quantity can be integer or float describing length, area, volume, or mass.
        /// Negative values are not allowed.
        /// </summary>
        [JsonProperty("quantity")]
        public float quantity { get; set; }

        /// <summary>
        /// Optional. Specifies a unit of measure by which the specified quantity is to be interpreted as a physical measure, specifying how much of the specified EPCClass is denoted by this QuantityElement.
        /// Is a 2- or 3-character code for a physical unit specified in the Common Code column of UN/CEFACT Recommendation 20.
        /// </summary>
        [JsonProperty("uom")]
        public String Uom { get; set; }


        /// <summary>
        /// Equals method for comparison of quantity elements
        /// </summary>
        /// <param name="obj">Object to compare to</param>
        /// <returns>True if quantity elements match</returns>
        public override bool Equals(object obj)
        {
            // If parameter is null return false.
            if (obj == null)
            {
                return false;
            }

            // If parameter cannot be cast to QuantityElement return false.
            QuantityElement p = obj as QuantityElement;
            if ((global::System.Object)p == null)
            {
                return false;
            }

            // Return true if the fields match:
            return Equals(p);
        }

        /// <summary>
        /// Equals method for comparison of quantity elements
        /// </summary>
        /// <param name="p">Quantity element to compare to</param>
        /// <returns>True if quantity elements match</returns>
        public bool Equals(QuantityElement p)
        {
            // If parameter is null return false:
            if ((object)p == null)
            {
                return false;
            }

            // Return true if the fields match:
            return EpcClass.Equals(EpcClass) && quantity.Equals(p.quantity) && Uom.Equals(p.Uom);
        }

        /// <summary>
        /// Override default Hashcode method
        /// </summary>
        /// <returns>Hashcode</returns>
        public override int GetHashCode()
        {
            string hashBase = "";
            if (EpcClass != null)
            {
                hashBase += EpcClass;
            }
            hashBase += quantity.ToString();
            if (Uom != null)
            {
                hashBase += Uom;
            }
            return hashBase.GetHashCode();
        }
    }
}