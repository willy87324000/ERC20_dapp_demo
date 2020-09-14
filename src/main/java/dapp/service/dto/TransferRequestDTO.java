package dapp.service.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequestDTO {
    String recipient;
    BigInteger amount;
}
