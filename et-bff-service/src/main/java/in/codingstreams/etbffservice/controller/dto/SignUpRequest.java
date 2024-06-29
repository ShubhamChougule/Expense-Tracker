package in.codingstreams.etbffservice.controller.dto;

import in.codingstreams.etbffservice.constant.ErrorMessages;
import in.codingstreams.etbffservice.exception.InvalidRequestExecution;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class SignUpRequest {
    private String name;
    private String email;
    private String password;


    public void isValid() {
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new InvalidRequestExecution(ErrorMessages.INVALID_SIGN_UP_REQUEST.getErrorMessage(),
                    ErrorMessages.INVALID_SIGN_UP_REQUEST.getErrorCode());
        }
    }
}
