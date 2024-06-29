package in.codingstreams.etexpenseservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
    EXP_CAT_ALREADY_EXISTS("EC409", "Expense Category already exists!"),
    EXP_CAT_NOT_FOUND("EC404", "Expense Category not found!"),
    EXP_NOT_FOUND("EC404", "Expense not found!");
    private String errorCode;
    private String errorMessage;
}
