package com.example.libraryserver.mappers;

import com.example.libraryserver.entities.UserEntity;
import com.example.libraryserver.dtos.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "loans", ignore = true)
    @Named("userEntityToUserDTOWithoutPswrdAndRoleAndLoans")
    UserDTO userEntityToUserDTOWithoutPswrdAndRoleAndLoans(UserEntity userEntity);
    UserEntity userDTOToUserEntity(UserDTO userDTO);
}
