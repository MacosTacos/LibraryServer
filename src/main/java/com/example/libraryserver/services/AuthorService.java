package com.example.libraryserver.services;

import com.example.libraryserver.dtos.AuthorDTO;
import com.example.libraryserver.entities.AuthorEntity;
import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.exceptions.DatabaseConnectionException;
import com.example.libraryserver.exceptions.ResourceNotFoundException;
import com.example.libraryserver.mappers.AuthorMapper;
import com.example.libraryserver.repositories.AuthorRepository;
import com.example.libraryserver.repositories.BookRepository;
import com.example.libraryserver.requests.authors.CreateAuthorRequest;
import com.example.libraryserver.requests.authors.UpdateAuthorRequest;
import com.example.libraryserver.responses.authors.GetAuthorResponse;
import com.example.libraryserver.responses.authors.GetAuthorsResponse;
import com.example.libraryserver.responses.general.InfoResponse;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;

    @Transactional
    public ResponseEntity<InfoResponse> createAuthor(CreateAuthorRequest createAuthorRequest) {
        try {
            AuthorEntity authorEntity = AuthorEntity.builder()
                    .name(createAuthorRequest.getName())
                    .surname(createAuthorRequest.getSurname())
                    .info(createAuthorRequest.getInfo())
                    .build();
            authorRepository.save(authorEntity);
            return new ResponseEntity<>(new InfoResponse("Author was created: " + authorEntity), HttpStatus.CREATED);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseConnectionException("Author was not created due to problems connecting to the database");
        }
    }
    @Transactional
    public ResponseEntity<?> getAuthorById(Long id) {
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));
        AuthorDTO authorDTO = authorMapper.authorEntityToAuthorDTOWithoutAuthorsAndGenresAndLoansInBooks(authorEntity);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);

//        GetAuthorResponse getAuthorResponse = GetAuthorResponse.builder()
//                .id(authorEntity.getId())
//                .name(authorEntity.getName())
//                .surname(authorEntity.getSurname())
//                .info(authorEntity.getInfo())
//                .books(authorEntity.getBooks())
//                .build();
//        return getAuthorResponse;
    }
    @Transactional
    public GetAuthorsResponse getAllAuthors() {
        List<AuthorEntity> authors = authorRepository.findAll();
        List<GetAuthorResponse> getAuthorResponseList = authors.stream()
                .map(authorEntity -> GetAuthorResponse.builder()
                        .id(authorEntity.getId())
                        .name(authorEntity.getName())
                        .surname(authorEntity.getSurname())
                        .info(authorEntity.getInfo())
                        .books(authorEntity.getBooks())
                        .build())
                .toList();
        return new GetAuthorsResponse(getAuthorResponseList);
    }
    @Transactional
    public ResponseEntity<InfoResponse> updateAuthor(UpdateAuthorRequest updateAuthorRequest) {
        AuthorEntity authorEntity = authorRepository.findById(updateAuthorRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + updateAuthorRequest.getId() + " not found"));
        try {
            if (updateAuthorRequest.getName() != null) {
                authorEntity.setName(updateAuthorRequest.getName());
            }
            if (updateAuthorRequest.getSurname() != null) {
                authorEntity.setSurname(updateAuthorRequest.getSurname());
            }
            if (updateAuthorRequest.getInfo() != null) {
                authorEntity.setInfo(updateAuthorRequest.getInfo());
            }
            authorRepository.save(authorEntity);
            return new ResponseEntity<>(new InfoResponse("Book with id " + updateAuthorRequest.getId() + " updated."), HttpStatus.OK);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseConnectionException("Author was not updated due to problems connecting to the database");
        }
    }
    @Transactional
    public ResponseEntity<InfoResponse> deleteAuthor(Long id) {
        try {
            AuthorEntity author = authorRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));
            List<AuthorEntity> authors = List.of(author);
            List<BookEntity> books = bookRepository.findAllByAuthors(authors);
            for (BookEntity book : books) {
                book.getAuthors().remove(author);
                bookRepository.save(book);
            }
            authorRepository.deleteById(id);
            return new ResponseEntity<>(new InfoResponse("Author with id " + id + " has been deleted."), HttpStatus.OK);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseConnectionException("Author was not deleted due to problems connecting to the database");
        }
    }
}
