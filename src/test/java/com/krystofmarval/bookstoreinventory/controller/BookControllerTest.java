package com.krystofmarval.bookstoreinventory.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krystofmarval.bookstoreinventory.error.util.ErrorResponseUtil;
import com.krystofmarval.bookstoreinventory.model.Book;
import com.krystofmarval.bookstoreinventory.security.filter.JwtExceptionFilter;
import com.krystofmarval.bookstoreinventory.security.filter.JwtRequestFilter;
import com.krystofmarval.bookstoreinventory.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private ErrorResponseUtil errorResponseUtil;

    @MockitoBean
    private JwtExceptionFilter jwtExceptionFilter;

    @MockitoBean
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateBookSuccessfully() throws Exception {
        Book book = new Book();
        book.setTitle("1984");

        Mockito.when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .with(csrf())  // Disable CSRF protection
                        .with(user("admin").roles("ADMIN"))  // Simulate authenticated user
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }
}
