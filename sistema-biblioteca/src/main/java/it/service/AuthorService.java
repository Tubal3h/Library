package it.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.dto.AuthorDto;
import it.repository.AuthorRepository;
import it.entity.Author;

@Service
public class AuthorService {
    
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDto> getAllAuthors() {
        return authorRepository.getAllAuthors().stream()
            .map(this::toAuthorDto)
            .toList();
    }

    private AuthorDto toAuthorDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setAuthorId(author.getAuthorId());
        dto.setAuthorName(author.getAuthorName());
        dto.setAuthorLastName(author.getAuthorLastName());
        return dto;
    }
}
