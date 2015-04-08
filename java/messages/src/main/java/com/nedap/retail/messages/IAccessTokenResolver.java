package com.nedap.retail.messages;

import com.sun.jersey.api.client.ClientHandlerException;

public interface IAccessTokenResolver {

    /**
     * Login with Nedap OAuth 2.0 Authorization Server.
     * 
     * @return OAuth 2.0 access token
     * @throws ClientException - if the status of the HTTP response is greater than or equal to 300 and c is not the
     *             type ClientResponse.
     * @throws ClientHandlerException - if the client handler fails to process the request or response.
     */
    public String resolve();
}
