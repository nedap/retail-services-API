package com.nedap.retail.messages;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Response message
 */
@XmlRootElement
public class ApiResponse extends AbstractApiResponse {

    private static final long serialVersionUID = 1L;
    protected Object payload;

    /**
     * Default constructor.
     */
    public ApiResponse() {
    }

    @Override
    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
