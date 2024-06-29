package in.codingstreams.etbffservice.service.expense;


import in.codingstreams.etbffservice.controller.dto.Expense;
import in.codingstreams.etbffservice.controller.dto.ExpenseData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpService {
    Expense createExpense(String userId, ExpenseData expenseData);

    Expense getExpById(String userId, String expId);

    Page<Expense> listExpByUserId(String userId, Pageable pageable, String startDate, String endDate);

    Expense updateExpense(String userId, String expId, ExpenseData expRequest);

    void deleteExpById(String userId, String expId);
}
