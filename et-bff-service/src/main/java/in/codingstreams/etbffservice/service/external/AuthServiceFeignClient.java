package in.codingstreams.etbffservice.service.external;

import in.codingstreams.etbffservice.controller.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${services.et-uas.name}", url = "${services.et-uas.url}")
public interface AuthServiceFeignClient {


    // http://localhost:8081/api/

    @PostMapping("/auth/sign-up")
    ResponseEntity<AuthResponse> signUp(@RequestBody SignUpRequest signUpRequest);


    @PostMapping("/auth/login")
    ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest);

    @PostMapping("/auth/verify-token")
    ResponseEntity<VerifyTokenResponse> verifyToken(VerifyTokenRequest accessToken);

    @GetMapping("/users/{userId}")
    ResponseEntity<UserInfo> getUserInfo(@PathVariable String userId);

    @PostMapping("/users/{userId}/change-password")
    ResponseEntity<Void> changePassword(@PathVariable String userId, @RequestBody ChangePasswordRequest changePasswordRequest);

    @PostMapping("/users/{userId}")
    ResponseEntity<UserInfo> updateUserDetails(@PathVariable String userId, @RequestBody UserDetails userDetails);
}
