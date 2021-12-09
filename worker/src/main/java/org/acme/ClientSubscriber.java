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

@ApplicationScoped
public class ClientSubscriber {

    private final Logger LOG = LoggerFactory.getLogger(ClientSubscriber.class);

    @Channel("extraction-responses")
    Emitter<ClientInvestment> clientInvestmentEmmiter;

    @ConfigProperty(name = "app.process.delay")
    int appProcessDelay;

    @Incoming("extraction-requests")
    public void read(Client client) throws JsonProcessingException, InterruptedException {

        LOG.info("read() " + client + " delay=[" + appProcessDelay + "]");

        Thread.sleep(appProcessDelay);

        int random = ThreadLocalRandom.current().nextInt(1, 3000);

        var clientInvestment = new ClientInvestment(client.id, LocalDateTime.now().toString(), random);

        LOG.info("Before send reply message " + clientInvestment);

        clientInvestmentEmmiter.send(clientInvestment);

    }

}
