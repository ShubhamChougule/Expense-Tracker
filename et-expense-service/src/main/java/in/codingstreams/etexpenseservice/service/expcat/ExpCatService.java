package in.codingstreams.etexpenseservice.service.expcat;

import in.codingstreams.etexpenseservice.controller.data.ExpenseCategory;

import java.util.List;

public interface ExpCatService {
    ExpenseCategory createExpCat(String userId, String name);

    ExpenseCategory getExpCatById(String userId, String expCatId);

    List<ExpenseCategory> listExpCatByUserId(String userId);

    ExpenseCategory updateExpCat(String userId, String expCatId, String name);

    void deleteExpCatById(String userId, String expCatId);
}
