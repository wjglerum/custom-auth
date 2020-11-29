package nl.wjglerum.security;

import java.security.Principal;

public class APIKeyPrincipal implements Principal {

    private final APIKeyUser user;

    public APIKeyPrincipal(APIKeyUser user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getName();
    }
}
