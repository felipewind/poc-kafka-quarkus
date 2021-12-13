package org.acme;

public class ClientInvestment {

    public String clientId;
    public String investmentSendTime;
    public int amount;

    /**
     * Default constructor required for Jackson serializer
     */
    public ClientInvestment() {
    }

    public ClientInvestment(String clientId, String investmentSendTime, int amount) {
        this.clientId = clientId;
        this.investmentSendTime = investmentSendTime;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ClientInvestment [clientId=" + clientId + ", amount=" + amount + ", investmentSendTime="
                + investmentSendTime + "]";
    }

}
