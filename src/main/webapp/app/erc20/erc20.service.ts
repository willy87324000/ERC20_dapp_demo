import { Injectable } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CoinbaseDTO, IMessage, Message, TransferReqDTO } from 'app/erc20/erc20.model';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';

@Injectable({
  providedIn: 'root',
})
export class ERC20Service {
  public loading = false;
  public sending = false;
  public inputForm: FormGroup;
  public checkBalanceForm: FormGroup;
  public balance = 0;
  public coinbase: CoinbaseDTO;
  public message: IMessage = new Message(); // alert 格式

  private apiUrl = SERVER_API_URL + 'api';

  constructor(private fb: FormBuilder, public http: HttpClient) {
    this.coinbase = new CoinbaseDTO();
    this.inputForm = this.fb.group({
      recipient: [''],
      amount: [0],
    });
    this.checkBalanceForm = this.fb.group({
      account: [''],
    });
  }

  /**
   * 取得Coinbase資訊
   */
  getCoinbaseInfo(): void {
    this.loading = true;
    this.getResource('coinbaseInfo').subscribe(({ code, data }) => {
      if (code && code === '0') {
        this.coinbase = data;
        this.loading = false;
      }
    });
  }

  /**
   * 呼叫transfer
   */
  transfer(): void {
    this.sending = true;

    const dto = new TransferReqDTO();
    dto.recipient = this.inputForm.get('recipient')?.value;
    dto.amount = this.inputForm.get('amount')?.value;

    this.postResource('transfer', dto).subscribe(({ code, data }) => {
      if (code && code === '0') {
        this._setMessage('success', '發送成功! Tx Hash:' + data.transactionHash);
        this.sending = false;
        this.resetForm();
        this.getCoinbaseInfo();
      }
    });
  }

  /**
   * 取得ERC20餘額
   */
  getBalance(): void {
    this.loading = true;

    const account = this.checkBalanceForm.get('account')?.value;

    this.getResource('balance', account).subscribe(({ code, data }) => {
      if (code && code === '0') {
        this._setMessage('success', '查詢成功!');
        this.balance = data;
        this.loading = false;
        this.resetForm();
      }
    });
  }

  /**
   * TODO: 目前只有先接收一個params，可在改良為接收多個
   * 封裝呼叫GET method API，統一做錯誤處理
   * @param { string } type url
   * @param { T } params 呼叫API時帶的參數
   */
  getResource<T>(type: string, account?: string): Observable<any> {
    let paramsDTO = null;
    if (account) {
      paramsDTO = new HttpParams().set('account', account);
    }
    const requestOptions: Object = {
      headers: new HttpHeaders().append('Content-Type', 'application/json'),
      responseType: 'json',
      params: paramsDTO,
    };

    return this.http.get(this.apiUrl + '/' + type, requestOptions).pipe(catchError(this._handleError<any>(type)));
  }

  /**
   * 封裝呼叫POST method API，統一做錯誤處理
   * @param { string } type url
   * @param { T } params 呼叫API時帶的參數
   */
  postResource<T>(type: string, params?: T): Observable<any> {
    return this.http.post(this.apiUrl + '/' + type, params).pipe(catchError(this._handleError<any>(type)));
  }

  /**
   * 設定alert訊息
   * @param type alert的樣式 danger:紅 success:綠 info:黃
   * @param msg alert中的訊息
   * @param pos alert的位置 table:列表上方 modal:彈窗裡面
   */
  private _setMessage(type: string, msg: string): void {
    this.message = { type, msg };
  }

  /**
   * 呼叫API錯誤訊息處理
   * @param operation 錯誤的API uri
   * @param result API回傳訊息
   */
  _handleError<T>(operation = 'operation', result?: T): any {
    return (res: any): Observable<T> => {
      let msg;
      !res.error || !res.error.title ? (msg = '發生非預期錯誤，請重新整理或稍後再試 !') : (msg = res.error.title);
      this._setMessage('danger', '呼叫API:' + operation + ' ' + msg);

      this.loading = false;
      this.sending = false;
      return of(result as T);
    };
  }

  /**
   * 重置訊息 alert
   */
  removeMassage(): void {
    this.message = new Message();
  }

  /**
   * 重製表單
   */
  resetForm(): void {
    this.inputForm.reset();
  }
}
