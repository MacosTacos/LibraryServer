package com.example.libraryserver.controllers;

import com.example.libraryserver.requests.authors.CreateAuthorRequest;
import com.example.libraryserver.requests.authors.UpdateAuthorRequest;
import com.example.libraryserver.responses.authors.GetAuthorResponse;
import com.example.libraryserver.responses.authors.GetAuthorsResponse;
import com.example.libraryserver.responses.general.InfoResponse;
import com.example.libraryserver.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    @PostMapping("/create")
    public ResponseEntity<InfoResponse> createAuthor(@RequestBody CreateAuthorRequest createAuthorRequest){
        return authorService.createAuthor(createAuthorRequest);
    }

    @GetMapping("/get")
    public GetAuthorsResponse getAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @PatchMapping("/update")
    public ResponseEntity<InfoResponse> updateAuthor(@RequestBody UpdateAuthorRequest updateAuthorRequest) {
        return authorService.updateAuthor(updateAuthorRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<InfoResponse> deleteAuthor(@PathVariable("id") Long id) {
        return authorService.deleteAuthor(id);
    }


}
