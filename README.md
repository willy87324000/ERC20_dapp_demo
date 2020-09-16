# ERC20_dapp_demo

本專案演示如何使用 Web3J 介接 ERC20 智能合約，並進行 token 移轉與查詢的 Dapp

需要先運行測試用區塊鏈，以及部屬智能合約

## 運行測試用區塊鏈 (使用 Ganache-Cli)

下載 Ganache Cli
https://www.trufflesuite.com/docs/ganache/quickstart

若確認測試區塊鏈運行成功，且 port 是監聽 7545 即可

## 部屬智能合約

至 `/truffle` 資料夾中，執行 `truffle migrate`，即可部屬智能合約

## 設定後端私鑰與 ERC20 合約地址

於 `/src/main/resources/config/application-dev.yml` 中設置區塊鏈資訊以及私鑰資訊
(因測試開發用，不考慮私鑰安全問題)

```
# application:
cht:
  blockchain:
    ip: 'http:/localhost:7545'
    erc_contract:
      addr: '<ERC 20合約地址>'
    account:
      privateKey: '<coinbase私鑰>'
```

## 啟動後端服務 (Java/Spring)

預設是會連接 7545 port 的區塊鏈，後端會監聽 8080 port

Windows: mvwn
Linux/Macos: ./mvwn

## 啟動前端服務 (Angular/Webpack)

執行 `npm start`

前端會監聽 9090 port

## 參考

如果要在 Ubuntu 建置本範例，可參考下面連結:

[Ubuntu 環境設置 ERC20 範例說明
](https://hackmd.io/6_fjYnyZT4Szu0BGsqb4Ug?view)
