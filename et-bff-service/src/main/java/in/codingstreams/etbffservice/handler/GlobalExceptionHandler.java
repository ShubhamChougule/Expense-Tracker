package in.codingstreams.etbffservice.handler;


import in.codingstreams.etbffservice.controller.dto.ErrorResponse;
import in.codingstreams.etbffservice.exception.ApiException;
import in.codingstreams.etbffservice.exception.InvalidRequestExecution;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestExecution.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestExecution(InvalidRequestExecution e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .errorCode(e.getErrorCode())
                                .errorMessage(e.getMessage()).build()
                );
    }


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        return ResponseEntity
                .status(HttpStatus.valueOf(e.getStatusCode()))
                .body(
                        ErrorResponse.builder()
                                .errorCode(e.getErrorCode())
                                .errorMessage(e.getMessage()).build()
                );
    }
}
