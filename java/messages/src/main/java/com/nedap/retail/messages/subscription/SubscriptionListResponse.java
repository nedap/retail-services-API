package com.nedap.retail.messages.subscription;

import com.nedap.retail.messages.AbstractBusResponse;
import java.util.List;

public class SubscriptionListResponse extends AbstractBusResponse {

    private static final long serialVersionUID = 1L;
    private List<Subscription> payload;

    @Override
    public List<Subscription> getPayload() {
        return payload;
    }

    public void setPayload(List<Subscription> payload) {
        this.payload = payload;
    }
}