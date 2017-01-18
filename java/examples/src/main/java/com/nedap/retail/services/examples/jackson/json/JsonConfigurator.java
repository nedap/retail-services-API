package com.nedap.retail.services.examples.jackson.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nedap.retail.client.ApiClient;
import com.nedap.retail.client.model.EpcisEvent;

/**
 * Created by marke on 17-1-17.
 */
public abstract class JsonConfigurator {

    public static void configure(final ApiClient defaultClient) {
        final SimpleModule module = new SimpleModule("EpcisEventDeserializerModule", new Version( 1, 0, 0, null, null, null ));
        module.addDeserializer(EpcisEvent.class, new EpcisEventDeserializer(defaultClient.getJSON().getContext(EpcisEvent.class)));
        defaultClient.getJSON().getContext(EpcisEvent.class).registerModule(module);
    }
}
