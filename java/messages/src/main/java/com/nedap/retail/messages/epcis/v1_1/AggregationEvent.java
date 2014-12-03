package com.nedap.retail.messages.epcis.v1_1;

import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.nedap.retail.messages.epcis.v1_1.cbv.Action;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;
import com.nedap.retail.messages.epcis.v1_1.cbv.EventType;
import com.nedap.retail.messages.epcis.v1_1.elements.QuantityElement;

public class AggregationEvent extends EpcisEvent {

    /**
     * Example: urn:epc:id:sscc:08410580.999999999
     */
    public static final String PARENT_ID = "parent_id";
    @JsonProperty(PARENT_ID)
    @SerializedName(PARENT_ID)
    @org.codehaus.jackson.annotate.JsonProperty(PARENT_ID)
    public String parentId;

    public static final String CHILD_EPCS = "child_epcs";
    @JsonProperty(CHILD_EPCS)
    @SerializedName(CHILD_EPCS)
    @org.codehaus.jackson.annotate.JsonProperty(CHILD_EPCS)
    public List<String> epcList;

    public static final String CHILD_QUANTITY_LIST = "child_quantity_list";
    @JsonProperty(CHILD_QUANTITY_LIST)
    @SerializedName(CHILD_QUANTITY_LIST)
    @org.codehaus.jackson.annotate.JsonProperty(CHILD_QUANTITY_LIST)
    public List<QuantityElement> quantityList;

    public AggregationEvent() {
        type = EventType.AggregationEvent;
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
     * @param eventTimeZoneOffset The time zone offset in effect at the time and place the event occurred, expressed as an offset from UTC
     * @param action How this event relates to the lifecycle of the EPCs named in this event.
     * @param bizLocation The business location where the objects associated with the EPCs may be found, until contradicted by a subsequent event.
     * @param readPoint The read point at which the event took place.
     * @param disposition The business condition of the objects associated with the EPCs, presumed to hold true until contradicted by a subsequent event.
     * @param parentId The identifier of the parent of the association. If the parentID is an EPC, the EPC's URI is required. This field is required, unless the action is OBSERVE.
     * @param epcList An unordered list of one or more EPCs naming specific objects to which the event pertained.
     * @param quantityList An unordered list of one or more QuantityElements identifying (at the class level) objects to which the event pertained.
     */
    public AggregationEvent(final String id, final DateTime eventTime, final DateTime recordTime,
            final String eventTimeZoneOffset, final Action action, final String bizLocation, final String readPoint,
            final Disposition disposition, final String parentId, final List<String> epcList,
            final List<QuantityElement> quantityList) {
        this.id = id;
        this.eventTime = eventTime;
        this.recordTime = recordTime;
        this.eventTimeZoneOffset = eventTimeZoneOffset;
        this.type = EventType.AggregationEvent;
        this.action = action != null ? action.action() : null;
        this.bizLocation = bizLocation;
        this.readPoint = readPoint;
        this.disposition = disposition != null ? disposition.disposition() : Disposition.UNKNOWN.disposition();
        this.parentId = parentId;
        this.epcList = epcList;
        this.quantityList = quantityList;
    }

    @Override
    public String toString() {
        final String epcListSize = epcList == null ? "null" : Integer.toString(epcList.size());
        final String quantityListSize = quantityList == null ? "null" : Integer.toString(quantityList.size());

        return "AggregationEvent" + super.toString() + "[parent_id(" + parentId + "),child_epcs(" + epcListSize
                + "),quantityList(" + quantityListSize + ")]";
    }
}
