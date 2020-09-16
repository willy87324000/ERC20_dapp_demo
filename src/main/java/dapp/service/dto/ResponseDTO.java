package dapp.service.dto;


import java.io.Serializable;

import dapp.type.StatusCode;

public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 8888L;

    private String msg;

    private String code;

    public T data;

    public ResponseDTO() {
        this.setStatusCode(StatusCode.NeedInit);
    }

    public ResponseDTO(StatusCode statusCode) {
        this.setStatusCode(statusCode);
    }

    public ResponseDTO(T data, StatusCode statusCode) {
        this.data = data;
        this.setStatusCode(statusCode);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setStatusCode(StatusCode statusCode) {
        if (statusCode != null) {
            this.msg = statusCode.getMsg();
            this.code = statusCode.getCode();
        }
    }
}
