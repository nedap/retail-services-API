package com.nedap.retail.messages;

import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;

public class ClientFactory {

    private ClientFactory() {
        // avoid instantiation
    }

    /**
     * Create default client with connection timeout of 10000 and read timeout of 60000
     *
     * @return http client to use
     */
    public static javax.ws.rs.client.Client createDefault() {
        return create(10000, 60000);
    }

    /**
     * Create http client with default configuration and specified timeout values
     *
     * @param connectionTimeout connection timeout to set
     * @param readTimeout read timeout to set
     * @return http client to use
     */
    public static javax.ws.rs.client.Client create(final int connectionTimeout, final int readTimeout) {
        final ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(new IdcloudObjectMapperProvider());
        clientConfig.register(new JacksonFeature());

        // Creating an instance of a Client is an expensive operation, so try to avoid creating an unnecessary
        // number of client instances. A good approach is to reuse an existing instance, when possible.
        final javax.ws.rs.client.Client client = ClientBuilder.newClient(clientConfig);
        client.property(ClientProperties.CONNECT_TIMEOUT, connectionTimeout);
        client.property(ClientProperties.READ_TIMEOUT, readTimeout);
        client.property(ClientProperties.FOLLOW_REDIRECTS, false);

        return client;
    }
}
