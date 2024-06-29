package in.codingstreams.etbffservice.controller.dto;


import in.codingstreams.etbffservice.constant.ErrorMessages;
import in.codingstreams.etbffservice.exception.InvalidRequestExecution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetails {
    // TODO : add type of update
    private String name;
    private String email;
    public void isValid() {
        if(StringUtils.isEmpty(email) && StringUtils.isEmpty(name)) {
            throw new InvalidRequestExecution(ErrorMessages.INVALID_UPDATE_USER_REQUEST.getErrorMessage(),
                    ErrorMessages.INVALID_UPDATE_USER_REQUEST.getErrorCode());
        }
    }
}
