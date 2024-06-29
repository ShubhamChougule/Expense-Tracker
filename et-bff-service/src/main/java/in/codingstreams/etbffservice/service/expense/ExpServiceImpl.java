package in.codingstreams.etbffservice.service.expense;


import in.codingstreams.etbffservice.constant.LoggingConstants;
import in.codingstreams.etbffservice.controller.dto.Expense;
import in.codingstreams.etbffservice.controller.dto.ExpenseData;
import in.codingstreams.etbffservice.service.external.ExpenseServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpServiceImpl implements ExpService {


    private final ExpenseServiceFeignClient expenseServiceFeignClient;

    @Override
    public Expense createExpense(String userId, ExpenseData data) {
        String methodName = "ExpServiceImpl:createExpense";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, data);

        var expense = expenseServiceFeignClient.createExpense(userId, data).getBody();

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return expense;
    }

    @Override
    public Expense getExpById(String userId, String expId) {
        String methodName = "ExpServiceImpl:getExpById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var expense = expenseServiceFeignClient.getExpById(userId, expId).getBody();

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return expense;
    }

    @Override
    public Page<Expense> listExpByUserId(String userId, Pageable pageable, String startDate, String endDate) {
        String methodName = "ExpServiceImpl:listExpByUserId";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var expense = expenseServiceFeignClient.listExpByUserId(userId, pageable, startDate, endDate).getBody();

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return expense;
    }

    @Override
    public Expense updateExpense(String userId, String expId, ExpenseData expenseData) {
        String methodName = "ExpServiceImpl:updateExpense";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var expense = expenseServiceFeignClient.updateExpense(userId, expId, expenseData).getBody();

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return expense;
    }

    @Override
    public void deleteExpById(String userId, String expId) {
        String methodName = "ExpServiceImpl:deleteExpById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expId);

        expenseServiceFeignClient.deleteExpById(userId, expId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);
    }


}
