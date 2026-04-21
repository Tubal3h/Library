package it.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.dto.BookDto;
import it.service.BookService;

/**
 * Controller REST per la gestione delle richieste AJAX relative ai libri.
 */
@RestController
@RequestMapping("/api/books")
public class RestBookController {

    private final BookService bookService;

    public RestBookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Endpoint per recuperare tutte le copie di un'edizione specifica.
     * 
     * @param editionId L'ID dell'edizione
     * @param includeDeleted Se includere le copie eliminate (default false)
     * @return Una lista di BookDto in formato JSON
     */
    @GetMapping("/edition/{editionId}")
    public List<BookDto> getBooksByEdition(
            @PathVariable int editionId, 
            @RequestParam(required = false, defaultValue = "false") boolean includeDeleted) {
        return bookService.getBooksByEditionId(editionId, includeDeleted);
    }
}
