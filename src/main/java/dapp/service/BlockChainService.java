package dapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;
import org.web3j.utils.Convert;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class BlockChainService {

    @Value("${cht.blockchain.ip}")
    public String _rcpURL;
    @Value("${cht.blockchain.account.privateKey}")
    public String _privateKey;

    public TransactionReceipt sendRawTransaction(
        String functionName,
        List inputParams,
        String contractAddress
    ) throws Exception {
        Function function = new Function(
            functionName, // Function name
            inputParams, // Function input parameters
            Collections.emptyList()); // Function returned parameters

        //Encode function values in transaction data format
        String txData = FunctionEncoder.encode(function);

        // RawTransactionManager use a wallet (credential) to create and sign transaction
        TransactionManager txManager = new RawTransactionManager(this.getWebJInstance(), this.getCredentialsFromPrivateKey());

        // Send transaction
        String txHash = txManager.sendTransaction(
            DefaultGasProvider.GAS_PRICE,
            DefaultGasProvider.GAS_LIMIT,
            contractAddress,
            txData,
            BigInteger.ZERO).getTransactionHash();

        // Wait for transaction to be mined
        TransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor(
            this.getWebJInstance(),
            TransactionManager.DEFAULT_POLLING_FREQUENCY,
            TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
        TransactionReceipt txReceipt = receiptProcessor.waitForTransactionReceipt(txHash);

        return txReceipt;
    }

    public Web3j getWebJInstance() throws Exception {
        Web3j web3j = null;
        try {
            web3j = Web3j.build(new HttpService(this._rcpURL));
            // this will cause the exception if web3j is not connected
            log.info("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
        } catch (Exception e) {
            log.error("WEB3-Connection Error", e);
            throw e;
        }
        return web3j;
    }

    /**
     * Returns the balance (in Ether) of the specified account address.
     */
    public BigDecimal getBalanceEther(Web3j web3j, String address) throws InterruptedException, ExecutionException {
        return weiToEther(getBalanceWei(web3j, address));
    }

    /**
     * Returns the balance (in Wei) of the specified account address.
     */
    public BigInteger getBalanceWei(Web3j web3j, String address) throws InterruptedException, ExecutionException {
        EthGetBalance balance = web3j
            .ethGetBalance(address, DefaultBlockParameterName.LATEST)
            .sendAsync()
            .get();

        return balance.getBalance();
    }

    /**
     * Converts the provided Wei amount (smallest value Unit) to Ethers.
     */
    public static BigDecimal weiToEther(BigInteger wei) {
        return Convert.fromWei(wei.toString(), Convert.Unit.ETHER);
    }

    public static BigInteger etherToWei(BigDecimal ether) {
        return Convert.toWei(ether, Convert.Unit.ETHER).toBigInteger();
    }

    public Credentials getCredentialsFromPrivateKey() {
        return Credentials.create(this._privateKey);
    }
}
