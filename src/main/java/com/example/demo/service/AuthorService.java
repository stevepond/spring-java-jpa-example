package com.example.demo.service;

import com.example.demo.dto.AuthorDto;
import com.example.demo.dto.BookDto;
import com.example.demo.entity.Author;
import com.example.demo.entity.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public AuthorDto createAuthor(String name) {
        Author author = new Author(name);
        Author saved = authorRepository.save(author);
        return toDto(saved);
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<AuthorDto> getAuthor(Long id) {
        return authorRepository.findById(id).map(this::toDto);
    }

    public BookDto addBook(Long authorId, String title) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
        Book book = new Book(title, author);
        author.getBooks().add(book);
        Book saved = bookRepository.save(book);
        return new BookDto(saved.getId(), saved.getTitle());
    }

    private AuthorDto toDto(Author author) {
        AuthorDto dto = new AuthorDto(author.getId(), author.getName());
        dto.setBooks(author.getBooks().stream()
                .map(b -> new BookDto(b.getId(), b.getTitle()))
                .collect(Collectors.toList()));
        return dto;
    }
}
