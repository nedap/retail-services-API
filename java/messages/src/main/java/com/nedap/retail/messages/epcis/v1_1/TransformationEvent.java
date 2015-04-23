package com.nedap.retail.messages.epcis.v1_1;

import java.util.List;
import java.util.Objects;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;
import com.nedap.retail.messages.epcis.v1_1.cbv.EventType;
import com.nedap.retail.messages.epcis.v1_1.elements.QuantityElement;

public class TransformationEvent extends EpcisEvent {

    public static final String INPUT_EPC_LIST = "input_epc_list";
    @JsonProperty(INPUT_EPC_LIST)
    @SerializedName(INPUT_EPC_LIST)
    @org.codehaus.jackson.annotate.JsonProperty(INPUT_EPC_LIST)
    public List<String> inputEpcList;

    public static final String OUTPUT_EPC_LIST = "output_epc_list";
    @JsonProperty(OUTPUT_EPC_LIST)
    @SerializedName(OUTPUT_EPC_LIST)
    @org.codehaus.jackson.annotate.JsonProperty(OUTPUT_EPC_LIST)
    public List<String> outputEpcList;

    public static final String INPUT_QUANTITY_LIST = "input_quantity_list";
    @JsonProperty(INPUT_QUANTITY_LIST)
    @SerializedName(INPUT_QUANTITY_LIST)
    @org.codehaus.jackson.annotate.JsonProperty(INPUT_QUANTITY_LIST)
    public List<QuantityElement> inputQuantityList;

    public static final String OUTPUT_QUANTITY_LIST = "output_quantity_list";
    @JsonProperty(OUTPUT_QUANTITY_LIST)
    @SerializedName(OUTPUT_QUANTITY_LIST)
    @org.codehaus.jackson.annotate.JsonProperty(OUTPUT_QUANTITY_LIST)
    public List<QuantityElement> outputQuantityList;

    public static final String TRANSFORMATION_ID = "transformation_id";
    @JsonProperty(TRANSFORMATION_ID)
    @SerializedName(TRANSFORMATION_ID)
    @org.codehaus.jackson.annotate.JsonProperty(TRANSFORMATION_ID)
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
        final String inputQuantityListSize = inputQuantityList == null ? "null" : Integer.toString(inputQuantityList
                .size());
        final String outputQuantityListSize = outputQuantityList == null ? "null" : Integer.toString(outputQuantityList
                .size());

        return "TransformationEvent" + super.toString() + "[input_epc_list(" + inputEpcListSize + "),output_epc_list("
                + outputEpcListSize + "),input_quantity_list(" + inputQuantityListSize + "),output_quantity_list("
                + outputQuantityListSize + ")";
    }

    @Override
    public boolean equals(final Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransformationEvent other = (TransformationEvent) obj;
        if (!compareAsSet(this.inputEpcList, other.inputEpcList)) {
            return false;
        }
        if (!compareAsSet(this.outputEpcList, other.outputEpcList)) {
            return false;
        }
        if (!Objects.equals(this.inputQuantityList, other.inputQuantityList)) {
            return false;
        }
        if (!Objects.equals(this.outputQuantityList, other.outputQuantityList)) {
            return false;
        }
        return true;
    }
}
