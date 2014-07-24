package com.nedap.retail.messages.epcis.v1_1;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

import com.google.gson.annotations.SerializedName;
import com.nedap.retail.messages.epcis.v1_1.cbv.Action;
import com.nedap.retail.messages.epcis.v1_1.cbv.Disposition;
import com.nedap.retail.messages.epcis.v1_1.cbv.EventType;
import com.nedap.retail.messages.epcis.v1_1.elements.QuantityElement;

/**
 * Required parameters to construct an object_event are:
 * - epcList
 * - *or* quantityList
 *
 * @see http://nvs0272/confluence/display/storeid/EPCIS+1.1+Event#EPCIS1.1Event-ObjectEvent
 */
public class ObjectEvent extends EpcisEvent {

    public static final String EPC_LIST = "epc_list";
    @JsonProperty(EPC_LIST)
    @SerializedName(EPC_LIST)
    public Set<String> epcList;

    public static final String QUANTITY_LIST = "quantity_list";
    @JsonProperty(QUANTITY_LIST)
    @SerializedName(QUANTITY_LIST)
    public List<QuantityElement> quantityList;

    public static final String ILMD = "ilmd";
    @JsonProperty(ILMD)
    @SerializedName(ILMD)
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
     * @param eventTimeZoneOffset The time zone offset in effect at the time and place the event occurred, expressed as an offset from UTC
     * @param action How this event relates to the lifecycle of the EPCs named in this event.
     * @param bizLocation The business location where the objects associated with the EPCs may be found, until contradicted by a subsequent event.
     * @param readPoint The read point at which the event took place.
     * @param disposition The business condition of the objects associated with the EPCs, presumed to hold true until contradicted by a subsequent event.
     * @param epcList An unordered list of one or more EPCs naming specific objects to which the event pertained.
     * @param quantityList An unordered list of one or more QuantityElements identifying (at the class level) objects to which the event pertained.
     */
    public ObjectEvent(final String id, final DateTime eventTime, final DateTime recordTime,
            final String eventTimeZoneOffset, final Action action, final String bizLocation, final String readPoint,
            final Disposition disposition, final Set<String> epcList,
            final List<QuantityElement> quantityList) {
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

        return "ObjectEvent" + super.toString() + "[epcList(" + epcListSize + "),quantityList(" + quantityListSize + ")]";
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
        final ObjectEvent other = (ObjectEvent) obj;
        if (!Objects.equals(this.epcList, other.epcList)) {
            return false;
        }
        if (!Objects.equals(this.quantityList, other.quantityList)) {
            return false;
        }
        if (!Objects.equals(this.ilmd, other.ilmd)) {
            return false;
        }
        return true;
    }
}
