package nl.wjglerum.security;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.arc.AlternativePriority;
import io.quarkus.oidc.runtime.OidcAuthenticationMechanism;
import io.quarkus.security.identity.IdentityProviderManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.identity.request.AuthenticationRequest;
import io.quarkus.vertx.http.runtime.security.ChallengeData;
import io.quarkus.vertx.http.runtime.security.HttpAuthenticationMechanism;
import io.quarkus.vertx.http.runtime.security.HttpCredentialTransport;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@AlternativePriority(1)
@ApplicationScoped
public class APIKeyHttpAuthenticationMechanism implements HttpAuthenticationMechanism {

    protected static final String AUTH_HEADER_NAME = "X-API-KEY";
    protected static final String API_PATH = "/api";

    private static final Logger LOGGER = Logger.getLogger(APIKeyHttpAuthenticationMechanism.class);

    @Inject
    OidcAuthenticationMechanism delegate;

    @Override
    public Uni<SecurityIdentity> authenticate(RoutingContext context, IdentityProviderManager identityProviderManager) {
        LOGGER.infof("Context path: %s", context.normalisedPath());
        if(context.normalisedPath().startsWith(API_PATH)) {
            String token = context.request().getHeader(AUTH_HEADER_NAME);
            LOGGER.infof("Token: %s", token);
            if (token != null) {
                APIKeyCredential credential = new APIKeyCredential(token);
                return identityProviderManager.authenticate(new APIKeyAuthenticationRequest(credential));
            } else {
                return Uni.createFrom().optional(Optional.empty());
            }
        } else {
            return delegate.authenticate(context, identityProviderManager);
        }
    }

    @Override
    public Uni<ChallengeData> getChallenge(RoutingContext context) {
        if(context.normalisedPath().startsWith(API_PATH)) {
            ChallengeData result = new ChallengeData(
                    HttpResponseStatus.UNAUTHORIZED.code(),
                    HttpHeaderNames.WWW_AUTHENTICATE,
                    AUTH_HEADER_NAME);
            return Uni.createFrom().item(result);
        } else {
            return delegate.getChallenge(context);
        }
    }

    @Override
    public Set<Class<? extends AuthenticationRequest>> getCredentialTypes() {
        return Collections.singleton(APIKeyAuthenticationRequest.class);
    }

    @Override
    public HttpCredentialTransport getCredentialTransport() {
        return new HttpCredentialTransport(HttpCredentialTransport.Type.OTHER_HEADER, AUTH_HEADER_NAME);
    }
}
