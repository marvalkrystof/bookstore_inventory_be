package com.krystofmarval.bookstoreinventory.repository;

import com.krystofmarval.bookstoreinventory.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
