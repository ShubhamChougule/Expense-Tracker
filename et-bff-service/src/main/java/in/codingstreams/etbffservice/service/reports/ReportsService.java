package in.codingstreams.etbffservice.service.reports;

import in.codingstreams.etbffservice.controller.dto.Expense;
import in.codingstreams.etbffservice.controller.dto.MonthlyReport;
import in.codingstreams.etbffservice.controller.dto.WeeklyReport;

import java.util.List;

public interface ReportsService {
    WeeklyReport getWeeklyReport(String userId);

    MonthlyReport getMonthlyReports(String userId, String startDate);
}
