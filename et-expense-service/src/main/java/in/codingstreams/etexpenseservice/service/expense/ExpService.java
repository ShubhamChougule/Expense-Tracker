package in.codingstreams.etexpenseservice.service.expense;

import in.codingstreams.etexpenseservice.controller.data.Expense;
import in.codingstreams.etexpenseservice.controller.data.ExpenseCategory;
import in.codingstreams.etexpenseservice.controller.dto.ExpRequest;
import in.codingstreams.etexpenseservice.service.expense.model.ExpenseData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpService {
    Expense createExpense(String userId, ExpenseData expenseData);

    Expense getExpById(String userId, String expId);

    Page<Expense> listExpByUserId(String userId, Pageable pageable, String startDate, String endDate);

    Expense updateExpense(String userId, String expId, ExpRequest expRequest);

    void deleteExpById(String userId, String expId);
}
