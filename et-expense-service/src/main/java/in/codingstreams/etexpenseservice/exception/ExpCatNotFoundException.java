package in.codingstreams.etexpenseservice.exception;

import lombok.Getter;

@Getter

public class ExpCatNotFoundException extends RuntimeException {
    String errorCode;
    public ExpCatNotFoundException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
