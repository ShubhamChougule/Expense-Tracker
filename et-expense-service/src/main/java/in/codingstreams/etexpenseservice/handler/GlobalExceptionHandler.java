package in.codingstreams.etexpenseservice.handler;


import in.codingstreams.etexpenseservice.controller.dto.ErrorResponse;
import in.codingstreams.etexpenseservice.exception.ExpCatAlreadyExistsException;
import in.codingstreams.etexpenseservice.exception.ExpCatNotFoundException;
import in.codingstreams.etexpenseservice.exception.ExpNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpCatAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleExpCatAlreadyExistsException(ExpCatAlreadyExistsException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.builder()
                                .errorCode(e.getErrorCode())
                                .errorMessage(e.getMessage()).build()
                );
    }


    @ExceptionHandler(ExpCatNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExpCatNotFoundException(ExpCatNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .errorCode(e.getErrorCode())
                                .errorMessage(e.getMessage()).build()
                );
    }


    @ExceptionHandler(ExpNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleExpNotFoundException(ExpNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .errorCode(e.getErrorCode())
                                .errorMessage(e.getMessage()).build()
                );
    }
}
