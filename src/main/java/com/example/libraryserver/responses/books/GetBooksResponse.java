package com.example.libraryserver.responses.books;

import com.example.libraryserver.dtos.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetBooksResponse {
    private List<BookDTO> books;
}
