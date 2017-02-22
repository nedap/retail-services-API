package com.nedap.retail.services.examples.oauth;

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
    private final String accessTokenUri;
    private final Client httpClient;

    /**
     * @param accessTokenUri The URL to use to obtain an OAuth2 access token
     * @param clientId       The client identifier to use for this protected resource
     * @param secret         The client secret
     * @param httpClient     HTTP client
     */
    public AccessTokenResolver(final String accessTokenUri, final String clientId, final String secret, final Client httpClient) {
        this.accessTokenUri = accessTokenUri;
        this.clientId = clientId;
        this.secret = secret;
        this.httpClient = httpClient;
    }

    @Override
    public String resolve() {
        logger.debug("getting OAuth 2.0 access token: {}", clientId);

        final WebTarget target = httpClient.target(accessTokenUri)
                .queryParam("grant_type", "client_credentials").queryParam("client_id", clientId)
                .queryParam("client_secret", secret);

        try {
            // post is done with query parameters and empty body
            final OAuthResponse response = target.request(APPLICATION_JSON).post(Entity.json(""), OAuthResponse.class);

            logger.debug("successful. access token: {}", response.getAccess_token());
            return response.getAccess_token();
        } catch (final WebApplicationException webApplicationException) {
            //TODO throw new ClientException(webApplicationException);

            return null;
        }
    }
}
