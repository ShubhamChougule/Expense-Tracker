package in.codingstreams.etbffservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense {
    private String expId;
    private String userId;
    private String title;
    private String amount;
    private ExpenseCategory expCat;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
}
