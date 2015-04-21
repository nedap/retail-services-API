package com.nedap.retail.messages.subscription;

import java.io.Serializable;

public class Subscription implements Serializable {

    private static final long serialVersionUID = -1754895195431644892L;

    public String topic;
    public String callback;

    public Subscription() {
    }

    public Subscription(final String topic, final String callback) {
        this.topic = topic;
        this.callback = callback;
    }
}
