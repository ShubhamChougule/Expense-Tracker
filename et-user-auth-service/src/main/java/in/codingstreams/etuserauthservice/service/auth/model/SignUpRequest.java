package in.codingstreams.etuserauthservice.service.auth.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SignUpRequest {
    private String name;
    private String email;
    private String password;
}
