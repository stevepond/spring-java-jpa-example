package com.example.demo.controller;

import com.example.demo.dto.AuthorDto;
import com.example.demo.dto.BookDto;
import com.example.demo.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto createAuthor(@RequestBody AuthorDto dto) {
        return authorService.createAuthor(dto.getName());
    }

    @GetMapping
    public List<AuthorDto> getAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthor(@PathVariable Long id) {
        return authorService.getAuthor(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    @PostMapping("/{id}/books")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@PathVariable Long id, @RequestBody BookDto dto) {
        return authorService.addBook(id, dto.getTitle());
    }
}
