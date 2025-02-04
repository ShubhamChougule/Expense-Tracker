package in.codingstreams.etuserauthservice.service.user;

import in.codingstreams.etuserauthservice.data.model.AppUser;

public interface UserService {
    AppUser getUserInfo(String userId);

    void changePassword(String userId, String oldPassword, String newPassword);

    AppUser updateEmail(String userId, String email);

    AppUser updateName(String userId, String name);
}
