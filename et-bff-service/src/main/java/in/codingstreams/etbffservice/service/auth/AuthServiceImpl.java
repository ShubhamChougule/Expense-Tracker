package in.codingstreams.etbffservice.service.auth;

import in.codingstreams.etbffservice.constant.LoggingConstants;
import in.codingstreams.etbffservice.controller.dto.AuthResponse;
import in.codingstreams.etbffservice.controller.dto.LoginRequest;
import in.codingstreams.etbffservice.controller.dto.SignUpRequest;
import in.codingstreams.etbffservice.controller.dto.VerifyTokenRequest;
import in.codingstreams.etbffservice.service.external.AuthServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthServiceFeignClient authServiceFeignClient;
    @Override
    public AuthResponse signUp(SignUpRequest signUpRequest) {

        var methodName = "AuthServiceImpl:signUp";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, signUpRequest);

        var responseEntity = authServiceFeignClient.signUp(signUpRequest);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return responseEntity.getBody();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        var methodName = "AuthServiceImpl:login";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, loginRequest);

        var responseEntity = authServiceFeignClient.login(loginRequest);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return responseEntity.getBody();
    }

    @Override
    public String verifyToken(String accessToken) {
        var methodName = "AuthServiceImpl:verifyToken";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, accessToken);

        if(accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }

        var responseEntity = authServiceFeignClient.verifyToken(VerifyTokenRequest.builder().accessToken(accessToken).build());

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return Objects.requireNonNull(responseEntity.getBody()).getUserId();
    }
}
