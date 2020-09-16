package dapp.type;

public enum StatusCode {
    Success("0", "Success"),

    NeedInit("-1", "No Status Code"),

    Failure("-2", "Failure");

    StatusCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final String code;

    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static StatusCode toStatusCode(String code) {
        for (StatusCode tmp : StatusCode.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }
}
