package dapp.service.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class ERC20LogDTO {
    String txHash;
    String blockHash;
    BigInteger blockNumber;
    String contractAddr;
    String sender;
    String receiver;
    BigInteger amount;
}
