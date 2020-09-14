package dapp.service.dto;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import dapp.type.StatusCode;

public class ResponseDTO<T> implements Serializable {

    private static final long serialVersionUID = 8888L;

    @Getter
    @Setter
    private String msg;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
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

    private void setStatusCode(StatusCode statusCode) {
        if (statusCode != null) {
            this.msg = statusCode.getMsg();
            this.code = statusCode.getCode();
        }
    }
}
