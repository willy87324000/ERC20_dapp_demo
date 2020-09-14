/**
 * 取得Coinbase Information DTO Interface
 */
export interface ICoinbaseDTO {
  address?: string;
  balance?: number;
  tokenBalance?: number;
}

/**
 * 取得Coinbase Information DTO
 */
export class CoinbaseDTO implements ICoinbaseDTO {
  constructor(public address = '', public balance = 0, public tokenBalance = 0) {}
}

/**
 * 發送代幣DTO interface
 */
export interface ITransferReqDTO {
  recipient?: string;
  amount?: number;
}

/**
 * 發送代幣 DTO
 */
export class TransferReqDTO implements ITransferReqDTO {
  constructor(public recipient = '', public amount = 0) {}
}

/**
 * alert 資訊
 */
export interface IMessage {
  type: string;
  msg: string;
}
export class Message implements IMessage {
  constructor(public type = '', public msg = '') {}
}
