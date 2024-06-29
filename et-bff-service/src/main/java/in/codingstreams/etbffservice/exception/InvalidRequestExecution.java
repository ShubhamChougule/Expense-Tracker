package in.codingstreams.etbffservice.exception;


import lombok.Getter;

@Getter
public class InvalidRequestExecution extends RuntimeException {
    private String errorCode;

    public InvalidRequestExecution(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
