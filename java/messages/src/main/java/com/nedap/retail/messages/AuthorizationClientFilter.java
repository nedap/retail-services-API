package com.nedap.retail.messages;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientRequest;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.ClientFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This Client filter automatically adds the "Authorization" header to the HTTP request.
 *
 * The access token to be added as message header is received from the AccessTokenResolver.
 */
public class AuthorizationClientFilter extends ClientFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationClientFilter.class);
    private static final String LOGIN_PATH = "/login/oauth/token";
    private static final String AUTHORIZATION = "Authorization";
    private final IAccessTokenResolver accessTokenResolver;

    private volatile String accessToken;

    /**
     * Constructor.
     *
     * @param accessTokenResolver Responsible for creating new access token.
     */
    public AuthorizationClientFilter(final IAccessTokenResolver accessTokenResolver) {
        this.accessTokenResolver = accessTokenResolver;
    }

    @Override
    public ClientResponse handle(final ClientRequest request) throws ClientHandlerException {

        // When request is made to authorization server, don't don't ask for access token! It will result in endless loop!
        if (request.getURI().getPath().equals(LOGIN_PATH)) {
            return getNext().handle(request);
        }

        if (accessToken == null) {
            logger.info("getting acces token...");
            accessToken = accessTokenResolver.resolve();
        }

        // Add access token as header.
        request.getHeaders().putSingle(AUTHORIZATION, accessToken);

        // Forward the request to the next filter and get the result back
        final ClientResponse response = getNext().handle(request);
        // The server asked for authentication ? (status 401)
        if (response.getClientResponseStatus() != ClientResponse.Status.UNAUTHORIZED) {
            // Not 401 status : no authentication issue
            return response;
        }

        logger.info("access token is expired or invalid. get new access token...");
        accessToken = accessTokenResolver.resolve();
        // Overwrite access token in header. Don't use "add"!
        request.getHeaders().putSingle(AUTHORIZATION, accessToken);

        // Forward the request to the next filter and get the result back
        return getNext().handle(request);
    }
}
