package com.nedap.retail.messages;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource.Builder;

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

        final Builder builder = httpClient.resource(url + "/login/oauth/token")
                .queryParam("grant_type", "client_credentials")
                .queryParam("client_id", clientId)
                .queryParam("client_secret", secret).accept(APPLICATION_JSON);

        try {
            final OAuthResponse result = builder.post(OAuthResponse.class);
            logger.debug("successful. access token: {}", result.getAccess_token());
            return result.getAccess_token();
        } catch (final UniformInterfaceException uniformInterfaceException) {
            throw new ClientException(uniformInterfaceException);
        }
    }
}
