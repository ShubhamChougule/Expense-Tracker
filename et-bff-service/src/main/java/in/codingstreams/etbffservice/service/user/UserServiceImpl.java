package in.codingstreams.etbffservice.service.user;

import in.codingstreams.etbffservice.constant.LoggingConstants;
import in.codingstreams.etbffservice.controller.dto.ChangePasswordRequest;
import in.codingstreams.etbffservice.controller.dto.UserDetails;
import in.codingstreams.etbffservice.controller.dto.UserInfo;
import in.codingstreams.etbffservice.service.external.AuthServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthServiceFeignClient authServiceFeignClient;

    @Override
    public UserInfo getUserInfo(String userId) {
        var methodName = "UserServiceImpl:getUserInfo";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var responseEntity = authServiceFeignClient.getUserInfo(userId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return responseEntity.getBody();
    }

    @Override
    public void changePassword(String userId, ChangePasswordRequest changePasswordRequest) {
        var methodName = "UserServiceImpl:changePassword";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        authServiceFeignClient.changePassword(userId, changePasswordRequest);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);
    }

    @Override
    public UserInfo updateUserDetails(String userId, UserDetails userDetails) {

        var methodName = "UserServiceImpl:updateUserDetails";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var responseEntity = authServiceFeignClient.updateUserDetails(userId, userDetails);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return responseEntity.getBody();
    }
}
