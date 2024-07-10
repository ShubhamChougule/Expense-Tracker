package in.codingstreams.etexpenseservice.repo;

import in.codingstreams.etexpenseservice.controller.data.Expense;
import in.codingstreams.etexpenseservice.controller.data.ExpenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExpRepo extends MongoRepository<Expense, String> {

    Optional<Expense> findByExpIdAndUserId(String expId, String userId);

//    Page<Expense> findAllByUserId(String userId, Pageable pageable);
//
//    Page<Expense> findAllByUserIdAndCreatedAtBetween(String userId, LocalDateTime localDateTime, LocalDateTime localDateTime1, Pageable pageable);

    void deleteByExpIdAndUserId(String expId, String userId);

    Page<Expense> findAllByUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(String userId, LocalDateTime localDateTime, LocalDateTime localDateTime1, Pageable pageable);

    Page<Expense> findAllByUserIdOrderByCreatedAtDesc(String userId, Pageable pageable);
}
