package com.nedap.retail.messages.epcis.v1_1;

import com.nedap.retail.messages.epcis.v1_1.cbv.EventType;

@Deprecated
public class QuantityEvent extends EpcisEvent {

    public QuantityEvent() {
        type = EventType.QuantityEvent;
    }
}
