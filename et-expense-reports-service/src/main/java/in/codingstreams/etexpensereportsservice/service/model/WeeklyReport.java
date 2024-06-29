package in.codingstreams.etexpensereportsservice.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeeklyReport {
    private Map<String, Double> totalExpenseByDate;
    private Double totalExpenses;
}
