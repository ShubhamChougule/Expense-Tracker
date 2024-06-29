package in.codingstreams.etbffservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum ErrorMessages {

    INVALID_SIGN_UP_REQUEST("A400", "Invalid sign-up request!"),
    INVALID_CHANGE_PWD_REQUEST("P400", "Invalid change password request!"),
    INVALID_UPDATE_USER_REQUEST("U400", "Invalid user update request!"),
    INVALID_EXPENSE_REQUEST("E400", "Invalid expense request!"),
    INVALID_DATE_RANGE("E400", "Invalid date range!"),
    INVALID_LOGIN_REQUEST("A400", "Invalid login request!");

    String errorCode;
    String errorMessage;
}
