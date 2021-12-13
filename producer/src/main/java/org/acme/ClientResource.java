package org.acme;

import java.time.LocalDateTime;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@Path("/extracions")
public class ClientResource {

    private final Logger LOG = LoggerFactory.getLogger(ClientResource.class);

    @Channel("extraction-requests")
    Emitter<Client> clientEmmiter;

    @GET
    @Path("/request/{begin}/{end}")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(
            @Parameter(example = "1") @PathParam("begin") int begin,
            @Parameter(example = "5") @PathParam("end") int end) {

        LOG.info("get() begin=[" + begin + "] end=[" + end + "]");

        for (int i = begin; i <= end; i++) {
            var client = new Client(i + "", LocalDateTime.now().toString());

            LOG.info("Before send message " + client);

            clientEmmiter.send(client);
        }

        return "Messages sent to Kafka ";
    }
}