package in.codingstreams.etexpensereportsservice.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import in.codingstreams.etexpensereportsservice.service.model.MonthlyReport;
import in.codingstreams.etexpensereportsservice.service.model.WeeklyReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ReportsResponse {
    private WeeklyReport weeklyReport;
    private MonthlyReport monthlyReport;
}
