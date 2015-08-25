package com.nedap.retail.messages.epcis.v1_1;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;
import com.nedap.retail.messages.epcis.v1_1.cbv.EventType;
import com.nedap.retail.messages.epcis.v1_1.elements.QuantityElement;

public class TransformationEvent extends EpcisEvent {

    @JsonProperty("input_epc_list")
    public List<String> inputEpcList;

    @JsonProperty("output_epc_list")
    public List<String> outputEpcList;

    @JsonProperty("input_quantity_list")
    public List<QuantityElement> inputQuantityList;

    @JsonProperty("output_quantity_list")
    public List<QuantityElement> outputQuantityList;

    @JsonProperty("transformation_id")
    public String transformationId;

    public TransformationEvent() {
        type = EventType.TransformationEvent;
    }

    /**
     * An aggregation_event captures information about an event pertaining to one or more physical or digital objects
     * identified by instance-level (EPC) or class-level (EPC Class) identifiers. Most object_events are envisioned to
     * represent actual observations of objects, but strictly speaking it can be used for any event a Capturing
     * Application wants to assert about objects, including for example capturing the fact that an expected observation
     * failed to occur.
     *
     * @param id The ID that identifies this message uniquely to an organization
     * @param eventTime The date and time at which the EPCIS Capturing Applications asserts the event occurred
     * @param recordTime The date and time at which this event was recorded by the EPCIS Repository.
     * @param eventTimeZoneOffset The time zone offset in effect at the time and place the event occurred, expressed as
     *            an offset from UTC
     * @param bizLocation The business location where the objects associated with the EPCs may be found, until
     *            contradicted by a subsequent event.
     * @param readPoint The read point at which the event took place.
     * @param disposition The business condition of the objects associated with the EPCs, presumed to hold true until
     *            contradicted by a subsequent event.
     */
    public TransformationEvent(final String id, final DateTime eventTime, final DateTime recordTime,
            final String eventTimeZoneOffset, final String bizLocation, final String readPoint,
            final Disposition disposition) {
        this.id = id;
        this.eventTime = eventTime;
        this.recordTime = recordTime;
        this.eventTimeZoneOffset = eventTimeZoneOffset;
        this.type = EventType.TransformationEvent;
        this.bizLocation = bizLocation;
        this.readPoint = readPoint;
        this.disposition = disposition != null ? disposition.disposition() : Disposition.UNKNOWN.disposition();
    }

    @Override
    public String toString() {
        final String inputEpcListSize = inputEpcList == null ? "null" : Integer.toString(inputEpcList.size());
        final String outputEpcListSize = outputEpcList == null ? "null" : Integer.toString(outputEpcList.size());
        final String inputQuantityListSize = inputQuantityList == null ? "null"
                : Integer.toString(inputQuantityList.size());
        final String outputQuantityListSize = outputQuantityList == null ? "null"
                : Integer.toString(outputQuantityList.size());

        return "TransformationEvent" + super.toString() + "[input_epc_list(" + inputEpcListSize + "),output_epc_list("
                + outputEpcListSize + "),input_quantity_list(" + inputQuantityListSize + "),output_quantity_list("
                + outputQuantityListSize + ")";
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final TransformationEvent other = (TransformationEvent) obj;
        if (!compareAsSet(this.inputEpcList, other.inputEpcList)) {
            return false;
        }
        if (!compareAsSet(this.outputEpcList, other.outputEpcList)) {
            return false;
        }
        if (!compareAsSet(this.inputQuantityList, other.inputQuantityList)) {
            return false;
        }
        if (!compareAsSet(this.outputQuantityList, other.outputQuantityList)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (inputEpcList != null ? inputEpcList.hashCode() : 0);
        result = 31 * result + (outputEpcList != null ? outputEpcList.hashCode() : 0);
        result = 31 * result + (inputQuantityList != null ? inputQuantityList.hashCode() : 0);
        result = 31 * result + (outputQuantityList != null ? outputQuantityList.hashCode() : 0);
        result = 31 * result + (transformationId != null ? transformationId.hashCode() : 0);
        return result;
    }
}
