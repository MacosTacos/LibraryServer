package com.example.libraryserver.services;

import com.example.libraryserver.entities.BookEntity;
import com.example.libraryserver.exceptions.DatabaseConnectionException;
import com.example.libraryserver.exceptions.ResourceNotFoundException;
import com.example.libraryserver.mappers.BookMapper;
import com.example.libraryserver.repositories.AuthorRepository;
import com.example.libraryserver.repositories.BookRepository;
import com.example.libraryserver.repositories.GenreRepository;
import com.example.libraryserver.requests.books.CreateBookRequest;
import com.example.libraryserver.requests.books.UpdateBookRequest;
import com.example.libraryserver.responses.books.GetBookResponse;
import com.example.libraryserver.responses.books.GetBooksAmountResponse;
import com.example.libraryserver.responses.books.GetBooksResponse;
import com.example.libraryserver.responses.general.InfoResponse;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.libraryserver.dtos.BookDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;
    public ResponseEntity<InfoResponse> createBook(CreateBookRequest createBookRequest) {
        try {
            BookEntity bookEntity = BookEntity.builder()
                    .title(createBookRequest.getTitle())
                    .description(createBookRequest.getDescription())
                    .quantity(createBookRequest.getQuantity())
                    .authors(createBookRequest.getAuthors() != null ? authorRepository.findAllById(createBookRequest.getAuthors()) : null)
                    .genres(createBookRequest.getGenres() != null ? genreRepository.findAllById(createBookRequest.getGenres()) : null)
                    .build();
            bookRepository.save(bookEntity);
            return new ResponseEntity<>(new InfoResponse("Book created: " + bookEntity), HttpStatus.CREATED);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseConnectionException("Genre was not created due to problems connecting to the database");
        }
    }

    public ResponseEntity<?> getBookById(Long id) {
        BookEntity bookEntity = bookRepository.findBookEntityById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
        BookDTO bookDTO = bookMapper.bookEntityToBookDTO(bookEntity);


//        GetBookResponse getBookResponse = GetBookResponse.builder()
//                .id(bookEntity.getId())
//                .title(bookEntity.getTitle())
//                .quantity(bookEntity.getQuantity())
//                .authors(bookEntity.getAuthors())
//                .description(bookEntity.getDescription())
//                .genres(bookEntity.getGenres())
//                .build();

        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    public GetBooksResponse getAllBooks() {
        List<BookEntity> bookEntityList = bookRepository.findAll();
        List<GetBookResponse> getBookResponseList = bookEntityList.stream()
                .map(bookEntity -> GetBookResponse.builder()
                        .id(bookEntity.getId())
                        .title(bookEntity.getTitle())
                        .quantity(bookEntity.getQuantity())
                        .authors(bookEntity.getAuthors())
                        .description(bookEntity.getDescription())
                        .genres(bookEntity.getGenres())
                        .build())
                .toList();
        return new GetBooksResponse(getBookResponseList);
    }

    public ResponseEntity<InfoResponse> updateBook(UpdateBookRequest updateBookRequest) {
        BookEntity bookEntity = bookRepository.findBookEntityById(updateBookRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + updateBookRequest.getId() + " not found"));
        try {
            if (updateBookRequest.getTitle() != null) {
                bookEntity.setTitle(updateBookRequest.getTitle());
            }
            if (updateBookRequest.getQuantity() > 0) {
                bookEntity.setQuantity(updateBookRequest.getQuantity());
            }
            if (updateBookRequest.getDescription() != null) {
                bookEntity.setDescription(updateBookRequest.getDescription());
            }
            if (updateBookRequest.getGenres() != null) {
                bookEntity.setGenres(genreRepository.findAllById(updateBookRequest.getGenres()));
            }
            if (updateBookRequest.getAuthors() != null) {
                bookEntity.setAuthors(authorRepository.findAllById(updateBookRequest.getAuthors()));
            }
            bookRepository.save(bookEntity);
            return new ResponseEntity<>(new InfoResponse("Book with id " + updateBookRequest.getId() + " updated."), HttpStatus.OK);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseConnectionException("Book was not updated due to problems connecting to the database");
        }
    }

    public ResponseEntity<InfoResponse> deleteBook(Long id) {
        try {
            bookRepository.deleteById(id);
            return new ResponseEntity<>(new InfoResponse("Book with id " + id + " has been deleted."), HttpStatus.OK);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseConnectionException("Book was not deleted due to problems connecting to the database");
        }
    }


    public ResponseEntity<?> getPage(int page, int size) {
        Long startId = (long) size * (page - 1L) + 1L;
        Long endId = startId + size - 1L;
        List<BookEntity> bookEntities = bookRepository.findAllByIdBetween(startId,endId);
        List<GetBookResponse> getBookResponseList = bookEntities.stream()
                .map(bookEntity -> GetBookResponse.builder()
                        .id(bookEntity.getId())
                        .title(bookEntity.getTitle())
                        .quantity(bookEntity.getQuantity())
                        .authors(bookEntity.getAuthors())
                        .description(bookEntity.getDescription())
                        .genres(bookEntity.getGenres())
                        .build())
                .collect(Collectors.toList());

        return new ResponseEntity<>(new GetBooksResponse(getBookResponseList), HttpStatus.OK);
    }

    public ResponseEntity<?> getAmount() {
        Long amount = bookRepository.count();
        return new ResponseEntity<GetBooksAmountResponse>(new GetBooksAmountResponse(amount), HttpStatus.OK);
    }

    public ResponseEntity<?> getByTitle(String title) {
        List<BookEntity> bookEntities = bookRepository.findBookEntitiesByTitleContaining(title);
        // TODO: reconsider about using this
        List<GetBookResponse> getBookResponseList = bookEntities.stream()
                .map(bookEntity -> GetBookResponse.builder()
                        .id(bookEntity.getId())
                        .title(bookEntity.getTitle())
                        .quantity(bookEntity.getQuantity())
                        .authors(bookEntity.getAuthors())
                        .description(bookEntity.getDescription())
                        .genres(bookEntity.getGenres())
                        .build())
                .collect(Collectors.toList());
        return new ResponseEntity<>(new GetBooksResponse(getBookResponseList), HttpStatus.OK);
    }
}
