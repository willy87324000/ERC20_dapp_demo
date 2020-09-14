package dapp.type;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatusCode {
    Success("0", "Success"),

    NeedInit("-1", "No Status Code"),

    Failure("-2", "Failure");

    @Getter
    private final String code;

    @Getter
    private final String msg;

    public static StatusCode toStatusCode(String code) {
        for (StatusCode tmp : StatusCode.values()) {
            if (tmp.getCode().equals(code)) {
                return tmp;
            }
        }
        return null;
    }
}
