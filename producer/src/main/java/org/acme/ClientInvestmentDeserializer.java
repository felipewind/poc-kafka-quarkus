package org.acme;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ClientInvestmentDeserializer extends ObjectMapperDeserializer<ClientInvestment> {

    public ClientInvestmentDeserializer() {
        super(ClientInvestment.class);
    }

}
