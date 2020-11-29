package nl.wjglerum.security;

import io.quarkus.security.identity.request.AuthenticationRequest;

public class APIKeyAuthenticationRequest implements AuthenticationRequest {

    private final APIKeyCredential credential;

    public APIKeyAuthenticationRequest(APIKeyCredential credential) {
        this.credential = credential;
    }

    public APIKeyCredential getCredential() {
        return credential;
    }
}
