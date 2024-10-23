package com.example.demo.mapper;

import com.example.demo.dto.request.ProfileRequest;
import com.example.demo.dto.response.UserProfileDTO;
import com.example.demo.entity.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfileMapper {


    UserProfileDTO toProfileDTO(ProfileRequest request);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    UserProfileDTO toProfileDTO(Profile profile);
}
