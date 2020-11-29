package nl.wjglerum;

import io.quarkus.security.identity.SecurityIdentity;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class ExampleResource {

    private static final Logger LOGGER = Logger.getLogger(ExampleResource.class);

    @Inject
    SecurityIdentity identity;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOGGER.infof("Principal: %s", identity.getPrincipal().getName());
        return "hello";
    }

    @GET
    @Path("/api")
    @Produces(MediaType.TEXT_PLAIN)
    public String api() {
        LOGGER.infof("Principal: %s", identity.getPrincipal().getName());
        return "api";
    }
}
