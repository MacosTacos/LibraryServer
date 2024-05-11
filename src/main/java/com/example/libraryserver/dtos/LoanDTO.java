package com.example.libraryserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {
    private Long id;
    //private Long version;
    private LocalDateTime loanDate;
    private int status; // 1- книга на руках у юзера, 2- книга возвращена
    private UserDTO user;
    private BookDTO book;
}
