package in.codingstreams.etuserauthservice.controller;


import in.codingstreams.etuserauthservice.constant.LoggingConstants;
import in.codingstreams.etuserauthservice.controller.dto.ChangePasswordRequest;
import in.codingstreams.etuserauthservice.controller.dto.UserDetails;
import in.codingstreams.etuserauthservice.controller.dto.UserInfo;
import in.codingstreams.etuserauthservice.controller.mapper.AuthRequestMapper;
import in.codingstreams.etuserauthservice.controller.mapper.UserInfoMapper;
import in.codingstreams.etuserauthservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Get user Info
    @GetMapping("/{userId}")
    ResponseEntity<UserInfo> getUserInfo(@PathVariable String userId) {
        var methodName = "UserController:getUserInfo";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);


        var appUser = userService.getUserInfo(userId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        UserInfoMapper.INSTANCE.mapToUserInfo(appUser)
                );
    }

    // change password


    @PostMapping("/{userId}/change-password")
    ResponseEntity<Void> changePassword(@PathVariable String userId, @RequestBody ChangePasswordRequest changePasswordRequest) {
        var methodName = "UserController:changePassword";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);


        userService.changePassword(userId, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity.ok().build();
    }




    // update user details


    @PostMapping("/{userId}")
    ResponseEntity<UserInfo> updateUserDetails(@PathVariable String userId, @RequestBody UserDetails userDetails) {
        var methodName = "UserController:updateUserDetails";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);


        var appUser =  switch (userDetails.getRequestType()) {
            case UPDATE_EMAIL -> userService.updateEmail(userId, userDetails.getEmail());
            case UPDATE_NAME -> userService.updateName(userId, userDetails.getName());
        };

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserInfoMapper.INSTANCE.mapToUserInfo(appUser));
    }
}
