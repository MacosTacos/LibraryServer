package com.example.libraryserver.controllers;

import com.example.libraryserver.requests.genres.CreateGenreRequest;
import com.example.libraryserver.requests.genres.UpdateGenreRequest;
import com.example.libraryserver.responses.general.InfoResponse;
import com.example.libraryserver.responses.genres.GetGenreResponse;
import com.example.libraryserver.responses.genres.GetGenresResponse;
import com.example.libraryserver.services.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/genre")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;


    @PostMapping("/create")
    public ResponseEntity<InfoResponse> createGenre(@RequestBody CreateGenreRequest createGenreRequest) {
        return genreService.createGenre(createGenreRequest);
    }

    @GetMapping("/get/full/{id}")
    public ResponseEntity<?> getGenreByIdFull(@PathVariable("id") Long id) {
        return genreService.getGenreByIdFull(id);
    }

    @GetMapping("/get/brief/{id}")
    public GetGenreResponse getGenreByIdBrief(@PathVariable("id") Long id) {
        return genreService.getGenreByIdBrief(id);
    }

    @GetMapping("/get/full")
    public ResponseEntity<?> getGenresFull() {
        return genreService.getAllGenresFull();
    }

    @GetMapping("/get/brief")
    public GetGenresResponse getGenresBrief() {
        return genreService.getAllGenresBrief();
    }

    @PatchMapping("/update")
    public ResponseEntity<InfoResponse> updateGenre(@RequestBody UpdateGenreRequest updateGenreRequest) {
        return genreService.updateGenre(updateGenreRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<InfoResponse> deleteGenre(Long id) {
        return genreService.deleteGenre(id);
    }


}
