package in.codingstreams.etexpensereportsservice.service;

import in.codingstreams.etexpensereportsservice.controller.dto.Expense;
import in.codingstreams.etexpensereportsservice.service.model.MonthlyReport;
import in.codingstreams.etexpensereportsservice.service.model.WeeklyReport;

import java.util.List;

public interface ReportsService {
    WeeklyReport getWeeklyReport(String userId, List<Expense> expense);

    MonthlyReport getMonthlyReports(String userId, List<Expense> expenses);
}
