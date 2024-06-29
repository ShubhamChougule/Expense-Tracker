package in.codingstreams.etexpensereportsservice.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense {
    private Float amount;
    private String expenseCategory;
    private String createdAt;
}
