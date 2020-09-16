package dapp.web.rest.custom;

import dapp.service.dto.CoinbaseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import dapp.service.SimpleERC20Service;
import dapp.service.dto.ResponseDTO;
import dapp.service.dto.TransferRequestDTO;
import dapp.type.StatusCode;

@RestController
@RequestMapping("/api")
public class WalletResource {
    Logger log = LoggerFactory.getLogger(SimpleERC20Service.class);

    SimpleERC20Service simpleERC20Service;

    WalletResource(SimpleERC20Service simpleERC20Service) {
        this.simpleERC20Service = simpleERC20Service;
    }

    @RequestMapping(value="/erc20TokenName", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getERC20ContractName() {
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setMsg("Get ERC20 Name success");
        responseDTO.setCode(StatusCode.Success.getCode());
        responseDTO.setData(simpleERC20Service.getContractName());

        return ResponseEntity.ok(responseDTO);
    }

    @RequestMapping(value="/balance", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getBalance(@RequestParam(value="account") String account) {
        ResponseDTO responseDTO = new ResponseDTO();

        responseDTO.setMsg("Get ERC20 Balance success");
        responseDTO.setCode(StatusCode.Success.getCode());
        responseDTO.setData(simpleERC20Service.getAccountBalance(account));

        return ResponseEntity.ok(responseDTO);
    }

    @RequestMapping(value="/transfer", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        TransactionReceipt txReceipt;
        try {
            txReceipt = simpleERC20Service.transfer(
                transferRequestDTO.getRecipient(),
                transferRequestDTO.getAmount()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDTO.setMsg("Call ERC20 transfer() error:" + e.getMessage());
            responseDTO.setCode(StatusCode.Failure.getCode());
            return ResponseEntity.ok(responseDTO);
        }

        responseDTO.setMsg(StatusCode.Success.getMsg());
        responseDTO.setCode(StatusCode.Success.getCode());
        responseDTO.setData(txReceipt);
        return ResponseEntity.ok(responseDTO);
    }

    @RequestMapping(value="/rawTransfer", method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> rawTransfer(@RequestBody TransferRequestDTO transferRequestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        TransactionReceipt txReceipt;
        try {
            txReceipt = simpleERC20Service.transferByRawTransaction(
                transferRequestDTO.getRecipient(),
                transferRequestDTO.getAmount()
            );
        } catch (Exception e) {
            log.error(e.getMessage());
            responseDTO.setMsg("Call ERC20 transfer() error:" + e.getMessage());
            responseDTO.setCode(StatusCode.Failure.getCode());
            return ResponseEntity.ok(responseDTO);
        }

        responseDTO.setMsg(StatusCode.Success.getMsg());
        responseDTO.setCode(StatusCode.Success.getCode());
        responseDTO.setData(txReceipt);
        return ResponseEntity.ok(responseDTO);
    }

    @RequestMapping(value="/coinbaseInfo", method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getCoinbaseInfo() {
        ResponseDTO responseDTO = new ResponseDTO();

        CoinbaseDTO coinbaseDTO = null;
        try {
            coinbaseDTO = this.simpleERC20Service.getCoinbase();
        } catch (Exception e) {
            log.error("Get Coinbase Infomation Error");
            responseDTO.setCode(StatusCode.Failure.getCode());
            responseDTO.setMsg(StatusCode.Failure.getMsg());
            return ResponseEntity.ok(responseDTO);
        }
        responseDTO.setMsg(StatusCode.Success.getMsg());
        responseDTO.setCode(StatusCode.Success.getCode());
        responseDTO.setData(coinbaseDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
