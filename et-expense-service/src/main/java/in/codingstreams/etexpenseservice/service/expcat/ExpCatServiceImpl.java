package in.codingstreams.etexpenseservice.service.expcat;

import in.codingstreams.etexpenseservice.constant.ErrorMessage;
import in.codingstreams.etexpenseservice.constant.LoggingConstants;
import in.codingstreams.etexpenseservice.controller.data.ExpenseCategory;
import in.codingstreams.etexpenseservice.exception.ExpCatAlreadyExistsException;
import in.codingstreams.etexpenseservice.exception.ExpCatNotFoundException;
import in.codingstreams.etexpenseservice.repo.ExpCatRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpCatServiceImpl implements ExpCatService {

    private final ExpCatRepo expCatRepo;





    @Override
    public ExpenseCategory updateExpCat(String userId, String expCatId, String name) {
        String methodName = "ExpCatServiceImpl:updateExpCat";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expCatId);

        // find exp cat by name and userid
        if(expCatRepo.existsByUserIdAndName(userId, name.toLowerCase())) {
            throw new ExpCatAlreadyExistsException(ErrorMessage.EXP_CAT_ALREADY_EXISTS.getErrorCode(),
                    ErrorMessage.EXP_CAT_ALREADY_EXISTS.getErrorMessage());
        }


        // get expense category

        var expenseCategory = getExpCatById(userId, expCatId);

        // update name
        expenseCategory.setName(name.toLowerCase());

        // save expense category

        var saveExpCat = expCatRepo.save(expenseCategory);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return saveExpCat;
    }

    @Override
    public void deleteExpCatById(String userId, String expCatId) {
        String methodName = "ExpCatServiceImpl:deleteExpCatById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expCatId);

        expCatRepo.deleteByExpCatIdAndUserId(expCatId, userId);


        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);
    }


    @Override
    public List<ExpenseCategory> listExpCatByUserId(String userId) {
        String methodName = "ExpCatServiceImpl:listExpCatByUserId";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var expenseCategories = expCatRepo.findAllByUserId(userId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return expenseCategories;
    }




    @Override
    public ExpenseCategory createExpCat(String userId, String name) {
        String methodName = "ExpCatServiceImpl:createExpCat";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, name);

        // find exp cat by name and userid
        if(expCatRepo.existsByUserIdAndName(userId, name.toLowerCase())) {
            throw new ExpCatAlreadyExistsException(ErrorMessage.EXP_CAT_ALREADY_EXISTS.getErrorCode(),
                    ErrorMessage.EXP_CAT_ALREADY_EXISTS.getErrorMessage());
        }


        // create expense category

        var expenseCategory = ExpenseCategory.builder()
                .name(name.toLowerCase())
                        .userId(userId)
                                .build();


        // save expense category

        var saveExpCat = expCatRepo.save(expenseCategory);


        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return saveExpCat;
    }

    @Override
    public ExpenseCategory getExpCatById(String userId, String expCatId) {
        String methodName = "ExpCatServiceImpl:getExpCatById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expCatId);

        // find if exists or not
        var expenseCategory = expCatRepo.findByExpCatIdAndUserId(expCatId, userId)
                        .orElseThrow(() -> new ExpCatNotFoundException(ErrorMessage.EXP_CAT_NOT_FOUND.getErrorCode(),
                                ErrorMessage.EXP_CAT_NOT_FOUND.getErrorMessage()));


        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);
        return expenseCategory;
    }


}
