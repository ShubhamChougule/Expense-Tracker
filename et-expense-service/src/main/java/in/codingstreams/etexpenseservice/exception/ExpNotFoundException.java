package in.codingstreams.etexpenseservice.exception;

import lombok.Getter;

@Getter

public class ExpNotFoundException extends RuntimeException {
    String errorCode;
    public ExpNotFoundException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
