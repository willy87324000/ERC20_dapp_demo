package dapp.service.dto;

import java.math.BigInteger;

public class TransferRequestDTO {
    String recipient;
    BigInteger amount;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }
}
