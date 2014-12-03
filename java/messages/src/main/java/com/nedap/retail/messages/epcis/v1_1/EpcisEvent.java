package com.nedap.retail.messages.epcis.v1_1;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.annotations.SerializedName;
import com.nedap.retail.messages.epcis.v1_1.cbv.EventType;
import com.nedap.retail.messages.epcis.v1_1.elements.BizTransactionElement;
import com.nedap.retail.messages.epcis.v1_1.elements.DestinationElement;
import com.nedap.retail.messages.epcis.v1_1.elements.SourceElement;

/**
 * Required parameters to construct any EpcisEvent are:
 * - eventTime
 * - recordTime
 * - eventTimeZoneOffset
 * - type
 * - action
 *
 * @see http://nvs0272/confluence/display/storeid/EPCIS+1.1+Event#EPCIS1.1Event-EpcisEvent
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@org.codehaus.jackson.annotate.JsonTypeInfo(use = org.codehaus.jackson.annotate.JsonTypeInfo.Id.NAME, include = org.codehaus.jackson.annotate.JsonTypeInfo.As.PROPERTY, property = "type")
@org.codehaus.jackson.annotate.JsonSubTypes({
    @org.codehaus.jackson.annotate.JsonSubTypes.Type(value = ObjectEvent.class, name = "object_event"),
    @org.codehaus.jackson.annotate.JsonSubTypes.Type(value = AggregationEvent.class, name = "aggregation_event"),
    @org.codehaus.jackson.annotate.JsonSubTypes.Type(value = TransactionEvent.class, name = "transaction_event"),
    @org.codehaus.jackson.annotate.JsonSubTypes.Type(value = TransformationEvent.class, name = "transformation_event"),
    @org.codehaus.jackson.annotate.JsonSubTypes.Type(value = QuantityEvent.class, name = "quantity_event"),})
@JsonSubTypes({
    @JsonSubTypes.Type(value = ObjectEvent.class, name = "object_event"),
    @JsonSubTypes.Type(value = AggregationEvent.class, name = "aggregation_event"),
    @JsonSubTypes.Type(value = TransactionEvent.class, name = "transaction_event"),
    @JsonSubTypes.Type(value = TransformationEvent.class, name = "transformation_event"),
    @JsonSubTypes.Type(value = QuantityEvent.class, name = "quantity_event"),})
abstract public class EpcisEvent {

    public static final String ID = "id";
    @JsonProperty(ID)
    @SerializedName(ID)
    @org.codehaus.jackson.annotate.JsonProperty(ID)
    public String id;

    public static final String EVENT_TIME = "event_time";
    @JsonProperty(EVENT_TIME)
    @SerializedName(EVENT_TIME)
    @org.codehaus.jackson.annotate.JsonProperty(EVENT_TIME)
    public DateTime eventTime;

    public static final String RECORD_TIME = "record_time";
    @JsonProperty(RECORD_TIME)
    @SerializedName(RECORD_TIME)
    @org.codehaus.jackson.annotate.JsonProperty(RECORD_TIME)
    public DateTime recordTime;

    public static final String EVENT_TIME_ZONE_OFFSET = "event_time_zone_offset";
    @JsonProperty(EVENT_TIME_ZONE_OFFSET)
    @SerializedName(EVENT_TIME_ZONE_OFFSET)
    @org.codehaus.jackson.annotate.JsonProperty(EVENT_TIME_ZONE_OFFSET)
    public String eventTimeZoneOffset = "+00:00";

    public static final String TYPE = "type";
    @JsonProperty(TYPE)
    @SerializedName(TYPE)
    @org.codehaus.jackson.annotate.JsonProperty(TYPE)
    public EventType type;

    public static final String ACTION = "action";
    @JsonProperty(ACTION)
    @SerializedName(ACTION)
    @org.codehaus.jackson.annotate.JsonProperty(ACTION)
    public String action;

    public static final String BIZ_STEP = "biz_step";
    @JsonProperty(BIZ_STEP)
    @SerializedName(BIZ_STEP)
    @org.codehaus.jackson.annotate.JsonProperty(BIZ_STEP)
    public String bizStep;

    public static final String DISPOSITION = "disposition";
    @JsonProperty(DISPOSITION)
    @SerializedName(DISPOSITION)
    @org.codehaus.jackson.annotate.JsonProperty(DISPOSITION)
    public String disposition;

    public static final String BIZ_LOCATION = "biz_location";
    @JsonProperty(BIZ_LOCATION)
    @SerializedName(BIZ_LOCATION)
    @org.codehaus.jackson.annotate.JsonProperty(BIZ_LOCATION)
    public String bizLocation;

    public static final String READ_POINT = "read_point";
    @JsonProperty(READ_POINT)
    @SerializedName(READ_POINT)
    @org.codehaus.jackson.annotate.JsonProperty(READ_POINT)
    public String readPoint;

    public static final String SOURCE_LIST = "source_list";
    @JsonProperty(SOURCE_LIST)
    @SerializedName(SOURCE_LIST)
    @org.codehaus.jackson.annotate.JsonProperty(SOURCE_LIST)
    public List<SourceElement> sourceList;

    public static final String DESTINATION_LIST = "destination_list";
    @JsonProperty(DESTINATION_LIST)
    @SerializedName(DESTINATION_LIST)
    @org.codehaus.jackson.annotate.JsonProperty(DESTINATION_LIST)
    public List<DestinationElement> destinationList;

    public static final String BIZ_TRANSACTION_LIST = "biz_transaction_list";
    @JsonProperty(BIZ_TRANSACTION_LIST)
    @SerializedName(BIZ_TRANSACTION_LIST)
    @org.codehaus.jackson.annotate.JsonProperty(BIZ_TRANSACTION_LIST)
    public List<BizTransactionElement> bizTransactionList;

    @Override
    public String toString() {
        return "[id=" + id + ",eventTime=" + eventTime + "," + "type=" + (type != null ? type : "null") + ",action=" + action + ",bizLocation=" + bizLocation + ",disposition=" + disposition + ",bizStep=" + bizStep + "]";
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
        if (!Objects.equals(this.sourceList, other.sourceList)) {
            return false;
        }
        if (!Objects.equals(this.destinationList, other.destinationList)) {
            return false;
        }
        if (!Objects.equals(this.bizTransactionList, other.bizTransactionList)) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if every member of list A is contained in list B (and vise versa).
     * The order of the elements is not relevant and may contain duplicates.
     * (compares 2 lists as if they were sets).
     */
    public static boolean compareAsSet(final List<String> a, final List<String> b) {
        if (Objects.equals(a, b)) {
            return true;
        } else {
            // We need to do an extra test. Transform list's into set's (this will eliminate out of order elements
            // as well as duplicates).
            if (a != null && b != null) {
                final Set<String> setA = new HashSet<>(a);
                final Set<String> setB = new HashSet<>(b);
                return setA.equals(setB);
            } else {
                return false;
            }
        }
    }
}
