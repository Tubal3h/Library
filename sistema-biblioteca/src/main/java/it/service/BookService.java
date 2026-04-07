package it.service;

import java.util.List;

import org.springframework.stereotype.Service;
import it.repository.BookRepository;
import it.dto.BookCatalogDto;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookCatalogDto> getAllBooks() {
        return bookRepository.findAllForBooks();
    }

}
