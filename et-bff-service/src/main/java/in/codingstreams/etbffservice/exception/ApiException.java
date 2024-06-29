package in.codingstreams.etbffservice.exception;


import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private int statusCode;
    private String errorCode;

    public ApiException(String message, int statusCode, String errorCode) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }
}
