package in.codingstreams.etexpenseservice.controller;


import in.codingstreams.etexpenseservice.constant.LoggingConstants;
import in.codingstreams.etexpenseservice.controller.dto.ExpCatRequest;
import in.codingstreams.etexpenseservice.controller.data.ExpenseCategory;
import in.codingstreams.etexpenseservice.service.expcat.ExpCatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/categories")
@Slf4j
@RequiredArgsConstructor
public class ExpCatController {
    private final ExpCatService expCatService;

    @PostMapping("/")
    public ResponseEntity<ExpenseCategory> createExpCat(@PathVariable String userId, @RequestBody ExpCatRequest expCatRequest) {
        String methodName = "ExpCatController:createExpCat";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expCatRequest);

        var expenseCategory = expCatService.createExpCat(userId, expCatRequest.getName());

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(expenseCategory);
    }


    @GetMapping("/{expCatId}")
    public ResponseEntity<ExpenseCategory> getExpCatById(@PathVariable String userId, @PathVariable String expCatId) {
        String methodName = "ExpCatController:getExpCatById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expCatId);

        var expenseCategory = expCatService.getExpCatById(userId, expCatId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(expenseCategory);
    }


    @GetMapping("/")
    public ResponseEntity<List<ExpenseCategory>> listExpCatByUserId(@PathVariable String userId) {
        String methodName = "ExpCatController:listExpCatByUserId";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var expenseCategories = expCatService.listExpCatByUserId(userId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expenseCategories);
    }


    @PutMapping("/{expCatId}")
    public ResponseEntity<ExpenseCategory> updateExpCat(@PathVariable String userId, @PathVariable String expCatId , @RequestBody ExpCatRequest expCatRequest) {
        String methodName = "ExpCatController:updateExpCat";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expCatId);

        var expenseCategory = expCatService.updateExpCat(userId, expCatId, expCatRequest.getName());

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(expenseCategory);
    }


    @DeleteMapping("/{expCatId}")
    public ResponseEntity<Void> deleteExpCatById(@PathVariable String userId, @PathVariable String expCatId) {
        String methodName = "ExpCatController:deleteExpCatById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expCatId);

        expCatService.deleteExpCatById(userId, expCatId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}




















