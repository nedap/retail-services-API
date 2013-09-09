package com.nedap.retail.messages.subscription;

import java.io.Serializable;

public class Subscription implements Serializable {

    private String topic;
    private String callback;

    public Subscription() {
    }

    public Subscription(String topic, String callback) {
        this.topic = topic;
        this.callback = callback;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
