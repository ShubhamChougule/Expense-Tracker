package in.codingstreams.etexpensereportsservice.controller;


import in.codingstreams.etexpensereportsservice.constant.LoggingConstant;
import in.codingstreams.etexpensereportsservice.controller.dto.ReportsRequest;
import in.codingstreams.etexpensereportsservice.controller.dto.ReportsResponse;
import in.codingstreams.etexpensereportsservice.service.ReportsService;
import in.codingstreams.etexpensereportsservice.service.model.WeeklyReport;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/reports")
@Slf4j
@RequiredArgsConstructor
public class ReportsController {
    private final ReportsService reportsService;

    @PostMapping("/weekly-report")
    public ResponseEntity<ReportsResponse> getWeeklyReports(@PathVariable String userId, @RequestBody ReportsRequest request) {
        String methodName = "ReportsController:getWeeklyReports";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, userId);

        var weeklyReport = reportsService.getWeeklyReport(userId, request.getExpenses());

        log.info(LoggingConstant.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ReportsResponse.builder()
                                .weeklyReport(weeklyReport)
                                .build()
                );

    }


    @PostMapping("/monthly-report")
    public ResponseEntity<ReportsResponse> getMonthlyReports(@PathVariable String userId, @RequestBody ReportsRequest request) {
        String methodName = "ReportsController:getMonthlyReports";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, userId);

        var monthlyReports = reportsService.getMonthlyReports(userId, request.getExpenses());

        log.info(LoggingConstant.END_METHOD_LOG, methodName, userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ReportsResponse.builder()
                                .monthlyReport(monthlyReports)
                                .build()
                );

    }
}
