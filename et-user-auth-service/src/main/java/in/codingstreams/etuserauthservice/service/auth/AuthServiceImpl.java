package in.codingstreams.etuserauthservice.service.auth;

import in.codingstreams.etuserauthservice.constant.ErrorMessages;
import in.codingstreams.etuserauthservice.constant.LoggingConstants;
import in.codingstreams.etuserauthservice.data.model.AppUser;
import in.codingstreams.etuserauthservice.data.repo.AppUserRepo;
import in.codingstreams.etuserauthservice.exception.BadCredentialsException;
import in.codingstreams.etuserauthservice.exception.InvalidTokenException;
import in.codingstreams.etuserauthservice.exception.UserAlreadyExistsException;
import in.codingstreams.etuserauthservice.exception.UserNotFoundException;
import in.codingstreams.etuserauthservice.service.auth.model.LoginRequest;
import in.codingstreams.etuserauthservice.service.auth.model.SignUpRequest;
import in.codingstreams.etuserauthservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signUp(SignUpRequest signUpRequest) {
        var methodName = "AuthServiceImpl:signUp";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, signUpRequest);


        if(appUserRepo.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistsException(
                    ErrorMessages.USER_ALREADY_EXISTS.getErrorMessage(),
                    ErrorMessages.USER_ALREADY_EXISTS.getErrorCode());
        }

        var appUser = AppUser.builder()
                        .name(signUpRequest.getName())
                                .email(signUpRequest.getEmail())
                                        .password(passwordEncoder.encode(signUpRequest.getPassword())).build();

        appUserRepo.save(appUser);

        var accessToken = JwtUtils.generateAccessToken(signUpRequest.getEmail());


        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return accessToken;
    }

    @Override
    public String login(LoginRequest loginRequest) {
        var methodName = "AuthServiceImpl:signUp";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, loginRequest);


        var appUser = appUserRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> {
                    return new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getErrorCode(),
                            ErrorMessages.USER_NOT_FOUND.getErrorMessage());
                });



        if(!passwordEncoder.matches(loginRequest.getPassword(), appUser.getPassword())) {
            throw  new BadCredentialsException(ErrorMessages.USER_BAD_CREDENTIALS.getErrorCode(),
                    ErrorMessages.USER_BAD_CREDENTIALS.getErrorMessage());
        }


        var accessToken = JwtUtils.generateAccessToken(loginRequest.getEmail());


        return accessToken;


    }

    @Override
    public String verifyToken(String accessToken) {
        var methodName = "AuthServiceImpl:verifyToken";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, accessToken);


        var userName = JwtUtils.getUsernameFromToken(accessToken)
                .orElseThrow(() -> {
                    log.error(LoggingConstants.ERROR_METHOD_LOG, methodName, "Invalid token");

                    return new InvalidTokenException(
                            ErrorMessages.INVALID_ACCESS_TOKEN.getErrorMessage(),
                            ErrorMessages.INVALID_ACCESS_TOKEN.getErrorCode()
                    );
                });

        var appUser = appUserRepo.findByEmail(userName)
                .orElseThrow(() -> {
                    return new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getErrorCode(),
                            ErrorMessages.USER_NOT_FOUND.getErrorMessage());
                });


        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return appUser.getUserId();
    }


}
