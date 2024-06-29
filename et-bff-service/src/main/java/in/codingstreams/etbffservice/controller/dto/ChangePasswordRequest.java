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
public class ChangePasswordRequest {
    private String oldPassword;
    private String newPassword;

    public void isValid() {
        if(StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
            throw new InvalidRequestExecution(ErrorMessages.INVALID_CHANGE_PWD_REQUEST.getErrorMessage(),
                    ErrorMessages.INVALID_CHANGE_PWD_REQUEST.getErrorCode());
        }
    }
}
