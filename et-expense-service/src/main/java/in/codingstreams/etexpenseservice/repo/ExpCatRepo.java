package in.codingstreams.etexpenseservice.repo;

import in.codingstreams.etexpenseservice.controller.data.ExpenseCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ExpCatRepo extends MongoRepository<ExpenseCategory, String> {
    boolean existsByUserIdAndName(String userId, String lowerCase);

    Optional<ExpenseCategory> findByExpCatIdAndUserId(String expCatId, String userId);

    List<ExpenseCategory> findAllByUserId(String userId);

    void deleteByExpCatIdAndUserId(String expCatId, String userId);
}
