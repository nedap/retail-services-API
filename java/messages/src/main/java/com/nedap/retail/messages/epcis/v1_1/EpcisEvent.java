package com.nedap.retail.messages.epcis.v1_1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.nedap.retail.messages.epcis.v1_1.cbv.EventType;
import com.nedap.retail.messages.epcis.v1_1.elements.BizTransactionElement;
import com.nedap.retail.messages.epcis.v1_1.elements.DestinationElement;
import com.nedap.retail.messages.epcis.v1_1.elements.SourceElement;

/**
 * Required parameters to construct any EpcisEvent are: - eventTime - recordTime - eventTimeZoneOffset - type - action
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@org.codehaus.jackson.annotate.JsonTypeInfo(use = org.codehaus.jackson.annotate.JsonTypeInfo.Id.NAME, include = org.codehaus.jackson.annotate.JsonTypeInfo.As.PROPERTY, property = "type")
@org.codehaus.jackson.annotate.JsonSubTypes({
        @org.codehaus.jackson.annotate.JsonSubTypes.Type(value = ObjectEvent.class, name = "object_event"),
        @org.codehaus.jackson.annotate.JsonSubTypes.Type(value = AggregationEvent.class, name = "aggregation_event"),
        @org.codehaus.jackson.annotate.JsonSubTypes.Type(value = TransactionEvent.class, name = "transaction_event"),
        @org.codehaus.jackson.annotate.JsonSubTypes.Type(value = TransformationEvent.class, name = "transformation_event"),
        @org.codehaus.jackson.annotate.JsonSubTypes.Type(value = QuantityEvent.class, name = "quantity_event"),})
@JsonSubTypes({@JsonSubTypes.Type(value = ObjectEvent.class, name = "object_event"),
        @JsonSubTypes.Type(value = AggregationEvent.class, name = "aggregation_event"),
        @JsonSubTypes.Type(value = TransactionEvent.class, name = "transaction_event"),
        @JsonSubTypes.Type(value = TransformationEvent.class, name = "transformation_event"),
        @JsonSubTypes.Type(value = QuantityEvent.class, name = "quantity_event"),})
abstract public class EpcisEvent {

    public String id;

    @JsonProperty("event_time")
    public DateTime eventTime;

    @JsonProperty("record_time")
    public DateTime recordTime;

    @JsonProperty("event_time_zone_offset")
    public String eventTimeZoneOffset = "+00:00";

    public EventType type;

    public String action;

    @JsonProperty("biz_step")
    public String bizStep;

    public String disposition;

    @JsonProperty("biz_location")
    public String bizLocation;

    @JsonProperty("read_point")
    public String readPoint;

    @JsonProperty("source_list")
    public List<SourceElement> sourceList;

    @JsonProperty("destination_list")
    public List<DestinationElement> destinationList;

    @JsonProperty("biz_transaction_list")
    public List<BizTransactionElement> bizTransactionList;

    public EpcisEvent() {
        this.sourceList = new ArrayList<>();
        this.destinationList = new ArrayList<>();
        this.bizTransactionList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "[id=" + id + ",eventTime=" + eventTime + "," + "type=" + (type != null ? type : "null") + ",action="
                + action + ",bizLocation=" + bizLocation + ",disposition=" + disposition + ",bizStep=" + bizStep + "]";
    }

    @Override
    // do not compare record time, that is determined when event is stored in DB, not when sent by client
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EpcisEvent other = (EpcisEvent) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.eventTime.toDate(), other.eventTime.toDate())) {
            return false;
        }
        if (!Objects.equals(this.eventTimeZoneOffset, other.eventTimeZoneOffset)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.action, other.action)) {
            return false;
        }
        if (!Objects.equals(this.bizStep, other.bizStep)) {
            return false;
        }
        if (!Objects.equals(this.disposition, other.disposition)) {
            return false;
        }
        if (!Objects.equals(this.bizLocation, other.bizLocation)) {
            return false;
        }
        if (!Objects.equals(this.readPoint, other.readPoint)) {
            return false;
        }
        if (!compareAsSet(this.sourceList, other.sourceList)) {
            return false;
        }
        if (!compareAsSet(this.destinationList, other.destinationList)) {
            return false;
        }
        if (!compareAsSet(this.bizTransactionList, other.bizTransactionList)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (eventTime != null ? eventTime.hashCode() : 0);
        result = 31 * result + (eventTimeZoneOffset != null ? eventTimeZoneOffset.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (bizStep != null ? bizStep.hashCode() : 0);
        result = 31 * result + (disposition != null ? disposition.hashCode() : 0);
        result = 31 * result + (bizLocation != null ? bizLocation.hashCode() : 0);
        result = 31 * result + (readPoint != null ? readPoint.hashCode() : 0);
        result = 31 * result + (sourceList != null ? sourceList.hashCode() : 0);
        result = 31 * result + (destinationList != null ? destinationList.hashCode() : 0);
        result = 31 * result + (bizTransactionList != null ? bizTransactionList.hashCode() : 0);
        return result;
    }

    /**
     * Returns true if every member of list A is contained in list B (and vise versa). The order of the elements is not
     * relevant and may contain duplicates. (compares 2 lists as if they were sets).
     * 
     * @param a A list of strings
     * @param b A second list of strings
     * @param <T> Type of list content
     * 
     * @return true if every member of list A is contained in list B (and vise versa)
     */
    public static <T> boolean compareAsSet(final List<T> a, final List<T> b) {
        if (Objects.equals(a, b)) {
            return true;
        } else {
            // We need to do an extra test. Transform list's into set's (this will eliminate out of order elements
            // as well as duplicates).
            if (a != null && b != null) {
                final Set<T> setA = new HashSet<>(a);
                final Set<T> setB = new HashSet<>(b);
                return setA.equals(setB);
            } else {
                return false;
            }
        }
    }
}
