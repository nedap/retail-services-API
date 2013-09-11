package com.nedap.retail.messages.system;

import com.nedap.retail.messages.AbstractApiResponse;
import java.util.List;

public class SystemListResponse extends AbstractApiResponse {

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
