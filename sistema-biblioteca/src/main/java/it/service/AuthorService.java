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
     * Aggiorna i dati di un autore.
     * 
     * @param author L'autore da aggiornare
     */

    public void updateAuthor(AuthorDto author) {
        Author authorEntity = toAuthorEntity(author);
    	authorRepository.updateAuthor(authorEntity);
    }

    /**
     * Verifica se un autore esiste nel database.
     * 
     * @param author L'autore da verificare
     * @return True se l'autore esiste, false altrimenti
     */
    public boolean isAuthorPresent(AuthorDto authorDto) {
        if (authorDto == null || authorDto.getAuthorName() == null || authorDto.getAuthorLastName() == null) {
            throw new IllegalArgumentException("L'autore non può essere null");
        }

        if(authorDto.getAuthorName().isBlank() || authorDto.getAuthorLastName().isBlank()) {
            throw new IllegalArgumentException("L'autore non può essere vuoto");
        }

        Author author = toAuthorEntity(authorDto);

        return authorRepository.isAuthorPresent(author.getAuthorName(), author.getAuthorLastName());
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

    /**
     * Converte un DTO AuthorDto in un'entità Author.
     * 
     * @param authorDto Il DTO da convertire
     * @return L'entità Author corrispondente
     */

    private Author toAuthorEntity(AuthorDto authorDto) {
        Author author = new Author();
        author.setAuthorId(authorDto.getAuthorId());
        author.setAuthorName(authorDto.getAuthorName());
        author.setAuthorLastName(authorDto.getAuthorLastName());
        return author;
    }
}
