package in.codingstreams.etuserauthservice.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails {
    private String name;
    private String email;
    private UserUpdateRequest requestType;
}
