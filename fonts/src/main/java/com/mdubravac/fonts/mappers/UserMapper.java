package com.mdubravac.fonts.mappers;

import com.mdubravac.fonts.dto.SignUpDto;
import com.mdubravac.fonts.dto.AdminDto;
import com.mdubravac.fonts.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "role", target = "role")
    AdminDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto userDto);
}
