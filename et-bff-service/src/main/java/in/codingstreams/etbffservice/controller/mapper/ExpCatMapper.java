package in.codingstreams.etbffservice.controller.mapper;

import in.codingstreams.etbffservice.controller.dto.ExpCatResponse;
import in.codingstreams.etbffservice.controller.dto.ExpenseCategory;
import in.codingstreams.etbffservice.controller.dto.UserInfo;
import in.codingstreams.etbffservice.controller.dto.UserInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper
public interface ExpCatMapper {
    ExpCatMapper INSTANCE = Mappers.getMapper(ExpCatMapper.class);

    @Mapping(target="date", source = "createdAt", qualifiedByName = "mapToDate")
    @Mapping(target="time", source = "createdAt", qualifiedByName = "mapToTime")
    ExpCatResponse mapToExpCatResponse(ExpenseCategory expenseCategory);
    List<ExpCatResponse> mapToExpCatResponseList(List<ExpenseCategory> expenseCategories);



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
}
