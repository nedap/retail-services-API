package com.nedap.retail.messages.epcis.v1_1.cbv;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.gson.annotations.SerializedName;
import com.nedap.retail.messages.epcis.v1_1.AggregationEvent;
import com.nedap.retail.messages.epcis.v1_1.EpcisEvent;
import com.nedap.retail.messages.epcis.v1_1.ObjectEvent;
import com.nedap.retail.messages.epcis.v1_1.QuantityEvent;
import com.nedap.retail.messages.epcis.v1_1.TransactionEvent;
import com.nedap.retail.messages.epcis.v1_1.TransformationEvent;

/**
 * Enumerates EPCIS EventType Specifications follow GS1 EPCIS v1.1 standard + Nedap extensions
 *
 * @see http://nvs0272/confluence/display/storeid/EPCIS+1.1+Event
 */
public enum EventType {

    /**
     * Captures information about an event pertaining to one or more physical or digital objects identified by
     * instance-level (EPC) or class-level (EPC Class) identifiers.
     */
    @SerializedName("object_event")
    ObjectEvent(1, "object_event", ObjectEvent.class),
    /**
     * Describes events that apply to objects that have been aggregated to one another
     */
    @SerializedName("aggregation_event")
    AggregationEvent(2, "aggregation_event", AggregationEvent.class),
    /**
     * Describes the association or disassociation of physical or digital objects to one or more business transactions.
     */
    @SerializedName("transaction_event")
    TransactionEvent(3, "transaction_event", TransactionEvent.class),
    /**
     * @deprecated
     */
    @SerializedName("quantity_event")
    QuantityEvent(4, "quantity_event", QuantityEvent.class),
    /**
     * Captures information about an event in which one or more physical or digital objects identified by instance-level
     * (EPC) or class-level (EPC Class) identifiers are consumed as inputs and one or more objects identified by
     * instance-level (EPC) or class-level (EPC Class) identifiers are produced as outputs.
     */
    @SerializedName("transformation_event")
    TransformationEvent(5, "transformation_event", TransformationEvent.class);

    public final int number;
    public final String eventType;
    public final Class<? extends EpcisEvent> clz;

    private EventType(final int aNumber, final String anEventType, final Class<? extends EpcisEvent> aClz) {
        number = aNumber;
        eventType = anEventType;
        clz = aClz;
    }

    /**
     * Provides similar functionality to valueOf(..).
     *
     * @param value String value of requested event_type
     * @return EventType enum value for provided String
     * @see valueOf(..)
     */
    @JsonCreator
    @org.codehaus.jackson.annotate.JsonCreator
    public static EventType permissiveValueOf(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("event type missing");
        }
        for (final EventType v : values()) {
            if (value.equalsIgnoreCase(v.eventType)) {
                return v;
            }
        }
        throw new IllegalArgumentException("unknown event type");
    }

    @Override
    public final String toString() {
        return this.name();
    }
}
