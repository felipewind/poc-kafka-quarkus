package org.acme;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.reactive.messaging.kafka.api.OutgoingKafkaRecordMetadata;

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

            var message = Message.of(client)
                    .addMetadata(
                            OutgoingKafkaRecordMetadata.<String>builder()
                                    .withKey(client.id).build())
                    .withAck(() -> {
                        LOG.info("Send ack - " + client);
                        return CompletableFuture.completedFuture(null);
                    })
                    .withNack(throwable -> {
                        LOG.info("Send nack - " + client + throwable);
                        return CompletableFuture.completedFuture(null);
                    });

            // LOG.info("Sending message. Key=["
            //         + message.getMetadata(OutgoingKafkaRecordMetadata.class).orElseThrow().getKey() +
            //         "] Payload=" + client);

            clientEmmiter.send(message);

        }

        return "Messages sent to Kafka ";
    }
}