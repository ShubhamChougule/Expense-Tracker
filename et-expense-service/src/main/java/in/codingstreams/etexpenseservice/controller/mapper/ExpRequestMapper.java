package in.codingstreams.etexpenseservice.controller.mapper;

import in.codingstreams.etexpenseservice.controller.dto.ExpCatRequest;
import in.codingstreams.etexpenseservice.controller.dto.ExpRequest;
import in.codingstreams.etexpenseservice.service.expense.model.ExpenseData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpRequestMapper {
    ExpRequestMapper INSTANCE = Mappers.getMapper(ExpRequestMapper.class);
    ExpenseData mapToExpenseData(ExpRequest expRequest);
}
