package com.example.libraryserver.mappers;

import com.example.libraryserver.dtos.GenreDTO;
import com.example.libraryserver.entities.GenreEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {BookMapper.class})
public interface GenreMapper {
    GenreDTO genreEntityToGenreDTO(GenreEntity genreEntity);

    GenreEntity genreDTOToGenreEntity(GenreDTO genreDTO);

    @Mapping(target = "books", ignore = true)
    @Named("genreEntityToGenreDTOWithoutBooks")
    GenreDTO genreEntityToGenreDTOWithoutBooks(GenreEntity genreEntity);

    @Mapping(target = "books", qualifiedByName = "bookEntityToBookDTOWithoutAuthorsLoansGenres")
    @Named("genreEntityToGenreDTOWithShortBooks")
    GenreDTO genreEntityToGenreDTOWithShortBooks(GenreEntity genreEntity);
}
