package nl.wjglerum.security;

import io.quarkus.security.credential.Credential;

public class APIKeyCredential implements Credential {

    private final String apiKey;

    public APIKeyCredential(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }
}
