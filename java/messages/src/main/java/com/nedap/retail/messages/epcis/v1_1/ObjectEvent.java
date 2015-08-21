package com.nedap.retail.messages.epcis.v1_1;

import java.util.List;
import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import com.nedap.retail.messages.epcis.v1_1.cbv.Action;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;
import com.nedap.retail.messages.epcis.v1_1.cbv.EventType;
import com.nedap.retail.messages.epcis.v1_1.elements.QuantityElement;

/**
 * Required parameters to construct an object_event are: - epcList - *or* quantityList
 */
public class ObjectEvent extends EpcisEvent {

    @JsonProperty("epc_list")
    public List<String> epcList;

    @JsonProperty("quantity_list")
    public List<QuantityElement> quantityList;

    public String ilmd;

    public ObjectEvent() {
        type = EventType.ObjectEvent;
    }

    /**
     * An object_event captures information about an event pertaining to one or more physical or digital objects
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
     * @param epcList An unordered list of one or more EPCs naming specific objects to which the event pertained.
     * @param quantityList An unordered list of one or more QuantityElements identifying (at the class level) objects to
     *            which the event pertained.
     */
    public ObjectEvent(final String id, final DateTime eventTime, final DateTime recordTime,
            final String eventTimeZoneOffset, final Action action, final String bizLocation, final String readPoint,
            final Disposition disposition, final List<String> epcList, final List<QuantityElement> quantityList) {
        this.id = id;
        this.eventTime = eventTime;
        this.recordTime = recordTime;
        this.eventTimeZoneOffset = eventTimeZoneOffset;
        this.type = EventType.ObjectEvent;
        this.action = action != null ? action.action() : null;
        this.bizLocation = bizLocation;
        this.readPoint = readPoint;
        this.disposition = disposition != null ? disposition.disposition() : Disposition.UNKNOWN.disposition();
        this.epcList = epcList;
        this.quantityList = quantityList;
    }

    @Override
    public String toString() {
        final String epcListSize = epcList == null ? "null" : Integer.toString(epcList.size());
        final String quantityListSize = quantityList == null ? "null" : Integer.toString(quantityList.size());

        return "ObjectEvent" + super.toString() + "[epcList(" + epcListSize + "),quantityList(" + quantityListSize
                + ")]";
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
        final ObjectEvent other = (ObjectEvent) obj;
        if (!compareAsSet(this.epcList, other.epcList)) {
            return false;
        }
        if (!compareAsSet(this.quantityList, other.quantityList)) {
            return false;
        }
        if (!Objects.equals(this.ilmd, other.ilmd)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (epcList != null ? epcList.hashCode() : 0);
        result = 31 * result + (quantityList != null ? quantityList.hashCode() : 0);
        result = 31 * result + (ilmd != null ? ilmd.hashCode() : 0);
        return result;
    }
}
