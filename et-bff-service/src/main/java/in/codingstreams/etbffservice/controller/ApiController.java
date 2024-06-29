package in.codingstreams.etbffservice.controller;


import in.codingstreams.etbffservice.constant.ErrorMessages;
import in.codingstreams.etbffservice.constant.LoggingConstants;
import in.codingstreams.etbffservice.controller.dto.*;
import in.codingstreams.etbffservice.controller.mapper.ExpCatMapper;
import in.codingstreams.etbffservice.controller.mapper.ExpMapper;
import in.codingstreams.etbffservice.controller.mapper.UserInfoMapper;
import in.codingstreams.etbffservice.exception.InvalidRequestExecution;
import in.codingstreams.etbffservice.service.auth.AuthService;
import in.codingstreams.etbffservice.service.expcat.ExpCatService;
import in.codingstreams.etbffservice.service.expense.ExpService;
import in.codingstreams.etbffservice.service.reports.ReportsService;
import in.codingstreams.etbffservice.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin("http://localhost:5173/")
public class ApiController {
    private final AuthService authService;
    private final UserService userService;
    private final ExpCatService expCatService;
    private final ExpService expService;
    private final ReportsService reportsService;

    // signup
    @PostMapping("/sign-up")
    ResponseEntity<AuthResponse> signup(
            @RequestBody SignUpRequest signUpRequest
    ) {
        var methodName = "ApiController:signup";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, signUpRequest);

        signUpRequest.isValid();

        var authResponse = authService.signUp(signUpRequest);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authResponse);
    }



    // login
    @PostMapping("/login")
    ResponseEntity<AuthResponse> login(
            @RequestBody LoginRequest loginRequest
    ) {
        var methodName = "ApiController:login";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, loginRequest);

        loginRequest.isValid();

        var authResponse = authService.login(loginRequest);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authResponse);
    }


    // getUserinfo
    @GetMapping("/users/info")
    ResponseEntity<UserInfoResponse> getUserInfo(
            @RequestHeader("Authorization") String accessToken
    ) {
        var methodName = "ApiController:getUserInfo";
        log.info(LoggingConstants.START_METHOD_LOG, methodName);


        var userId = authService.verifyToken(accessToken);
        var userInfo = userService.getUserInfo(userId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserInfoMapper.INSTANCE.mapToUserInfoResponse(userInfo));
    }


//     change password
    @PostMapping("/users/change-password")
    ResponseEntity<Void> changePassword( @RequestHeader("Authorization") String accessToken,
                                         @RequestBody ChangePasswordRequest changePasswordRequest) {

        var methodName = "ApiController:changePassword";
        log.info(LoggingConstants.START_METHOD_LOG, methodName);


        var userId = authService.verifyToken(accessToken);
        userService.changePassword(userId, changePasswordRequest);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();

    }



    // update user
    @PostMapping("/users/")
    ResponseEntity<UserInfo> updateUserDetails( @RequestHeader("Authorization") String accessToken,
                                         @RequestBody UserDetails userDetails) {

        var methodName = "ApiController:updateUserDetails";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userDetails);


        var userId = authService.verifyToken(accessToken);
        var userInfo = userService.updateUserDetails(userId, userDetails);

        log.info(LoggingConstants.END_METHOD_LOG, methodName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userInfo);

    }




    // expense categories

    @PostMapping("/categories")
    public ResponseEntity<ExpCatResponse> createExpCat(@RequestHeader("Authorization") String accessToken, @RequestBody ExpCatRequest expCatRequest) {
        String methodName = "ApiController:createExpCat";
        log.info(LoggingConstants.START_METHOD_LOG, methodName,expCatRequest);

        var userId = authService.verifyToken(accessToken);

        var expenseCategory = expCatService.createExpCat(userId, expCatRequest);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ExpCatMapper.INSTANCE.mapToExpCatResponse(expenseCategory));
    }


    @GetMapping("/categories/{expCatId}")
    public ResponseEntity<ExpCatResponse> getExpCatById(@RequestHeader("Authorization") String accessToken, @PathVariable String expCatId) {
        String methodName = "ApiController:getExpCatById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, expCatId);

        var userId = authService.verifyToken(accessToken);

        var expenseCategory = expCatService.getExpCatById(userId, expCatId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ExpCatMapper.INSTANCE.mapToExpCatResponse(expenseCategory));
    }


    @GetMapping("/categories")
    public ResponseEntity<List<ExpCatResponse>> listExpCatByUserId(@RequestHeader("Authorization") String accessToken) {
        String methodName = "ApiController:listExpCatByUserId";
        log.info(LoggingConstants.START_METHOD_LOG, methodName);

        var userId = authService.verifyToken(accessToken);

        var expenseCategories = expCatService.listExpCatByUserId(userId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ExpCatMapper.INSTANCE.mapToExpCatResponseList(expenseCategories));
    }


    @PostMapping("/categories/{expCatId}")
    public ResponseEntity<ExpCatResponse> updateExpCat(@RequestHeader("Authorization") String accessToken, @PathVariable String expCatId , @RequestBody ExpCatRequest expCatRequest) {
        String methodName = "ApiController:updateExpCat";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, expCatId);

        var userId = authService.verifyToken(accessToken);

        var expenseCategory = expCatService.updateExpCat(userId, expCatId, expCatRequest);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ExpCatMapper.INSTANCE.mapToExpCatResponse(expenseCategory));
    }


    @DeleteMapping("/categories/{expCatId}")
    public ResponseEntity<Void> deleteExpCatById(@RequestHeader("Authorization") String accessToken, @PathVariable String expCatId) {
        String methodName = "ApiController:deleteExpCatById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, expCatId);

        var userId = authService.verifyToken(accessToken);

        expCatService.deleteExpCatById(userId, expCatId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }









    // expenses methods


    @PostMapping("/expenses")
    public ResponseEntity<ExpResponse> createExpense(@RequestHeader("Authorization") String accessToken, @RequestBody ExpenseData expenseData) {
        String methodName = "ApiController:createExpense";
        log.info(LoggingConstants.START_METHOD_LOG, methodName,expenseData);
        var userId = authService.verifyToken(accessToken);

        var expense = expService.createExpense(userId, expenseData);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ExpMapper.INSTANCE.mapToExpResponse(expense));
    }


    @GetMapping("/expenses/{expId}")
    public ResponseEntity<ExpResponse> getExpById(@RequestHeader("Authorization") String accessToken, @PathVariable String expId) {
        String methodName = "ApiController:getExpById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, expId);
        var userId = authService.verifyToken(accessToken);

        var expense = expService.getExpById(userId, expId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ExpMapper.INSTANCE.mapToExpResponse(expense));
    }

    @GetMapping("/expenses")
    public ResponseEntity<ExpPageResponse> listExpByUserId(@RequestHeader("Authorization") String accessToken, Pageable pageable,
                                                         @RequestParam(required = false) String startDate,
                                                         @RequestParam(required = false) String endDate) {
        String methodName = "ApiController:listExpByUserId";
        log.info(LoggingConstants.START_METHOD_LOG, methodName);
        var userId = authService.verifyToken(accessToken);

        if(startDate != null && endDate != null)  {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                simpleDateFormat.parse(startDate);
                simpleDateFormat.parse(endDate);
            } catch (ParseException e) {
                throw new InvalidRequestExecution(ErrorMessages.INVALID_DATE_RANGE.getErrorMessage(),
                        ErrorMessages.INVALID_DATE_RANGE.getErrorCode());
            }
        }



        var expensesPage = expService.listExpByUserId(userId, pageable, startDate, endDate);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);


        ExpPageResponse build = ExpPageResponse.builder()
                .expenses(ExpMapper.INSTANCE.mapToExpResponseList(expensesPage.getContent()))
                .isFirstPage(expensesPage.isFirst())
                .isLastPage(expensesPage.isLast())
                .nextPage(expensesPage.hasNext() ? expensesPage.getNumber() + 1 : -1)
                .nextPage(expensesPage.hasPrevious() ? expensesPage.getNumber() - 1 : -1)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(build);
    }



    @PutMapping("/expenses/{expId}")
    public ResponseEntity<ExpResponse> updateExpense(@RequestHeader("Authorization") String accessToken, @PathVariable String expId , @RequestBody ExpenseData expenseData) {
        String methodName = "ApiController:updateExpense";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, expId);
        var userId = authService.verifyToken(accessToken);

        var expense = expService.updateExpense(userId, expId, expenseData);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ExpMapper.INSTANCE.mapToExpResponse(expense));
    }


    @DeleteMapping("/expenses/{expId}")
    public ResponseEntity<Void> deleteExpById(@RequestHeader("Authorization") String accessToken, @PathVariable String expId) {
        String methodName = "ApiController:deleteExpById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, expId);
        var userId = authService.verifyToken(accessToken);

        expService.deleteExpById(userId, expId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }












    // reports



    @PostMapping("/reports/weekly-report")
    public ResponseEntity<ReportsResponse> getWeeklyReports(@RequestHeader("Authorization") String accessToken) {
        String methodName = "ApiController:getWeeklyReports";
        log.info(LoggingConstants.START_METHOD_LOG, methodName);

        var userId = authService.verifyToken(accessToken);

        var weeklyReport = reportsService.getWeeklyReport(userId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        var val =  ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ReportsResponse.builder()
                                .weeklyReport(weeklyReport)
                                .build()
                );

        return val;

    }


    @PostMapping("/reports/monthly-report")
    public ResponseEntity<ReportsResponse> getMonthlyReports(@RequestHeader("Authorization") String accessToken, @RequestParam String startDate) {
        String methodName = "ApiController:getMonthlyReports";
        log.info(LoggingConstants.START_METHOD_LOG, methodName);

        var userId = authService.verifyToken(accessToken);

        var monthlyReports = reportsService.getMonthlyReports(userId,startDate);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ReportsResponse.builder()
                                .monthlyReport(monthlyReports)
                                .build()
                );

    }

}
