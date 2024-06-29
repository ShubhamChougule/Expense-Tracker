package in.codingstreams.etbffservice.service.expcat;


import in.codingstreams.etbffservice.controller.dto.ExpCatRequest;
import in.codingstreams.etbffservice.controller.dto.ExpenseCategory;

import java.util.List;

public interface ExpCatService {
    ExpenseCategory createExpCat(String userId, ExpCatRequest expCatRequest);

    ExpenseCategory getExpCatById(String userId, String expCatId);

    List<ExpenseCategory> listExpCatByUserId(String userId);

    ExpenseCategory updateExpCat(String userId, String expCatId, ExpCatRequest expCatRequest);

    void deleteExpCatById(String userId, String expCatId);
}
