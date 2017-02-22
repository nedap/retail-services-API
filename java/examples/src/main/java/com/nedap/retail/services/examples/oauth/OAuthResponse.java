package com.nedap.retail.services.examples.oauth;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class OAuthResponse {

    private String access_token;
    private String token_type;
    private int expires_in;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(final String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(final String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(final int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(final String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "OAuthResponse [accessToken=" + access_token + ", tokenType=" + token_type + ", expiresIn=" + expires_in
                + ", scope=" + scope;
    }
}
