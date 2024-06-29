package in.codingstreams.etexpenseservice.controller;
import in.codingstreams.etexpenseservice.constant.LoggingConstants;
import in.codingstreams.etexpenseservice.controller.data.Expense;
import in.codingstreams.etexpenseservice.controller.dto.ExpCatRequest;
import in.codingstreams.etexpenseservice.controller.dto.ExpRequest;
import in.codingstreams.etexpenseservice.controller.mapper.ExpRequestMapper;
import in.codingstreams.etexpenseservice.service.expense.ExpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/users/{userId}/expenses")
@Slf4j
@RequiredArgsConstructor
public class ExpController {
    private final ExpService expService;

    @PostMapping("/")
    public ResponseEntity<Expense> createExpense(@PathVariable String userId, @RequestBody ExpRequest expRequest) {
        String methodName = "ExpController:createExpense";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expRequest);

        var expense = expService.createExpense(userId, ExpRequestMapper.INSTANCE.mapToExpenseData(expRequest));

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(expense);
    }


    @GetMapping("/{expId}")
    public ResponseEntity<Expense> getExpById(@PathVariable String userId, @PathVariable String expId) {
        String methodName = "ExpController:getExpById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expId);

        var expense = expService.getExpById(userId, expId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(expense);
    }

    @GetMapping("/")
    public ResponseEntity<Page<Expense>> listExpByUserId(@PathVariable String userId, Pageable pageable,
                                                         @RequestParam(required = false) String startDate,
                                                         @RequestParam(required = false) String endDate) {
        String methodName = "ExpController:listExpByUserId";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var expenses = expService.listExpByUserId(userId, pageable, startDate, endDate);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expenses);
    }



    @PutMapping("/{expId}")
    public ResponseEntity<Expense> updateExpense(@PathVariable String userId, @PathVariable String expId , @RequestBody ExpRequest expRequest) {
        String methodName = "ExpController:updateExpense";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expId);

        var expense = expService.updateExpense(userId, expId, expRequest);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expense);
    }


    @DeleteMapping("/{expId}")
    public ResponseEntity<Void> deleteExpById(@PathVariable String userId, @PathVariable String expId) {
        String methodName = "ExpController:deleteExpById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expId);

        expService.deleteExpById(userId, expId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}




















