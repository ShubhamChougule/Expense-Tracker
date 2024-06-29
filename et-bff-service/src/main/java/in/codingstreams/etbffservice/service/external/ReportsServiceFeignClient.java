package in.codingstreams.etbffservice.service.external;

import in.codingstreams.etbffservice.controller.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${services.et-rs.name}", url = "${services.et-rs.url}")
public interface ReportsServiceFeignClient {


    // http://localhost:8082/api/

    @PostMapping("/users/{userId}/reports/weekly-report")
     ResponseEntity<ReportsResponse> getWeeklyReports(@PathVariable String userId, @RequestBody ReportsRequest request);

    @PostMapping("/users/{userId}/reports/monthly-report")
     ResponseEntity<ReportsResponse> getMonthlyReports(@PathVariable String userId, @RequestBody ReportsRequest request) ;

}
