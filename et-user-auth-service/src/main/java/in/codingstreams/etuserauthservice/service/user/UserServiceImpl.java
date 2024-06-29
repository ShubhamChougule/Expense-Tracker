package in.codingstreams.etuserauthservice.service.user;

import in.codingstreams.etuserauthservice.constant.ErrorMessages;
import in.codingstreams.etuserauthservice.constant.LoggingConstants;
import in.codingstreams.etuserauthservice.data.model.AppUser;
import in.codingstreams.etuserauthservice.data.repo.AppUserRepo;
import in.codingstreams.etuserauthservice.exception.BadCredentialsException;
import in.codingstreams.etuserauthservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser getUserInfo(String userId) {
        var methodName = "UserServiceImpl:getUserInfo";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var appUser = appUserRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.USER_NOT_FOUND.getErrorCode(),
                        ErrorMessages.USER_NOT_FOUND.getErrorMessage()));

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return appUser;
    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {
        var methodName = "UserServiceImpl:changePassword";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var appUser = getUserInfo(userId);

        if(!passwordEncoder.matches(oldPassword, appUser.getPassword())) {
            throw  new BadCredentialsException(ErrorMessages.USER_BAD_CREDENTIALS.getErrorMessage(),
                    ErrorMessages.USER_BAD_CREDENTIALS.getErrorCode());
        }

        appUser.setPassword(passwordEncoder.encode(newPassword));

        appUserRepo.save(appUser);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);
    }

    @Override
    public AppUser updateEmail(String userId, String email) {
        var methodName = "UserServiceImpl:updateEmail";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var appUser = getUserInfo(userId);

        appUser.setEmail(email);
        appUserRepo.save(appUser);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return appUser;

    }

    @Override
    public AppUser updateName(String userId, String name) {
        var methodName = "UserServiceImpl:updateName";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);
        var appUser = getUserInfo(userId);

        appUser.setName(name);
        appUserRepo.save(appUser);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return appUser;
    }


}
