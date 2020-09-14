package dapp.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class CoinbaseDTO {
    String address;
    BigDecimal balance;
    BigInteger tokenBalance;
}
