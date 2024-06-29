package in.codingstreams.etbffservice.controller.mapper;

import in.codingstreams.etbffservice.controller.dto.UserInfo;
import in.codingstreams.etbffservice.controller.dto.UserInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper
public interface UserInfoMapper {
    UserInfoMapper INSTANCE = Mappers.getMapper(UserInfoMapper.class);

    @Mapping(target="date", source = "createdAt", qualifiedByName = "mapToDate")
    @Mapping(target="time", source = "createdAt", qualifiedByName = "mapToTime")
    UserInfoResponse mapToUserInfoResponse(UserInfo userInfo);



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
