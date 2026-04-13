package it.service;

/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.stereotype.Service;

import it.dto.BookDto;
import it.entity.BookJoin;
import it.exception.BookNotFoundException;
import it.repository.BookRepository;

/**
 * Servizio per la gestione del catalogo dei libri.
 * Fornisce le operazioni di recupero e filtro dei libri in base al ruolo utente.
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    /**
     * Costruttore per BookService.
     *
     * @param bookRepository Repository per l'accesso ai dati dei libri
     */
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Recupera tutti i libri visibili per l'utente in base al suo ruolo.
     * Gli utenti con ruolo {@code role_user} vedono solo i libri disponibili;
     * gli amministratori vedono tutti i libri indipendentemente dallo stato.
     *
     * @param userRole Ruolo dell'utente (es. role_user, role_admin)
     * @return Lista di {@link BookDto} dei libri accessibili all'utente
     */
    public List<BookDto> getAllBooks(String userRole) {
        List<BookJoin> repoBook = bookRepository.getAllBooks();
        return repoBook.stream()
            .filter(book -> !userRole.equals("role_user") ||
                    "disponibilita".equalsIgnoreCase(book.getStatus()))
            .map(book -> {
                BookDto dto = new BookDto();
                dto.setEditionId(book.getEditionId());
                dto.setBookId(book.getBookId());
                dto.setTitle(book.getBookName());
                dto.setAuthorFullName(book.getAuthorFullName());
                dto.setPublisherName(book.getPublisherName());
                dto.setPublishingDate(book.getPublicationDate());
                dto.setIsbnCode(book.getIsbnCode());
                dto.setCategoryName(book.getCategoryName());
                dto.setStatus(book.getStatus());
                return dto;
            })
            .toList();
    }

    /**
     * Recupera i dettagli di un singolo libro tramite il suo ID.
     *
     * @param bookId ID del libro da cercare
     * @return {@link BookDto} del libro trovato
     * @throws BookNotFoundException se nessun libro corrisponde all'ID specificato
     */
    public BookDto getBookById(int bookId) {
        var book = bookRepository.getAllBooks().stream()
            .filter(b -> b.getBookId() == bookId)
            .findFirst()
            .orElseThrow(() -> new BookNotFoundException("Libro non trovato con l'ID: " + bookId));

        BookDto dto = new BookDto();
        dto.setEditionId(book.getEditionId());
        dto.setBookId(book.getBookId());
        dto.setTitle(book.getBookName());
        dto.setAuthorFullName(book.getAuthorFullName());
        dto.setPublisherName(book.getPublisherName());
        dto.setPublishingDate(book.getPublicationDate());
        dto.setIsbnCode(book.getIsbnCode());
        dto.setCategoryName(book.getCategoryName());
        dto.setStatus(book.getStatus());
        return dto;
    }

    /**
     * Recupera il numero totale di libri fisici presenti nel sistema.
     *
     * @return Numero totale di libri nel database
     */
    public int getTotalCountBooks() {
        return bookRepository.countBooks();
    }
}
