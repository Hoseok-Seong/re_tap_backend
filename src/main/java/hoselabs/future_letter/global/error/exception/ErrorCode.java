package hoselabs.future_letter.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {
    // COMMON
    INVALID_INPUT_VALUE(400, "C001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", "Method Not Allowed"),
    DATA_NOT_FOUND(400, "C003", "Data Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", "Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    IO_EXCEPTION(500, "C007", "IO Exception"),
    API_EXCEPTION(500, "C008", "API Exception"),

    // JWT
    INVALID_ACCESS_TOKEN_VERIFICATION_FAILED(401, "J001", "Access Token Verification Failed"),
    INVALID_ACCESS_TOKEN_ACCESS_DENIED(401, "J002", "Access Token Access Denied"),
    ACCESS_TOKEN_EXPIRED(401, "J003", "Access Token Expired"),
    INVALID_REFRESH_TOKEN(401, "J004", "Invalid Refresh Token"),
    REFRESH_TOKEN_EXPIRED(401, "J005", "Refresh Token Expired"),

    // USER
    SIGNIN_PASSWORD_MISMATCH(400, "U001", "Signin Password Mismatch"),
    USER_DUPLICATION(400, "U002", "User is Duplication"),
    USER_ALREADY_EXISTS(409, "U003", "User is Already Exists"),

    // LETTER
    LETTER_NOT_ARRIVED(403, "L001", "Letter is not arrived"),

    // SECURITY
    USER_DETAILS_NULL(500, "S001", "UserDetails is Null");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }
}
