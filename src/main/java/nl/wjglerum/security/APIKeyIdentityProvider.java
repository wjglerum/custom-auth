package nl.wjglerum.security;

import io.quarkus.security.identity.AuthenticationRequestContext;
import io.quarkus.security.identity.IdentityProvider;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;
import io.smallrye.mutiny.Uni;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class APIKeyIdentityProvider implements IdentityProvider<APIKeyAuthenticationRequest> {

    private static final Logger LOGGER = Logger.getLogger(APIKeyIdentityProvider.class);

    @Inject
    APIKeyConfig config;

    @Override
    public Class<APIKeyAuthenticationRequest> getRequestType() {
        LOGGER.infof("AuthenticationRequest: %s", APIKeyAuthenticationRequest.class.getName());
        return APIKeyAuthenticationRequest.class;
    }

    @Override
    public Uni<SecurityIdentity> authenticate(APIKeyAuthenticationRequest request, AuthenticationRequestContext context) {
        APIKeyCredential credential = request.getCredential();
        LOGGER.infof("Credential: %s", credential.getApiKey());
        if (config.admin.equals(credential.getApiKey())) {
            return Uni.createFrom().item(QuarkusSecurityIdentity.builder()
                    .setPrincipal(new APIKeyPrincipal(APIKeyUser.ADMIN))
                    .addCredential(credential)
                    .build());
        } else if (config.user.equals(credential.getApiKey())) {
            return Uni.createFrom().item(QuarkusSecurityIdentity.builder()
                    .setPrincipal(new APIKeyPrincipal(APIKeyUser.USER))
                    .addCredential(credential)
                    .build());
        } else {
            return Uni.createFrom().optional(Optional.empty());
        }
    }
}
