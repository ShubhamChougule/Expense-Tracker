package in.codingstreams.etbffservice.service.auth;

import in.codingstreams.etbffservice.controller.dto.AuthResponse;
import in.codingstreams.etbffservice.controller.dto.LoginRequest;
import in.codingstreams.etbffservice.controller.dto.SignUpRequest;

public interface AuthService {
    AuthResponse signUp(SignUpRequest signUpRequest);

    AuthResponse login(LoginRequest loginRequest);

    String verifyToken(String accessToken);
}
