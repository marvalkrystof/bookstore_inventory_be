package com.krystofmarval.bookstoreinventory.repository;

import com.krystofmarval.bookstoreinventory.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
