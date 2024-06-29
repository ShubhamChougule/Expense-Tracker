package in.codingstreams.etexpenseservice.service.expense;

import in.codingstreams.etexpenseservice.constant.ErrorMessage;
import in.codingstreams.etexpenseservice.constant.LoggingConstants;
import in.codingstreams.etexpenseservice.controller.data.Expense;
import in.codingstreams.etexpenseservice.controller.data.ExpenseCategory;
import in.codingstreams.etexpenseservice.controller.dto.ExpRequest;
import in.codingstreams.etexpenseservice.exception.ExpCatAlreadyExistsException;
import in.codingstreams.etexpenseservice.exception.ExpCatNotFoundException;
import in.codingstreams.etexpenseservice.repo.ExpCatRepo;
import in.codingstreams.etexpenseservice.repo.ExpRepo;
import in.codingstreams.etexpenseservice.service.expcat.ExpCatService;
import in.codingstreams.etexpenseservice.service.expense.model.ExpenseData;
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

    private final ExpRepo expRepo;
    private final ExpCatService expCatService;


    @Override
    public Expense createExpense(String userId, ExpenseData data) {
        String methodName = "ExpServiceImpl:createExpense";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, data);

        // find exp  by id
        var expenseCategory = expCatService.getExpCatById(userId, data.getExpCatId());


        // create expense
        Expense expense = Expense.builder()
                .title(data.getTitle())
                .userId(userId)
                .expCat(expenseCategory)
                .amount(data.getAmount())
                .build();


        // save expense category

        var saveExp = expRepo.save(expense);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return saveExp;
    }

    @Override
    public Expense getExpById(String userId, String expId) {
        String methodName = "ExpServiceImpl:getExpById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expId);

        // find if exists or not
        var expense = expRepo.findByExpIdAndUserId(expId, userId)
                        .orElseThrow(() -> new ExpCatNotFoundException(ErrorMessage.EXP_NOT_FOUND.getErrorCode(),
                                ErrorMessage.EXP_NOT_FOUND.getErrorMessage()));


        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);
        return expense;
    }

    @Override
    public Page<Expense> listExpByUserId(String userId, Pageable pageable, String startDate, String endDate) {
        String methodName = "ExpServiceImpl:listExpByUserId";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        Page<Expense> expenses;

        if(startDate != null && endDate != null) {
            var dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            expenses = expRepo.findAllByUserIdAndCreatedAtBetween(userId,
                    LocalDate.parse(startDate, dateTimeFormat).atStartOfDay(),
                    LocalDate.parse(endDate, dateTimeFormat).plusDays(1).atStartOfDay(), pageable);
        } else {
            expenses = expRepo.findAllByUserId(userId, pageable);
        }


        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return expenses;
    }

    @Override
    public Expense updateExpense(String userId, String expId, ExpRequest expRequest) {
        String methodName = "ExpServiceImpl:updateExpense";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expRequest);

        var expense = getExpById(userId, expId);

        var expenseCategory = expCatService.getExpCatById(userId, expRequest.getExpCatId());

        expense.setAmount(expRequest.getAmount());
        expense.setTitle(expRequest.getTitle());
        expense.setExpCat(expenseCategory);

        var savedExp = expRepo.save(expense);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return savedExp;
    }

    @Override
    public void deleteExpById(String userId, String expId) {
        String methodName = "ExpServiceImpl:deleteExpById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expId);

        expRepo.deleteByExpIdAndUserId(expId, userId);


        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);
    }


}
