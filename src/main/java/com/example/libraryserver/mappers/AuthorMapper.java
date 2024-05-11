package com.example.libraryserver.mappers;

import com.example.libraryserver.dtos.*;
import com.example.libraryserver.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorDTO authorEntityToAuthorDTO(AuthorEntity authorEntity);
    AuthorEntity authorDTOToAuthorEntity(AuthorDTO authorDTO);
    @Mapping(target = "books", ignore = true)
    @Named("authorEntityToAuthorDTOWithoutBooks")
    AuthorDTO authorEntityToAuthorDTOWithoutBooks(AuthorEntity authorEntity);
}
