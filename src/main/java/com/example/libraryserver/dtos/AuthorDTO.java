package com.example.libraryserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {
    private Long id;
    //private Long version;
    private String name;
    private String surname;
    private String info;
    private List<BookDTO> books;
}
