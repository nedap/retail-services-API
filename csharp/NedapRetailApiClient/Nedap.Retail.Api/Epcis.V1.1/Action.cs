namespace Nedap.Retail.Api.Epcis.V1._1
{
    /// <summary>
    /// Epcis event action
    /// </summary>
    public enum Action
    {
        /// <summary>
        /// The objects identified in the event have been commissioned as part of this event.
        /// </summary>
        ADD,
        /// <summary>
        /// The event represents a simple observation of the objects identified in the event, not their commissioning or decommissioning.
        /// </summary>
        OBSERVE,
        /// <summary>
        /// The objects identified in the event have been decommissioned as part of this event.
        /// </summary>
        DELETE
    }
}