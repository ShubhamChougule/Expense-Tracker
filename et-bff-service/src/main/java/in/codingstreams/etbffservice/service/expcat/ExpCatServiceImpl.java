package in.codingstreams.etbffservice.service.expcat;

import in.codingstreams.etbffservice.constant.LoggingConstants;
import in.codingstreams.etbffservice.controller.dto.ExpCatRequest;
import in.codingstreams.etbffservice.controller.dto.ExpenseCategory;
import in.codingstreams.etbffservice.service.external.ExpenseServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpCatServiceImpl implements ExpCatService {

    private final ExpenseServiceFeignClient expenseServiceFeignClient;


    @Override
    public ExpenseCategory updateExpCat(String userId, String expCatId, ExpCatRequest expCatRequest) {
        String methodName = "ExpCatServiceImpl:updateExpCat";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expCatId);


        var expenseCategory = expenseServiceFeignClient.updateExpCat(userId, expCatId, expCatRequest).getBody();

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return expenseCategory;
    }

    @Override
    public void deleteExpCatById(String userId, String expCatId) {
        String methodName = "ExpCatServiceImpl:deleteExpCatById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expCatId);

        expenseServiceFeignClient.deleteExpCatById(userId, expCatId);

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);
    }


    @Override
    public List<ExpenseCategory> listExpCatByUserId(String userId) {
        String methodName = "ExpCatServiceImpl:listExpCatByUserId";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId);

        var expenseCategories = expenseServiceFeignClient.listExpCatByUserId(userId).getBody();

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return expenseCategories;
    }




    @Override
    public ExpenseCategory createExpCat(String userId, ExpCatRequest expCatRequest) {
        String methodName = "ExpCatServiceImpl:createExpCat";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expCatRequest);

        var expenseCategory = expenseServiceFeignClient.
                createExpCat(userId, expCatRequest).getBody();

        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);

        return expenseCategory;
    }

    @Override
    public ExpenseCategory getExpCatById(String userId, String expCatId) {
        String methodName = "ExpCatServiceImpl:getExpCatById";
        log.info(LoggingConstants.START_METHOD_LOG, methodName, userId, expCatId);

        // find if exists or not
        var expenseCategory = expenseServiceFeignClient.
                getExpCatById(userId, expCatId)
                        .getBody();


        log.info(LoggingConstants.END_METHOD_LOG, methodName, userId);
        return expenseCategory;
    }


}
