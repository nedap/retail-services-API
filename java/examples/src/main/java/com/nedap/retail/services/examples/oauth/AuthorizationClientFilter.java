package com.nedap.retail.services.examples.oauth;

import com.nedap.retail.client.ApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * This Client filter automatically adds the "Authorization" header to the HTTP request.
 *
 * The access token to be added as message header is received from the AccessTokenResolver.
 */
public class AuthorizationClientFilter implements ClientRequestFilter, ClientResponseFilter {

    private static final String LOGIN_PATH = "/login/oauth/token";
    private final IAccessTokenResolver accessTokenResolver;
    private static final String REQUEST_PROPERTY_FILTER_REUSED = "com.nedap.retail.messages.AuthorizationClientFilter.reused";

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
    public void filter(final ClientRequestContext requestContext) throws IOException {

        if ("true".equals(requestContext.getProperty(REQUEST_PROPERTY_FILTER_REUSED))) {
            return;
        }
        // When request is made to authorization server, don't ask for access token! It will result in endless loop!
        if (LOGIN_PATH.equals(requestContext.getUri().getPath())) {
            return;
        }

        if (accessToken == null) {
           System.out.println("getting acces token...");
           try {
               accessToken = accessTokenResolver.resolve();
           } catch (ApiException e){
               throw new IOException(e);
           }
        }

        // Add access token as header.
        requestContext.getHeaders().putSingle(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    }

    @Override
    public void filter(final ClientRequestContext requestContext, final ClientResponseContext responseContext)
            throws IOException {
        if ("true".equals(requestContext.getProperty(REQUEST_PROPERTY_FILTER_REUSED))) {
            return;
        }
        // The server asked for authentication ? (status 401)
        if (responseContext.getStatus() != Response.Status.UNAUTHORIZED.getStatusCode()) {
            // Not 401 status : no authentication issue
            return;
        }

        // When request is made to authorization server, don't ask for access token! It will result in endless loop!
        if (LOGIN_PATH.equals(requestContext.getUri().getPath())) {
            return;
        }

        System.out.println("access token is expired or invalid. get new access token...");

        try {
            accessToken = accessTokenResolver.resolve();
        } catch (ApiException e){
            new IOException(e);
        }

        repeatRequest(requestContext, responseContext, accessToken);
    }

    /**
     * Does all the hard work to repeat request one more time with request context data
     * 
     * Idea taken from HttpAuthenticationFilter of org.glassfish.jersey.security.oauth1-client
     * 
     * @param request ClientRequestContext which was used
     * @param response ClientResponseContext which was received after first unauthorized request
     * @param newAuthorizationHeader new authorization header received from server
     */
    static void repeatRequest(final ClientRequestContext request, final ClientResponseContext response,
            final String newAuthorizationHeader) {
        final Client client = ClientBuilder.newClient(request.getConfiguration());
        final String method = request.getMethod();
        final MediaType mediaType = request.getMediaType();
        final URI lUri = request.getUri();

        final WebTarget resourceTarget = client.target(lUri);

        final Invocation.Builder builder = resourceTarget.request(mediaType);

        final MultivaluedMap<String, Object> newHeaders = new MultivaluedHashMap<String, Object>();

        for (final Map.Entry<String, List<Object>> entry : request.getHeaders().entrySet()) {
            if (HttpHeaders.AUTHORIZATION.equals(entry.getKey())) {
                continue;
            }
            newHeaders.put(entry.getKey(), entry.getValue());
        }

        newHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + newAuthorizationHeader);
        builder.headers(newHeaders);

        builder.property(REQUEST_PROPERTY_FILTER_REUSED, "true");

        Invocation invocation;
        if (request.getEntity() == null) {
            invocation = builder.build(method);
        } else {
            invocation = builder.build(method, Entity.entity(request.getEntity(), request.getMediaType()));
        }
        final Response nextResponse = invocation.invoke();

        if (nextResponse.hasEntity()) {
            response.setEntityStream(nextResponse.readEntity(InputStream.class));
        }
        final MultivaluedMap<String, String> headers = response.getHeaders();
        headers.clear();
        headers.putAll(nextResponse.getStringHeaders());
        response.setStatus(nextResponse.getStatus());
    }
}
