package com.nedap.retail.messages.system;

import com.nedap.retail.messages.AbstractBusResponse;
import java.util.List;

public class SystemStatusResponse extends AbstractBusResponse {

    private static final long serialVersionUID = 1L;
    private List<SystemStatusPayload> payload;

    @Override
    public List<SystemStatusPayload> getPayload() {
        return payload;
    }

    public void setPayload(List<SystemStatusPayload> payload) {
        this.payload = payload;
    }
}
