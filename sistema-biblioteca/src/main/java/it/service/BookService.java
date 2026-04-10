package it.service;

/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;
import org.springframework.stereotype.Service;

import it.dto.BookDto;

import it.entity.BookJoin;

import it.repository.BookRepository;

/**
 * Servizio per la gestione del catalogo dei libri.
 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    /**
     * Costruttore con iniezione delle dipendenze per BookService.
     */
    public BookService(
        BookRepository bookRepository
    ) {
        this.bookRepository = bookRepository;
    }

    /**
     * Recupera tutti i libri nel sistema.
     * 
     * @param userRole Ruolo dell'utente
     * @return Lista di DTO dei libri
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
     * Recupera i dettagli di un singolo libro tramite ID.
     * 
     * @param bookId ID del libro
     * @return DTO del libro trovato
     * @throws RuntimeException se il libro non viene trovato
     */
    public BookDto getBookById(int bookId) {
        var book = bookRepository.getAllBooks().stream()
            .filter(b -> b.getBookId() == bookId)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

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
        System.out.println("bookservice dto: " + dto);
        return dto;
    }

    /**
     * Recupera il numero totale di libri nel sistema.
     * 
     * @return Il numero totale di libri nel sistema
     */

    public int getTotalCountBooks() {
        int books = bookRepository.countBooks();
        return books;
    }

    
}

