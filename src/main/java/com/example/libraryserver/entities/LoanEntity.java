package com.example.libraryserver.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loans")
public class LoanEntity {
    @Id
    @SequenceGenerator(
            name = "loan_sequence",
            sequenceName = "loan_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "loan_sequence"
    )
    private Long id;
    @Version
    private Long version;
    private LocalDateTime loanDate;
    private int status; // 1 - книга на руках у юзера, 2 - книга возвращена

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    @JsonManagedReference(value = "loan-user reference")
    @JsonBackReference(value = "book-loan reference")
    private UserEntity user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    @JsonManagedReference(value = "loan-book reference")
    private BookEntity book;

}
