package it.service;

import java.util.List;
import org.springframework.stereotype.Service;

import it.dto.AuthorDto;
import it.repository.AuthorRepository;
import it.entity.Author;

/**
 * Servizio per la gestione degli autori dei libri.
 * Fornisce metodi per il recupero e la mappatura degli autori in DTO.
 */
@Service
public class AuthorService {
    
    private final AuthorRepository authorRepository;

    /**
     * Costruttore per AuthorService.
     * 
     * @param authorRepository Repository per l'accesso ai dati degli autori
     */
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Recupera la lista di tutti gli autori registrati nel sistema.
     * 
     * @return Lista di AuthorDto
     */
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.getAllAuthors().stream()
            .map(this::toAuthorDto)
            .toList();
    }

    /**
     * Converte un'entità Author in un DTO AuthorDto.
     * 
     * @param author L'entità da convertire
     * @return Il DTO corrispondente
     */
    private AuthorDto toAuthorDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setAuthorId(author.getAuthorId());
        dto.setAuthorName(author.getAuthorName());
        dto.setAuthorLastName(author.getAuthorLastName());
        return dto;
    }
}
