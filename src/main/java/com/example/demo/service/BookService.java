package com.example.demo.service;

import com.example.demo.dto.BookDto;
import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(b -> new BookDto(b.getId(), b.getTitle()))
                .collect(Collectors.toList());
    }
}
