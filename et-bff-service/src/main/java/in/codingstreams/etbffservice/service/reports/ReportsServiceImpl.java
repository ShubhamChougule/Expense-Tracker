package in.codingstreams.etbffservice.service.reports;

import in.codingstreams.etbffservice.constant.LoggingConstants;
import in.codingstreams.etbffservice.controller.dto.*;
import in.codingstreams.etbffservice.service.expense.ExpService;
import in.codingstreams.etbffservice.service.external.ReportsServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService{

    private final ExpService expService;
    private final ReportsServiceFeignClient reportsServiceFeignClient;

    @Override
    public WeeklyReport getWeeklyReport(String userId) {
        String methodName = "ReportsServiceImpl:getWeeklyReport";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);


        var dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        var today = LocalDateTime.now().toLocalDate();
        var startDate = dateFormatter.format(today);
        var endDate = dateFormatter.format(today.minusDays(7));

        var expensesPage = expService.listExpByUserId(userId, null, endDate, startDate);

        var reportsRequest = ReportsRequest.builder()
                .expenses(expensesPage.getContent()
                        .stream()
                        .map(ReportsServiceImpl::mapToReportsExp)
                        .collect(Collectors.toList()))
                .build();

        var weeklyReport = reportsServiceFeignClient.getWeeklyReports(userId,
                reportsRequest)
                .getBody()
                .getWeeklyReport();

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return weeklyReport;
    }

    private static ReportExp mapToReportsExp(Expense expense) {
        ReportExp build = ReportExp.builder()
                .amount(expense.getAmount())
                .createdAt(String.valueOf(expense.getCreatedAt()))
                .expenseCategory(expense.getExpCat().getName()).build();
        return build;
    }

    @Override
    public MonthlyReport getMonthlyReports(String userId, String startDate) {
        String methodName = "ReportsServiceImpl:getMonthlyReports";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);


        var dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        var endDate = dateFormatter.format(LocalDate.parse(startDate, dateFormatter).plusMonths(1));

        var expensesPage = expService.listExpByUserId(userId, null, startDate, endDate);

        var reportsRequest = ReportsRequest.builder()
                .expenses(expensesPage.getContent()
                        .stream()
                        .map(ReportsServiceImpl::mapToReportsExp)
                        .collect(Collectors.toList()))
                .build();

        var monthlyReport = reportsServiceFeignClient.getMonthlyReports(userId,
                        reportsRequest)
                .getBody()
                .getMonthlyReport();


        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return monthlyReport;

    }
}
