package in.codingstreams.etbffservice.controller.dto;

import in.codingstreams.etbffservice.constant.ErrorMessages;
import in.codingstreams.etbffservice.exception.InvalidRequestExecution;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseData {
    private String title;
    private Double amount;
    private String expCatId;

    public void isValid() {
        if(StringUtils.isEmpty(title) || amount <= 0 || StringUtils.isEmpty(expCatId)) {
            throw new InvalidRequestExecution(ErrorMessages.INVALID_EXPENSE_REQUEST.getErrorMessage(),
                    ErrorMessages.INVALID_EXPENSE_REQUEST.getErrorCode());
        }
    }
}
