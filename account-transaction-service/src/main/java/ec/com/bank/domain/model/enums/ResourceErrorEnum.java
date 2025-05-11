package ec.com.bank.domain.model.enums;

public enum ResourceErrorEnum {

    ACCOUNT_NOT_FOUND("100","Account no fond with id: %s."),
    TRANSACION_NOT_FOUND("200","Transaction no fond with id: %s.");

    private final String code;
    private final String message;

    ResourceErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
