package com.krystofmarval.bookstoreinventory.controller;

import com.krystofmarval.bookstoreinventory.model.Author;
import com.krystofmarval.bookstoreinventory.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void shouldSaveAndRetrieveAuthor() {
        Author author = new Author();
        author.setName("George Orwell");

        Author savedAuthor = authorRepository.save(author);
        assertNotNull(savedAuthor.getId());

        Author foundAuthor = authorRepository.findById(savedAuthor.getId()).orElse(null);
        assertNotNull(foundAuthor);
        assertEquals("George Orwell", foundAuthor.getName());
    }
}
