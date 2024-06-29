package in.codingstreams.etbffservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpPageResponse {
    private List<ExpResponse> expenses;
    private boolean isFirstPage;
    private boolean isLastPage;
    private int nextPage;
    private int previousPage;
}
