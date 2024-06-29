package in.codingstreams.etexpenseservice.exception;

import lombok.Getter;

@Getter

public class ExpCatAlreadyExistsException extends RuntimeException {
    String errorCode;
    public ExpCatAlreadyExistsException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
    }
}
