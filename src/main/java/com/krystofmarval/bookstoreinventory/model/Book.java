package com.krystofmarval.bookstoreinventory.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements ModelBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false)
    @NotBlank(message = "Title is required")
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = true)
    private Author author;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = true)
    private Genre genre;


    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Price is required")
    private BigDecimal price;
}
