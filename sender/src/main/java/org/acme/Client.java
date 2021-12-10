package org.acme;

public class Client {

    public String id;
    public String sendTime;

    /**
     * Default constructor required for Jackson serializer
     */
    public Client() {
    }

    public Client(String id, String sendTime) {
        this.id = id;
        this.sendTime = sendTime;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + ", sendTime=" + sendTime + "]";
    }

}
