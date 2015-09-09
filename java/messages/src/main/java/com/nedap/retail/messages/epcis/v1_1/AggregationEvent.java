package com.nedap.retail.messages.epcis.v1_1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import com.nedap.retail.messages.epcis.v1_1.cbv.Action;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;
import com.nedap.retail.messages.epcis.v1_1.cbv.EventType;
import com.nedap.retail.messages.epcis.v1_1.elements.QuantityElement;

public class AggregationEvent extends EpcisEvent {

    /**
     * Example: urn:epc:id:sscc:08410580.999999999
     */
    @JsonProperty("parent_id")
    public String parentId;

    @JsonProperty("child_epcs")
    public List<String> childEpcs;

    @JsonProperty("child_quantity_list")
    public List<QuantityElement> childQuantityList;

    public AggregationEvent() {
        this.type = EventType.AggregationEvent;
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
     * @param action How this event relates to the lifecycle of the EPCs named in this event.
     * @param bizLocation The business location where the objects associated with the EPCs may be found, until
     *            contradicted by a subsequent event.
     * @param readPoint The read point at which the event took place.
     * @param disposition The business condition of the objects associated with the EPCs, presumed to hold true until
     *            contradicted by a subsequent event.
     * @param parentId The identifier of the parent of the association. If the parentID is an EPC, the EPC's URI is
     *            required. This field is required, unless the action is OBSERVE.
     * @param epcList An unordered list of one or more EPCs naming specific objects to which the event pertained. If
     *            null empty list is assigned
     * @param quantityList An unordered list of one or more QuantityElements identifying (at the class level) objects to
     *            which the event pertained. If null empty list is assigned
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
        this.childEpcs = epcList == null ? new ArrayList<String>() : epcList;
        this.childQuantityList = quantityList == null ? new ArrayList<QuantityElement>() : quantityList;
    }

    @Override
    public String toString() {
        final String epcListSize = childEpcs == null ? "null" : Integer.toString(childEpcs.size());
        final String quantityListSize = childQuantityList == null ? "null" : Integer.toString(childQuantityList.size());

        return "AggregationEvent" + super.toString() + "[parent_id(" + parentId + "),child_epcs(" + epcListSize
                + "),quantityList(" + quantityListSize + ")]";
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
        final AggregationEvent other = (AggregationEvent) obj;
        if (!Objects.equals(this.parentId, other.parentId)) {
            return false;
        }
        if (!compareAsSet(this.childEpcs, other.childEpcs)) {
            return false;
        }
        if (!compareAsSet(this.childQuantityList, other.childQuantityList)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (childEpcs != null ? childEpcs.hashCode() : 0);
        result = 31 * result + (childQuantityList != null ? childQuantityList.hashCode() : 0);
        return result;
    }
}
