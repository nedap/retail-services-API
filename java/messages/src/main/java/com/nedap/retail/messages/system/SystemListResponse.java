package com.nedap.retail.messages.system;

import com.nedap.retail.messages.AbstractBusResponse;
import java.util.List;

public class SystemListResponse extends AbstractBusResponse {

    private static final long serialVersionUID = 1L;
    private List<SystemListPayload> payload;

    @Override
    public List<SystemListPayload> getPayload() {
        return payload;
    }

    public void setPayload(List<SystemListPayload> payload) {
        this.payload = payload;
    }
}
