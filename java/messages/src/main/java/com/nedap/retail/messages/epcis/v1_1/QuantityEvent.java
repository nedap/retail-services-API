package com.nedap.retail.messages.epcis.v1_1;

import com.nedap.retail.messages.epcis.v1_1.cbv.EventType;

/**
 * @deprecated The Quantity Event is deprecated in EPCIS 1.1, as it is replaced by new features added to the Object
 *             Event in EPCIS 1.1
 */
@Deprecated
public class QuantityEvent extends EpcisEvent {

    public QuantityEvent() {
        type = EventType.QuantityEvent;
    }
}
