package org.acme;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ClientDeserializer extends ObjectMapperDeserializer<Client> {

    public ClientDeserializer() {
        super(Client.class);
    }

}
