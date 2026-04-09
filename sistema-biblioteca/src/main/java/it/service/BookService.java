package it.service;

/* -------------------------------------------------------------------------- */
/*                                  JAVA UTIL                                 */
/* -------------------------------------------------------------------------- */
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* -------------------------------------------------------------------------- */
/*                              SPRING FRAMEWORK                              */
/* -------------------------------------------------------------------------- */
import org.springframework.stereotype.Service;

/* -------------------------------------------------------------------------- */
/*                                     DTO                                    */
/* -------------------------------------------------------------------------- */
import it.dto.BookCatalogDto;

/* -------------------------------------------------------------------------- */
/*                                   ENTITY                                   */
/* -------------------------------------------------------------------------- */
import it.entity.Author;
import it.entity.Book;
import it.entity.BookName;
import it.entity.Edition;
import it.entity.Publisher;
import it.entity.Category;


/* -------------------------------------------------------------------------- */
/*                                REPOSITORIES                                */
/* -------------------------------------------------------------------------- */
import it.repository.BookRepository;
import it.repository.CategoryRepository;
import it.repository.EditionRepository;
import it.repository.PublisherRepository;
import it.repository.BookNameRepository;
import it.repository.AuthorRepository;

@Service
public class BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final BookNameRepository bookNameRepository;
    private final CategoryRepository categoryRepository;
    private final EditionRepository editionRepository;
        private final PublisherRepository publisherRepository;    

    
    

    public BookService(
        BookRepository bookRepository, 
        EditionRepository editionRepository
        , AuthorRepository authorRepository,
        PublisherRepository publisherRepository,
        CategoryRepository categoryRepository,
        BookNameRepository bookNameRepository
        ) {
        this.bookRepository = bookRepository;
        this.editionRepository = editionRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
        this.bookNameRepository = bookNameRepository;
    }

    public List<BookCatalogDto> getAllBooks(String userRole) {
        List<Book> books = bookRepository.getAllBooks();
        List<Edition> editions = editionRepository.getAllEditions();
        List<Author> authors = authorRepository.getAllAuthors();
        List<Publisher> publishers = publisherRepository.getAllPublishers();
        List<Category> categories = categoryRepository.getAllCategories();
        List<BookName> bookNames = bookNameRepository.getAllBookNames();

        Map<Integer, Edition> editionsById = editions.stream()
            .collect(Collectors.toMap(Edition::getEditionId, e -> e));

        Map<Integer, Author> authorsById = authors.stream()
            .collect(Collectors.toMap(Author::getAuthorId, a -> a));

        Map<Integer, Publisher> publishersById = publishers.stream()
            .collect(Collectors.toMap(Publisher::getPublisherId, p -> p));

        Map<Integer, Category> categoriesById = categories.stream()
            .collect(Collectors.toMap(Category::getCategoryId, c -> c));

        Map<Integer, BookName> bookNamesById = bookNames.stream()
            .collect(Collectors.toMap(BookName::getBooksNameId, b -> b));

        return books.stream()
            .filter(book -> !userRole.equals("role_user") ||
                    "disponibilita".equalsIgnoreCase(book.getStatus()))
            .map(book -> {
                Edition edition = editionsById.get(book.getEditionId());
                Author author = authorsById.get(edition.getAuthorId());
                Publisher publisher = publishersById.get(edition.getPublisherId());
                Category category = categoriesById.get(book.getCategoryId());
                BookName bookName = bookNamesById.get(edition.getBookNameId());

                BookCatalogDto dto = new BookCatalogDto();
                dto.setEditionId(book.getEditionId());
                dto.setBookId(book.getBookId());
                dto.setTitle(bookName.getTitle());
                dto.setAuthorFullName(author.getAuthorName() + " " + author.getAuthorLastName());
                dto.setPublisherName(publisher.getPublisherName());
                dto.setPublishingDate(edition.getPublishingDate());
                dto.setIsbnCode(edition.getIsbn());
                dto.setCategoryName(category.getCategoryName());
                dto.setStatus(book.getStatus());
                return dto;
            })
            .toList();
    }

    public BookCatalogDto getBookById(int bookId) {
        var book = bookRepository.getAllBooks().stream()
            .filter(b -> b.getBookId() == bookId)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        var edition = editionRepository.getEditionById(book.getEditionId());

        BookCatalogDto dto = new BookCatalogDto();
        dto.setEditionId(book.getEditionId());
        dto.setBookId(book.getBookId());
        dto.setTitle(bookRepository.getTitleByID(edition.getBookNameId()));
        dto.setAuthorFullName(bookRepository.getAuthorFullNameByID(edition.getAuthorId()));
        dto.setPublisherName(bookRepository.getPublisherNameByID(edition.getPublisherId()));
        dto.setPublishingDate(edition.getPublishingDate());
        dto.setIsbnCode(edition.getIsbn());
        dto.setCategoryName(bookRepository.getCategoryNameByID(book.getCategoryId()));
        dto.setStatus(book.getStatus());

        return dto;
    }
}
