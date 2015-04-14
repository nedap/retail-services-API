package com.nedap.retail.messages;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessTokenResolver implements IAccessTokenResolver {

    private static final Logger logger = LoggerFactory.getLogger(AccessTokenResolver.class);

    private final String clientId;
    private final String secret;
    private final String url;
    private final Client httpClient;

    public AccessTokenResolver(final String url, final String clientId, final String secret, final Client httpClient) {
        this.url = url;
        this.clientId = clientId;
        this.secret = secret;
        this.httpClient = httpClient;
    }

    @Override
    public String resolve() {
        logger.debug("getting OAuth 2.0 access token: {}", clientId);

        final WebTarget target = httpClient.target(url).path("/login/oauth/token")
                .queryParam("grant_type", "client_credentials").queryParam("client_id", clientId)
                .queryParam("client_secret", secret);

        try {
            // post is done with query parameters and empty body
            final OAuthResponse response = target.request(APPLICATION_JSON).post(Entity.json(""), OAuthResponse.class);

            logger.debug("successful. access token: {}", response.getAccess_token());
            return response.getAccess_token();
        } catch (final WebApplicationException webApplicationException) {
            throw new ClientException(webApplicationException);
        }
    }
}
