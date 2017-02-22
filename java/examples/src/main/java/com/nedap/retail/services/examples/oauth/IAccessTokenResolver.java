package com.nedap.retail.services.examples.oauth;

import com.nedap.retail.client.ApiException;


public interface IAccessTokenResolver {

    /**
     * Login with Nedap OAuth 2.0 Authorization Server.
     * 
     * @return OAuth 2.0 access token
     * @throws ApiException - if the status of the HTTP response is greater than or equal to 300 and c is not the
     *             type ClientResponse.
     */
    String resolve() throws ApiException;
}
