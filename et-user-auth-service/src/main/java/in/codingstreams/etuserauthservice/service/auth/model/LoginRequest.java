package in.codingstreams.etuserauthservice.service.auth.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LoginRequest {
    private String email;
    private String password;
}
