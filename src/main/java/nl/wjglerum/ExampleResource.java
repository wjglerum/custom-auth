package nl.wjglerum;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

@Path("/")
public class ExampleResource {

    private static final Logger LOGGER = Logger.getLogger(ExampleResource.class);

    @Context
    SecurityContext context;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOGGER.info(context.getUserPrincipal().getName());
        LOGGER.info(context.getAuthenticationScheme());
        LOGGER.info(context.isSecure());
        LOGGER.info(context.isUserInRole("admin"));
        return "hello";
    }
}
