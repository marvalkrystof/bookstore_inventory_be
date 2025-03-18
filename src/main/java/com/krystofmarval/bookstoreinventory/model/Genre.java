package com.krystofmarval.bookstoreinventory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "books")
public class Genre implements ModelBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    @NotBlank(message = "Name is required")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "genre")
    private List<Book> books = new ArrayList<>();
}
