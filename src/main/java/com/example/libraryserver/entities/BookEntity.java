package com.example.libraryserver.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class BookEntity {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    private Long id;
    @Version
    private Long version;
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    private int quantity;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JsonManagedReference(value = "book-author reference")
    private List<AuthorEntity> authors;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "book")
    @JsonBackReference(value = "loan-book reference")
    @JsonManagedReference(value = "book-loan reference")
    private List<LoanEntity> loans;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "book-genres reference")
    private List<GenreEntity> genres;

}
