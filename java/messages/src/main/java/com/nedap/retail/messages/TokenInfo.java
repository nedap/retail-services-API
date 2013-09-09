package com.nedap.retail.messages;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * OAuth 2.0 access token information
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) //  to prevent writing of null properties
public class TokenInfo implements Serializable {

    private String clientId;
    private Set<String> clientScope;
    private String username;
    private Set<String> userAuthorities;

    public TokenInfo() {
    }

    public TokenInfo(String clientId) {
        this(clientId, Collections.EMPTY_SET);
    }

    public TokenInfo(String clientId, Set<String> clientScope) {
        this(clientId, clientScope, null, null);
    }

    public TokenInfo(String clientId, Set<String> clientScope, String username, Set<String> userAuthorities) {
        this.clientId = clientId;
        this.clientScope = clientScope;
        this.username = username;
        this.userAuthorities = userAuthorities;
    }

    /**
     * Identifies the client application that is making the request.
     *
     * @return OAuth 2.0 client ID (also called 'originID' in SignedRequest).
     */
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Scopes is a set of case-sensitive strings defined by the authorization server.
     * Clients can request scopes, and resource owners can authorize them.
     *
     * @return set of case-sensitive strings.
     */
    public Set<String> getClientScope() {
        return clientScope;
    }

    public void setClientScope(Set<String> clientScope) {
        this.clientScope = clientScope;
    }

    /**
     * @return Returns username. Can be null in case no user context exists.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return Set of user roles (case-sensitive). Can be null in case no user context exists.
     */
    public Set<String> getUserAuthorities() {
        return userAuthorities;
    }

    public void setUserAuthorities(Set<String> userAuthorities) {
        this.userAuthorities = userAuthorities;
    }
}
