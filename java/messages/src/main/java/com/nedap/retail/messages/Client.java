package com.nedap.retail.messages;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.sun.jersey.api.client.WebResource.Builder;

public class Client extends AbstractClient {
    private final String clientId;
    private final String secret;

    public Client(final String url, final String clientId, final String secret) {
        super(url);
        this.clientId = clientId;
        this.secret = secret;
    }

    public Client(final String url, final String clientId, final String secret, final com.sun.jersey.api.client.Client httpClient) {
        super(url, httpClient);
        this.clientId = clientId;
        this.secret = secret;
    }

    @Override
    protected final String authorize() {
        logger.debug("getting OAuth 2.0 access token: {}", clientId);

        final Builder builder = resource("/login/oauth/token").
                queryParam("grant_type", "client_credentials").
                queryParam("client_id", clientId).
                queryParam("client_secret", secret).
                accept(APPLICATION_JSON);

        final OAuthResponse result = builder.post(OAuthResponse.class);
        logger.debug("successful. access token: {}", result.getAccess_token());
        return result.getAccess_token();
    }
}

