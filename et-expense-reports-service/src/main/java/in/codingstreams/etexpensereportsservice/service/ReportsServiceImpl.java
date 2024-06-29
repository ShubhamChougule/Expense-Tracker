package in.codingstreams.etexpensereportsservice.service;

import in.codingstreams.etexpensereportsservice.constant.LoggingConstant;
import in.codingstreams.etexpensereportsservice.controller.dto.Expense;
import in.codingstreams.etexpensereportsservice.service.model.MonthlyReport;
import in.codingstreams.etexpensereportsservice.service.model.WeeklyReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReportsServiceImpl implements ReportsService{
    @Override
    public WeeklyReport getWeeklyReport(String userId, List<Expense> expenses) {
        String methodName = "ReportsServiceImpl:getWeeklyReport";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, userId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        DateTimeFormatter parser = DateTimeFormatter.ISO_DATE_TIME; // Adjust this pattern according to your `createdAt` format

        var totalExpenseByDate = expenses.stream()
                .collect(Collectors.groupingBy(
                        expense -> LocalDateTime.parse(expense.getCreatedAt(), parser)
                                .toLocalDate()
                                .format(formatter),
                        Collectors.summingDouble(expense -> expense.getAmount().doubleValue())
                ));

        // calculate in a week

        var totalExpenses = totalExpenseByDate.values()
                        .stream()
                                .mapToDouble(value -> value)
                                        .sum();

        var data = WeeklyReport.builder()
                        .totalExpenseByDate(totalExpenseByDate)
                                .totalExpenses(totalExpenses)
                                        .build();


        log.info(LoggingConstant.END_METHOD_LOG, methodName, userId);

        return data;
    }

    @Override
    public MonthlyReport getMonthlyReports(String userId, List<Expense> expenses) {
        String methodName = "ReportsServiceImpl:getMonthlyReports";
        log.info(LoggingConstant.START_METHOD_LOG, methodName, userId);


        var totalExpenseByExpCat = expenses.stream()
                .collect(
                        Collectors.groupingBy(Expense::getExpenseCategory,
                                Collectors.summingDouble(Expense::getAmount))
                );

        var totalExpenses = totalExpenseByExpCat.values()
                .stream()
                .mapToDouble(value -> value)
                .sum();

        var monthlyReport = MonthlyReport.builder()
                .totalExpenseByExpCat(totalExpenseByExpCat)
                .totalExpenses(totalExpenses)
                .build();



        log.info(LoggingConstant.END_METHOD_LOG, methodName, userId);

        return monthlyReport;

    }
}
