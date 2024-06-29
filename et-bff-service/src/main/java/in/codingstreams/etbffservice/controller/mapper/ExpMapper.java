package in.codingstreams.etbffservice.controller.mapper;

import in.codingstreams.etbffservice.controller.dto.ExpCatResponse;
import in.codingstreams.etbffservice.controller.dto.ExpResponse;
import in.codingstreams.etbffservice.controller.dto.Expense;
import in.codingstreams.etbffservice.controller.dto.ExpenseCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper
public interface ExpMapper {
    ExpMapper INSTANCE = Mappers.getMapper(ExpMapper.class);

    @Mapping(target="date", source = "createdAt", qualifiedByName = "mapToDate")
    @Mapping(target="time", source = "createdAt", qualifiedByName = "mapToTime")
    @Mapping(target="expCat", source = "expCat", qualifiedByName = "mapToExpCatResponse")
    ExpResponse mapToExpResponse(Expense expense);



    @Named("mapToExpCatResponse")
    static ExpCatResponse mapToExpCatResponse(ExpenseCategory expenseCategory) {
        return ExpCatMapper.INSTANCE.mapToExpCatResponse(expenseCategory);
    }



    @Named("mapToDate")
    static String mapToDate(LocalDateTime createdAt) {
        return DateTimeFormatter.ofPattern("dd-MMM-yyyy")
                .format(createdAt);
    }
    @Named("mapToTime")
    static String mapToTime(LocalDateTime createdAt) {
        return DateTimeFormatter.ofPattern("hh:mm a")
                .format(createdAt);
    }


    List<ExpResponse> mapToExpResponseList(List<Expense> expenses);
}
