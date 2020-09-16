package dapp.service.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CoinbaseDTO {
    String address;
    BigDecimal balance;
    BigInteger tokenBalance;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigInteger getTokenBalance() {
        return tokenBalance;
    }

    public void setTokenBalance(BigInteger tokenBalance) {
        this.tokenBalance = tokenBalance;
    }
}
