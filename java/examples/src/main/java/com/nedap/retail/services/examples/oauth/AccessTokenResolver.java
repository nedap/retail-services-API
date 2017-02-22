package com.nedap.retail.services.examples.oauth;

import com.nedap.retail.client.ApiClient;
import com.nedap.retail.client.ApiException;
import com.nedap.retail.client.api.AccessTokenResolverApi;

public class AccessTokenResolver implements IAccessTokenResolver {

    private final String clientId;
    private final String secret;
    private final String accessTokenUri;
    private final AccessTokenResolverApi api;

    /**
     * @param accessTokenUri The URL to use to obtain an OAuth2 access token
     * @param clientId       The client identifier to use for this protected resource
     * @param secret         The client secret
     */
    public AccessTokenResolver(final String accessTokenUri, final String clientId, final String secret) {
        this.accessTokenUri = accessTokenUri;
        this.clientId = clientId;
        this.secret = secret;
        final ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(accessTokenUri);
        System.out.println(accessTokenUri);
        this.api = new AccessTokenResolverApi(apiClient);
    }

    @Override
    public String resolve() throws ApiException {
        System.out.println("getting OAuth 2.0 access token: " + clientId);
        return api.resolve("client_credentials", clientId, secret).getAccessToken();
    }
}
