package in.codingstreams.etbffservice.service.user;

import in.codingstreams.etbffservice.controller.dto.*;


public interface UserService {

    UserInfo getUserInfo(String userId);

    void changePassword(String userId, ChangePasswordRequest changePasswordRequest);

    UserInfo updateUserDetails(String userId, UserDetails userDetails);
}
