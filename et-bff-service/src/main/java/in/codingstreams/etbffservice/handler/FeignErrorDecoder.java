package in.codingstreams.etbffservice.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import in.codingstreams.etbffservice.constant.LoggingConstants;
import in.codingstreams.etbffservice.controller.dto.ErrorResponse;
import in.codingstreams.etbffservice.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
//        methodKey = FeignClientName:method


        log.error(LoggingConstants.ERROR_METHOD_LOG, "FeignErrorDecoder:decode", "Error from feign client");

        var errorResponse = Optional.ofNullable(response.body())
                .map(body -> {
                    try {
                        return new String(body.asInputStream().readAllBytes());
                    } catch (IOException e) {
                        throw null;
                    }
                })
                .map(json -> {
                    try {
                        return new ObjectMapper().readValue(json, ErrorResponse.class);
                    } catch (JsonProcessingException e) {
                        throw null;
                    }
                }).orElse(
                        ErrorResponse.builder()
                                .errorMessage("Something went wrong")
                                .errorCode("500")
                                .build()
                );


        return new ApiException(
                errorResponse.getErrorMessage(),
                response.status(),
                errorResponse.getErrorCode()
        );
    }
}
