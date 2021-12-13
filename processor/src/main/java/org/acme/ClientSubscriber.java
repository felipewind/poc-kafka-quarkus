package org.acme;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import javax.enterprise.context.ApplicationScoped;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.smallrye.reactive.messaging.annotations.Blocking;

@ApplicationScoped
public class ClientSubscriber {

    private final Logger LOG = LoggerFactory.getLogger(ClientSubscriber.class);

    @Channel("extraction-responses")
    Emitter<ClientInvestment> clientInvestmentEmmiter;

    @ConfigProperty(name = "app.process.delay.ms")
    int appProcessDelayMs;

    @Incoming("extraction-requests")
    @Blocking(ordered = false, value = "my-custom-pool")
    public void read(Client client) throws JsonProcessingException, InterruptedException {

        LOG.info("read() " + client + " delay=[" + appProcessDelayMs + "]");

        if ("333".equals(client.id)) {
            throw new RuntimeException("Error 333");
        }

        Thread.sleep(appProcessDelayMs);

        int random = ThreadLocalRandom.current().nextInt(1, 3000);

        var clientInvestment = new ClientInvestment(client.id, LocalDateTime.now().toString(), random);

        LOG.info("Before send reply message " + clientInvestment);

        clientInvestmentEmmiter.send(clientInvestment);

    }

}
