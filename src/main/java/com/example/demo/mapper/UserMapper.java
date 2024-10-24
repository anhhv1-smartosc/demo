package com.example.demo.mapper;

import com.example.demo.dto.request.UserCreate;
import com.example.demo.dto.response.UserDTO;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreate request);

    @Mappings({
            @Mapping(source = "username", target = "userAccount"),
            @Mapping(source = "password", target = "passcode"),
            @Mapping(source = "email", target = "email"),
            @Mapping(target = "roles", ignore = true)
    })
    UserDTO toDTO(User user);

//    @Mappings({
//            @Mapping(source = "userAccount", target = "username"),
//            @Mapping(source = "passcode", target = "password")
//    })
//    User toEntity(UserDTO dto);
}
