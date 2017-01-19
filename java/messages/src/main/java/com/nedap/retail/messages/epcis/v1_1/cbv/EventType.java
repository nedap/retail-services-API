package com.nedap.retail.messages.epcis.v1_1.cbv;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.nedap.retail.messages.epcis.v1_1.AggregationEvent;
import com.nedap.retail.messages.epcis.v1_1.EpcisEvent;
import com.nedap.retail.messages.epcis.v1_1.ObjectEvent;
import com.nedap.retail.messages.epcis.v1_1.QuantityEvent;
import com.nedap.retail.messages.epcis.v1_1.TransactionEvent;
import com.nedap.retail.messages.epcis.v1_1.TransformationEvent;
import io.swagger.annotations.ApiModel;

/**
 * Enumerates EPCIS EventType Specifications follow GS1 EPCIS v1.1 standard + Nedap extensions
 */
@ApiModel
public enum EventType {

    /**
     * Captures information about an event pertaining to one or more physical or digital objects identified by
     * instance-level (EPC) or class-level (EPC Class) identifiers.
     */
    ObjectEvent(1, "object_event", ObjectEvent.class),
    /**
     * Describes events that apply to objects that have been aggregated to one another
     */
    AggregationEvent(2, "aggregation_event", AggregationEvent.class),
    /**
     * Describes the association or disassociation of physical or digital objects to one or more business transactions.
     */
    TransactionEvent(3, "transaction_event", TransactionEvent.class),
    /**
     * @deprecated The Quantity Event is deprecated in EPCIS 1.1, as it is replaced by new features added to the Object
     *             Event in EPCIS 1.1
     */
    QuantityEvent(4, "quantity_event", QuantityEvent.class),
    /**
     * Captures information about an event in which one or more physical or digital objects identified by instance-level
     * (EPC) or class-level (EPC Class) identifiers are consumed as inputs and one or more objects identified by
     * instance-level (EPC) or class-level (EPC Class) identifiers are produced as outputs.
     */
    TransformationEvent(5, "transformation_event", TransformationEvent.class);

    private final int number;
    private final String eventType;
    private final Class<? extends EpcisEvent> clz;

    private EventType(final int aNumber, final String anEventType, final Class<? extends EpcisEvent> aClz) {
        number = aNumber;
        eventType = anEventType;
        clz = aClz;
    }

    /**
     * @return EPCIS' event type Nedap identifier
     */
    public final int number() {
        return number;
    }

    /**
     * @return EPCIS' event type
     */
    @JsonValue
    public final String eventType() {
        return eventType;
    }

    /**
     * @return Class type
     */
    public final Class<? extends EpcisEvent> type() {
        return clz;
    }

    /**
     * Provides similar functionality to valueOf(..).
     *
     * @param value String value of requested event_type
     * @return EventType enum value for provided String
     */
    @JsonCreator
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
