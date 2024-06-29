package in.codingstreams.etbffservice.service.external;

import in.codingstreams.etbffservice.controller.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "${services.et-es.name}", url = "${services.et-es.url}")
public interface ExpenseServiceFeignClient {


    // http://localhost:8082/api/

    @PostMapping("/users/{userId}/categories/")
    ResponseEntity<ExpenseCategory> createExpCat(@PathVariable String userId, @RequestBody ExpCatRequest expCatRequest) ;


    @GetMapping("/users/{userId}/categories/{expCatId}")
    ResponseEntity<ExpenseCategory> getExpCatById(@PathVariable String userId, @PathVariable String expCatId) ;

    @GetMapping("/users/{userId}/categories/")
    ResponseEntity<List<ExpenseCategory>> listExpCatByUserId(@PathVariable String userId) ;


    @PutMapping("/users/{userId}/categories/{expCatId}")
    ResponseEntity<ExpenseCategory> updateExpCat(@PathVariable String userId, @PathVariable String expCatId , @RequestBody ExpCatRequest expCatRequest);


    // TODO
    @DeleteMapping("/users/{userId}/categories/{expCatId}")
    ResponseEntity<Void> deleteExpCatById(@PathVariable String userId, @PathVariable String expCatId) ;




    // expense methods

    @PostMapping("/users/{userId}/expenses/")
     ResponseEntity<Expense> createExpense(@PathVariable String userId, @RequestBody ExpenseData expenseData) ;


    @GetMapping("/users/{userId}/expenses/{expId}")
     ResponseEntity<Expense> getExpById(@PathVariable String userId, @PathVariable String expId) ;

    @GetMapping("/users/{userId}/expenses/")
     ResponseEntity<Page<Expense>> listExpByUserId(@PathVariable String userId, Pageable pageable,
                                                   @RequestParam(required = false) String startDate,
                                                   @RequestParam(required = false) String endDate) ;



    @PutMapping("/users/{userId}/expenses/{expId}")
     ResponseEntity<Expense> updateExpense(@PathVariable String userId, @PathVariable String expId , @RequestBody ExpenseData expenseData);


    @DeleteMapping("/users/{userId}/expenses/{expId}")
     ResponseEntity<Void> deleteExpById(@PathVariable String userId, @PathVariable String expId);
}
