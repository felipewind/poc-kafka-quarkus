package org.acme;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ClientInvestmentSubscriber {

    private final Logger LOG = LoggerFactory.getLogger(ClientInvestmentSubscriber.class);

    @Incoming("extraction-responses")
    public void read(ClientInvestment clientInvestment) {
        LOG.info("read() " + clientInvestment);
    }

}
