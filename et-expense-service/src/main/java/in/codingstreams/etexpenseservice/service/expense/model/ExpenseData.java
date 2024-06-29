package in.codingstreams.etexpenseservice.service.expense.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseData {
    private String title;
    private String amount;
    private String expCatId;
}
