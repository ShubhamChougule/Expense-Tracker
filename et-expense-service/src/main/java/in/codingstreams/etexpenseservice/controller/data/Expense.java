package in.codingstreams.etexpenseservice.controller.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "expenses")
@CompoundIndex(
        name = "expId_userId",
        def = "{'expId' : 1, 'userId' : 1}",
        unique = true
)

public class Expense {
    @Id
    private String expId;
    private String userId;
    private String title;
    private String amount;
    @DocumentReference
    private ExpenseCategory expCat;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
