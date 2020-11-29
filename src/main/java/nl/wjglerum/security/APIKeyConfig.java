package nl.wjglerum.security;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "nl.wjglerum.api.keys")
public class APIKeyConfig {
    public String admin;
    public String user;
}
