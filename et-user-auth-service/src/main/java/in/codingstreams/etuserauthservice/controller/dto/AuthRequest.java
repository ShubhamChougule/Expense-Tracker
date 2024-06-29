package in.codingstreams.etuserauthservice.controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequest {
    private String name;
    private String email;
    private String password;

}
