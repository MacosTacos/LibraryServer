package com.example.libraryserver.mappers;

import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.dtos.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {LoanMapper.class, AuthorMapper.class, GenreMapper.class})
public interface BookMapper {
    /**
     * Maps a BookEntity object to a BookDTO object, ignoring certain fields in the process.
     *
     * @param bookEntity The BookEntity object to be mapped.
     * @return A BookDTO object with fields mapped from the BookEntity object.
     * <p>
     * The 'genres' field in the returned BookDTO object is mapped using the
     * 'genreEntityToGenreDTOWithoutBooks' method in the GenreMapper interface.
     * This method ignores the 'books' field in the GenreEntity object,
     * resulting in 'null' values for this field in the corresponding GenreDTO object.
     * <p>
     * The 'loans' field in the returned BookDTO object is mapped using the
     * 'loanEntityToLoanDTOWithoutUserAndBook' method in the LoanMapper interface.
     * This method ignores the 'user' and 'book' fields in the LoanEntity object,
     * resulting in 'null' values for these fields in the corresponding LoanDTO object.
     * <p>
     * The 'authors' field in the returned BookDTO object is mapped using the
     * 'authorEntityToAuthorDTOWithoutBooks' method in the AuthorMapper interface.
     * This method ignores the 'books' field in the AuthorEntity object,
     * resulting in 'null' values for this field in the corresponding AuthorDTO object.
     * <p>
     * This method is typically used when you want to retrieve a book's details
     * but you don't want to include the details of the associated users, books in genres,
     * and books in authors in the response.
     */
    @Mapping(target = "genres", qualifiedByName = "genreEntityToGenreDTOWithoutBooks")
    @Mapping(target = "loans", qualifiedByName = "loanEntityToLoanDTOWithoutUserAndBook")
    @Mapping(target = "authors", qualifiedByName = "authorEntityToAuthorDTOWithoutBooks")
    BookDTO bookEntityToBookDTOWithoutBookAndUserInLoans(BookEntity bookEntity);

    /**
     * Maps a BookEntity object to a BookDTO object, ignoring certain fields in the process.
     *
     * @param bookEntity The BookEntity object to be mapped.
     * @return A BookDTO object with fields mapped from the BookEntity object.
     * <p>
     * The 'genres', 'loans', and 'authors' fields in the returned BookDTO object are ignored during the mapping process.
     * This results in 'null' values for these fields in the corresponding BookDTO object.
     * <p>
     * This method is typically used when you want to retrieve a book's details
     * but you don't want to include the details of the associated genres, loans, and authors in the response.
     */
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "loans", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Named("bookEntityToBookDTOWithoutAuthorsLoansGenres")
    BookDTO bookEntityToBookDTOWithoutAuthorsLoansGenres(BookEntity bookEntity);

    @Mapping(target = "loans", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", qualifiedByName = "genreEntityToGenreDTOWithoutBooks")
    @Named("bookEntityToBookDTOWithoutBooksInGenresAndAuthorsAndLoans")
    BookDTO bookEntityToBookDTOWithoutBooksInGenresAndAuthorsAndLoans(BookEntity bookEntity);


    BookEntity bookDTOToBookEntity(BookDTO bookDTO);
}
