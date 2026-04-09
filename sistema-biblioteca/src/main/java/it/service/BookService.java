package it.service;

import java.util.List;

import org.springframework.stereotype.Service;

import it.dto.BookCatalogDto;
import it.model.Edition;
import it.repository.BookRepository;
import it.repository.EditionRepository;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final EditionRepository editionRepository;

    public BookService(BookRepository bookRepository, EditionRepository editionRepository) {
        this.bookRepository = bookRepository;
        this.editionRepository = editionRepository;
    }

    public List<BookCatalogDto> getAllBooks(String userRole) {
        return bookRepository.GetAllBooks().stream()
            .filter(book -> {
                if ("user_user".equals(userRole)) {
                    return "disponibile".equalsIgnoreCase(book.getStatus());
                }
                return true; // admin e altri ruoli vedono tutto
            })
            .map(book -> {
                Edition edition = editionRepository.getEditionById(book.getBookId());

                BookCatalogDto dto = new BookCatalogDto();
                dto.setEditionId(book.getEditionId());
                dto.setBookId(book.getBookId());
                dto.setTitle(bookRepository.GetTitleByID(edition.getBookNameId()));
                dto.setAuthorFullName(bookRepository.GetAuthorFullNameByID(edition.getAuthorId()));
                dto.setPublisherName(bookRepository.GetPublisherNameByID(edition.getPublisherId()));
                dto.setPublishingDate(edition.getPublishingDate());
                dto.setIsbnCode(edition.getIsbn());
                dto.setCategoryName(bookRepository.GetCategoryNameByID(book.getCategoryId()));
                dto.setStatus(book.getStatus());

                return dto;
            })
            .toList();
    }

}
