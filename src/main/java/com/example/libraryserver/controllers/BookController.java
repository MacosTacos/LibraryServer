package com.example.libraryserver.controllers;

import com.example.libraryserver.requests.books.CreateBookRequest;
import com.example.libraryserver.requests.books.UpdateBookRequest;
import com.example.libraryserver.responses.general.InfoResponse;
import com.example.libraryserver.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<InfoResponse> createBook(@RequestBody CreateBookRequest createBookRequest) {
        return bookService.createBook(createBookRequest);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") Long id, @RequestParam String type) {
        return switch (type) {
            case "full" -> bookService.getBookByIdFull(id);
            case "short" -> bookService.getBookByIdShort(id);
            default ->
                    new ResponseEntity<>(new InfoResponse("Invalid type. full and short keys supported"), HttpStatus.BAD_REQUEST);
        };
    }

    @PatchMapping("/update")
    public ResponseEntity<InfoResponse> updateBook(@RequestBody UpdateBookRequest changeBookRequest) {
        return bookService.updateBook(changeBookRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<InfoResponse> deleteBook(@PathVariable("id") Long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/getPage")
    public ResponseEntity<?> getPage(@RequestParam int page, @RequestParam int size) {
        return bookService.getPage(page, size);
    }

    @GetMapping("/getAmount")
    public ResponseEntity<?> getAmount() {
        return bookService.getAmount();
    }

    @GetMapping("/getByTitle")
    public ResponseEntity<?> getByTitle(@RequestParam String title) {
        return bookService.getByTitle(title);
    }
}
