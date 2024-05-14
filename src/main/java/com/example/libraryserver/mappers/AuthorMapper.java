package com.example.libraryserver.mappers;

import com.example.libraryserver.dtos.*;
import com.example.libraryserver.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface AuthorMapper {
    AuthorEntity authorDTOToAuthorEntity(AuthorDTO authorDTO);

    @Mapping(target = "books", qualifiedByName = "bookEntityToBookDTOWithoutBooksInGenresAndAuthorsAndLoans")
    AuthorDTO authorEntityToAuthorDTOWithoutAuthorsAndGenresAndLoansInBooks(AuthorEntity authorEntity);

    @Mapping(target = "books", ignore = true)
    @Named("authorEntityToAuthorDTOWithoutBooks")
    AuthorDTO authorEntityToAuthorDTOWithoutBooks(AuthorEntity authorEntity);
}
