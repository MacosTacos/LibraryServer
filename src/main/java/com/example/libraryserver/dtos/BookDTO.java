package com.example.libraryserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    //private Long version;
    private String title;
    private String description;
    private int quantity;
    private List<AuthorDTO> authors;
    private List<LoanDTO> loans;
    private List<GenreDTO> genres;
}
