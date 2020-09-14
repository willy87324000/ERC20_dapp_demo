package dapp.service;

import dapp.service.dto.CoinbaseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import dapp.solidityWrapper.*;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.util.Arrays;

@Slf4j
@Service
public class SimpleERC20Service {
    @Value("${cht.blockchain.ip}")
    public String _rcpURL;
    @Value("${cht.blockchain.erc_contract.addr}")
    String contractAddress;

    BlockChainService blockChainService;
    Web3j web3j;
    ContractGasProvider contractGasProvider = new DefaultGasProvider();
    TransactionManager transactionManager;
    SimpleERC20 simpleERC20Contract;
    Credentials credentials;

    public SimpleERC20Service(BlockChainService blockChainService) {
        this.blockChainService = blockChainService;
        try {
            this.web3j = this.blockChainService.getWebJInstance();
        } catch (Exception e) {
            log.error("Fetch web3j instance error");
        }
        this.credentials = blockChainService.getCredentialsFromPrivateKey();
        this.transactionManager = new RawTransactionManager(this.web3j, this.credentials);
    }

    @PostConstruct
    private void init() {
        // Initialize the ERC20Contract
        try {
            this.simpleERC20Contract = SimpleERC20.load(
                contractAddress,
                web3j,
                this.transactionManager,
                contractGasProvider
            );
        } catch (Exception e) {
            log.error("Load Contract error");
        }
    }

    /**
     * Get Contract Name
     * @return
     */
    public String getContractName() {
        String contractName = null;
        try {
            contractName = this.simpleERC20Contract.name().send();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return contractName;
    }

    /**
     * Get Coinbase
     */
    public CoinbaseDTO getCoinbase() throws Exception{
        EthCoinbase coinbase = null;
        // 取得coinbase
        try {
            coinbase = web3j.ethCoinbase().sendAsync().get();
        } catch (Exception e) {
            log.error("Get coinbase error");
            throw e;
        }
        // 取得token balance
        BigInteger tokenBalance = null;
        try {
            tokenBalance = this.simpleERC20Contract.balanceOf(coinbase.getAddress()).send();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        CoinbaseDTO coinbaseDTO = new CoinbaseDTO();
        coinbaseDTO.setAddress(coinbase.getAddress());
        coinbaseDTO.setTokenBalance(tokenBalance);
        coinbaseDTO.setBalance(this.blockChainService.getBalanceEther(web3j, coinbase.getAddress()));

        return coinbaseDTO;
    }

    /**
     * Get Account ERC20 Token Balance
     * @return
     */
    public BigInteger getAccountBalance(String account) {
        BigInteger balance = null;
        try {
            balance = this.simpleERC20Contract.balanceOf(account).send();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return balance;
    }

    /**
     * Transfer ERC20 token from own account
     */
    public TransactionReceipt transfer(
        String recipient,
        BigInteger amount) throws Exception {
        TransactionReceipt txRecipt = null;
        try {
            txRecipt = this.simpleERC20Contract.transfer(recipient, amount).send();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return txRecipt;
    }

    /**
     * Transfer ERC20 token from own account (using raw send transaction)
     */
    public TransactionReceipt transferByRawTransaction(
        String recipient,
        BigInteger amount) throws Exception {
        TransactionReceipt txRecipt = null;
        try {
            String functionName = "transfer";
            txRecipt = this.blockChainService.sendRawTransaction(
                "transfer",
                Arrays.<Type>asList(
                    new org.web3j.abi.datatypes.Address(recipient),
                    new org.web3j.abi.datatypes.Uint(amount)),
                this.contractAddress);

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
        return txRecipt;
    }
}
