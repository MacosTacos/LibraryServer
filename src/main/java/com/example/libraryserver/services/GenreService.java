package com.example.libraryserver.services;

import com.example.libraryserver.dtos.GenreDTO;
import com.example.libraryserver.entities.GenreEntity;
import com.example.libraryserver.exceptions.DatabaseConnectionException;
import com.example.libraryserver.exceptions.ResourceNotFoundException;
import com.example.libraryserver.mappers.GenreMapper;
import com.example.libraryserver.repositories.GenreRepository;
import com.example.libraryserver.requests.genres.CreateGenreRequest;
import com.example.libraryserver.requests.genres.UpdateGenreRequest;
import com.example.libraryserver.responses.general.InfoResponse;
import com.example.libraryserver.responses.genres.GetGenreResponse;
import com.example.libraryserver.responses.genres.GetGenresResponse;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Transactional
    public ResponseEntity<InfoResponse> createGenre(CreateGenreRequest createGenreRequest) {
        GenreEntity genreEntity = GenreEntity.builder()
                .name(createGenreRequest.getName())
                .info(createGenreRequest.getInfo())
                .build();
        try {
            genreRepository.save(genreEntity);
            return new ResponseEntity<>(new InfoResponse("Genre created: " + createGenreRequest.getName()), HttpStatus.CREATED);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseConnectionException("Genre was not created due to problems connecting to the database");
        }
    }
    @Transactional
    public GetGenreResponse getGenreByIdBrief(Long id) {
        GenreEntity genreEntity = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with id " + id + " not found"));

        GetGenreResponse getGenreResponse = GetGenreResponse.builder()
                .id(genreEntity.getId())
                .name(genreEntity.getName())
                .build();
        return getGenreResponse;
    }
    @Transactional
    public ResponseEntity<?> getGenreByIdFull(Long id) {
        GenreEntity genreEntity = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre with id " + id + " not found"));
        GenreDTO genreDTO = genreMapper.genreEntityToGenreDTOWithShortBooks(genreEntity);
        return new ResponseEntity<>(genreDTO, HttpStatus.OK);
    }
    @Transactional
    public GetGenresResponse getAllGenresBrief() {
        List<GenreEntity> genreEntityList = genreRepository.findAll();
        List<GetGenreResponse> getGenreResponseList = genreEntityList.stream()
                .map(genreEntity -> GetGenreResponse.builder()
                        .id(genreEntity.getId())
                        .name(genreEntity.getName())
                        .build())
                .toList();
        return new GetGenresResponse(getGenreResponseList);
    }
    @Transactional
    public ResponseEntity<?> getAllGenresFull() {
        List<GenreEntity> genreEntityList = genreRepository.findAll();
        List<GenreDTO> genreDTOS = genreEntityList.stream()
                .map(genreMapper::genreEntityToGenreDTOWithShortBooks)
                .toList();
        return new ResponseEntity<>(genreDTOS, HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<InfoResponse> updateGenre(UpdateGenreRequest updateGenreRequest) {
        GenreEntity genreEntity = genreRepository.findById(updateGenreRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre with id " + updateGenreRequest.getId() + " not found"));
        try {
            genreEntity.setName(updateGenreRequest.getName());
            genreEntity.setInfo(updateGenreRequest.getInfo());
            genreEntity.setBooks(updateGenreRequest.getBooks());
            genreRepository.save(genreEntity);
            return new ResponseEntity<>(new InfoResponse("Genre with id " + updateGenreRequest.getId() + " has been updated."), HttpStatus.OK);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseConnectionException("Genre was not updated due to problems connecting to the database");
        }

    }
    @Transactional
    public ResponseEntity<InfoResponse> deleteGenre(Long id) {
        try {
            genreRepository.deleteById(id);
            return new ResponseEntity<>(new InfoResponse("Genre with id " + id + " has been deleted."), HttpStatus.OK);
        } catch (DataAccessException | PersistenceException e) {
            throw new DatabaseConnectionException("Genre was not deleted due to problems connecting to the database");
        }
    }


}
