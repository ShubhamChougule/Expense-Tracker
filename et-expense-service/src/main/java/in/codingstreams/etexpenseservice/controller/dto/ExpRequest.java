package in.codingstreams.etexpenseservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpRequest {
    private String title;
    private String amount;
    private String expCatId;
}
