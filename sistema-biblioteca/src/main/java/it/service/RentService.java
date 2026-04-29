package it.service;

import java.util.ArrayList;

/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.dto.BookDto;
import it.dto.EditionDto;
import it.dto.RentalRecordDto;
import it.dto.BookNameDto;
import it.dto.AuthorDto;
import it.dto.PublisherDto;
import it.dto.CategoryDto;
import it.dto.UserDto;

import it.entity.RentalRecord;
import it.entity.BookName;
import it.entity.Author;
import it.entity.Publisher;
import it.entity.Category;
import it.entity.Book;
import it.entity.User;
import it.entity.Edition;

import it.exception.HistoryNotFoundException;
import it.repository.RentRecordRepository;

/**
 * Servizio per la gestione dei prestiti dei libri.
 * Coordina le operazioni di noleggio tra il repository e il servizio dei libri.
 */
@Service
public class RentService {

    private final RentRecordRepository rentRepository;

    /**
     * Costruttore per RentService.
     *
     * @param rentRepository Repository per i record di noleggio
     */
    public RentService(RentRecordRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    /**
     * Recupera i prestiti attivi di un utente specifico.
     * Esegue una singola query con JOIN per evitare il problema N+1.
     *
     * @param userId ID dell'utente
     * @return Lista di {@link RentDto} rappresentanti i prestiti attivi dell'utente
     */
    @Transactional(readOnly = true)
    private List<RentalRecordDto> getRentedBooksByUserId(int userId) {
        return rentRepository.getActiveRentsByUserId(userId).stream()
                .map(this::toRentalRecordDto)
                .toList();
    }

    /**
     * Recupera tutti i prestiti attivi nel sistema (per uso amministrativo).
     * Esegue una singola query con JOIN per evitare il problema N+1.
     *
     * @return Lista di {@link RentDto} rappresentanti tutti i noleggi non ancora
     *         conclusi
     */

    @Transactional(readOnly = true)
    private List<RentalRecordDto> getRentedAllRents() {
        return rentRepository.getActiveRents().stream()
                .map(this::toRentalRecordDto)
                .toList();
    }

    /**
     * Recupera il numero totale di noleggi attivi nel sistema.
     *
     * @return Numero totale di prestiti non ancora conclusi
     */

    @Transactional(readOnly = true)
    public int getTotalRents() {
        return rentRepository.countRents();
    }

    /**
     * Recupera il numero totale di copie prenotate.
     *
     * @return Numero totale di copie prenotate
     */
    @Transactional(readOnly = true)
    public int getTotalBooksBooked() {
        return rentRepository.countBorrowedBooks();
    }

    /**
     * Recupera il numero di noleggi attivi per un utente specifico.
     *
     * @param userId ID dell'utente
     * @return Numero di prestiti attivi dell'utente specificato
     */

    @Transactional(readOnly = true)
    public int getTotalRentsByUserId(int userId) {
        return rentRepository.countRentsByUserId(userId);
    }

    /**
     * Crea un nuovo noleggio impostando la data odierna come inizio
     * e la scadenza a 14 giorni dalla data corrente.
     *
     * @param rentDto DTO contenente i dati del noleggio (userId, bookId)
     * @throws RuntimeException se si verifica un errore durante la creazione del
     *                          noleggio
     */

    @Transactional
    public void createBookedDate(RentalRecordDto rentDto) throws RuntimeException {
        try {

            User user = new User();
            user.setUserId(rentDto.getUserDto().getUserId());
            user.setUserName(rentDto.getUserDto().getUserName());
            user.setUserLastName(rentDto.getUserDto().getUserLastName());

            Edition edition = new Edition();
            edition.setEditionId(rentDto.getBookDto().getEditionDto().getEditionId());
            edition.setPublishingDate(rentDto.getBookDto().getEditionDto().getPublishingDate());

            BookName bookName = new BookName();
            bookName.setBookNameId(rentDto.getBookDto().getEditionDto().getBookNameDto().getBookNameId());
            bookName.setTitle(rentDto.getBookDto().getEditionDto().getBookNameDto().getTitle());

            Author author = new Author();
            author.setAuthorId(rentDto.getBookDto().getEditionDto().getAuthorDto().getAuthorId());
            author.setAuthorName(rentDto.getBookDto().getEditionDto().getAuthorDto().getAuthorName());
            author.setAuthorLastName(rentDto.getBookDto().getEditionDto().getAuthorDto().getAuthorLastName());

            Publisher publisher = new Publisher();
            publisher.setPublisherId(rentDto.getBookDto().getEditionDto().getPublisherDto().getPublisherId());
            publisher.setPublisherName(rentDto.getBookDto().getEditionDto().getPublisherDto().getPublisherName());

            Category category = new Category();
            category.setCategoryId(rentDto.getBookDto().getEditionDto().getCategoryDto().getCategoryId());
            category.setCategoryName(rentDto.getBookDto().getEditionDto().getCategoryDto().getCategoryName());

            Book book = new Book();
            book.setBookId(rentDto.getBookDto().getBookId());
            book.setEdition(edition);

            RentalRecord rental = new RentalRecord();
            rental.setUser(user);
            rental.setBook(book);
            rental.setBookingDate(LocalDate.now());
            rentRepository.createABookedDate(rental);
            rentRepository.updateStatusToLend(rentDto.getBookDto().getBookId());

        } catch (Exception e) {
            System.out.println("Eccezione nella repository: " + e.getMessage());
            throw new RuntimeException("impossibile effettuare la prenotazione.");
        }
    }

    @Transactional
    public void createRental(RentalRecordDto rentalRecordDto, Boolean confirmed) throws RuntimeException {
        if (rentalRecordDto != null) {
            if (confirmed) {
                try {
                    RentalRecord rental = new RentalRecord();
                    rental.setRentalId(rentalRecordDto.getRentalId());
                    rental.setRentalDate(LocalDate.now());
                    rental.setRentalExpired(LocalDate.now().plusDays(14));
                    ;
                    rentRepository.createRental(rental);
                    rentRepository.updateRentalStatusOk(rentalRecordDto.getBookDto().getBookId());

                } catch (Exception e) {
                    System.out.println("eccezione aggiunta rentalRecord");
                    throw new RuntimeException("impossibile effettuare la prenotazione");
                }
            } else {
                try {
                    Book book = new Book();
                    book.setBookId(rentalRecordDto.getBookDto().getBookId());
                    RentalRecord rental = new RentalRecord();
                    rental.setRentalId(rentalRecordDto.getRentalId());
                    rental.setBook(book);
                    rentRepository.deleteRentalById(rental.getRentalId());
                    rentRepository.updateRentalStatusNotOk(book.getBookId());
                } catch (Exception e) {
                    System.out.println("eccezione aggiunta delete");
                }
            }
        }
    }

    /**
     * Segna il libro come consegnato (inizia il prestito).
     * 
     * @param bookId ID del libro
     * @param rentId ID del noleggio
     */
    @Transactional
    public void deliveredRental(int bookId, int rentId) {
        RentalRecord rental = new RentalRecord();
        rental.setRentalId(rentId);
        rental.setRentalDate(LocalDate.now());
        rental.setRentalExpired(LocalDate.now().plusDays(14));
        rentRepository.createRental(rental);
        rentRepository.updateRentalStatusOk(bookId);
    }

    /**
     * Aggiorna lo stato di un noleggio registrando la restituzione del libro.
     *
     * @param bookId ID del libro restituito
     * @param rentId ID del record di noleggio da chiudere
     */
    @Transactional
    public void updateStatus(int bookId, int rentId) {
        rentRepository.endRental(bookId, rentId);
    }

    /**
     * Recupera una lista di prestiti filtrata per nome del libro.
     * 
     * @param search   Il termine di ricerca per il nome del libro
     * @param userRole Il ruolo dell'utente che effettua la ricerca
     * @param userId   L'ID dell'utente che effettua la ricerca
     * @return Lista di RentDto contenente le informazioni condensate dei prestiti
     *         filtrati per nome del libro
     */

    @Transactional(readOnly = true)
    public List<RentalRecordDto> getBookListByName(String search, String userRole, int userId) {
        List<RentalRecordDto> myList = new ArrayList<>();

        if (userRole.equals("role_user")) {
            myList = getRentedBooksByUserId(userId);
        } else {
            myList = getRentedAllRents();
        }

        List<RentalRecordDto> filteredList = new ArrayList<>();
        if (search != null && !search.isBlank()) {
            String[] arraySearch = search.toLowerCase().trim().split("\\s+");
            for (RentalRecordDto rent : myList) {
                String userName = rent.getUserDto().getUserName();
                String userLastName = rent.getUserDto().getUserLastName();
                String title = rent.getBookDto().getEditionDto().getBookNameDto().getTitle();
                String bookAuthorName = rent.getBookDto().getEditionDto().getAuthorDto().getAuthorName();
                String bookAuthorLastName = rent.getBookDto().getEditionDto().getAuthorDto().getAuthorLastName();
                String bookPublisherName = rent.getBookDto().getEditionDto().getPublisherDto().getPublisherName();
                String bookCategoryName = rent.getBookDto().getEditionDto().getCategoryDto().getCategoryName();
                String finalString = (userName + " " + userLastName + " " + title + " " + bookAuthorName + " "
                        + bookAuthorLastName + " " + bookPublisherName + " " + bookCategoryName).toLowerCase();
                System.out.println("finalString: " + finalString);
                boolean allMatch = true;

                for (String s : arraySearch) {
                    System.out.println("nome " + userName);
                    System.out.println("nome autore: ");
                    System.out.println("stringa " + arraySearch[0]);
                    if (!(finalString.contains(s))) {
                        allMatch = false;
                        break;
                    }
                }
                if (allMatch) {
                    filteredList.add(rent);
                }
            }

        } else {
            return myList;
        }
        if (filteredList.isEmpty() || filteredList == null) {
            return myList;
        } else {
            return filteredList;
        }
    }

    @Transactional(readOnly = true)
    public List<RentalRecordDto> getBookRecords(int bookId) throws HistoryNotFoundException {
        
        return rentRepository.getBookRecords(bookId).stream()
                .map(this::toRentalRecordDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RentalRecordDto> getUserRecords(int userId) throws HistoryNotFoundException {
        return rentRepository.getUserRecords(userId).stream()
                .map(this::toRentalRecordDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RentalRecordDto> bookHistory(Integer bookId) throws HistoryNotFoundException {
        List<RentalRecordDto> myList = new ArrayList<>();
        if (bookId != null) {
            myList = rentRepository.getBookRecords(bookId).stream()
                .map(this::toRentalRecordDto)
                .toList();
        }
        return myList;
    }

        /**
     * Converte un'entità {@link RentalRecordJoin} in un DTO {@link RentDto}.
     * Tutti i dati del libro sono già inclusi nell'entità senza ulteriori query.
     *
     * @param rent Record di noleggio aggregato da convertire
     * @return DTO convertito con i dati del libro inclusi
     */
    private RentalRecordDto toRentalRecordDto(RentalRecord rent) {
        UserDto user = new UserDto();
        user.setUserName(rent.getUser().getUserName());
        user.setUserLastName(rent.getUser().getUserLastName());

        BookNameDto bookNameDto = new BookNameDto();
        bookNameDto.setTitle(rent.getBook().getEdition().getBookName().getTitle());

        AuthorDto authorDto = new AuthorDto();
        authorDto.setAuthorName(rent.getBook().getEdition().getAuthor().getAuthorName());
        authorDto.setAuthorLastName(rent.getBook().getEdition().getAuthor().getAuthorLastName());

        PublisherDto publisherDto = new PublisherDto();
        publisherDto.setPublisherName(rent.getBook().getEdition().getPublisher().getPublisherName());

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryName(rent.getBook().getEdition().getCategory().getCategoryName());

        EditionDto edition = new EditionDto();
        edition.setEditionId(rent.getBook().getEdition().getEditionId());
        edition.setBookNameDto(bookNameDto);
        edition.setAuthorDto(authorDto);
        edition.setPublisherDto(publisherDto);
        edition.setCategoryDto(categoryDto);
        edition.setPublishingDate(rent.getBook().getEdition().getPublishingDate());
        edition.setIsbn(rent.getBook().getEdition().getIsbn());

        BookDto book = new BookDto();
        book.setBookId(rent.getBook().getBookId());
        book.setEditionDto(edition);
        book.setStatus(rent.getBook().getStatus());

        RentalRecordDto rentalRecordDto = new RentalRecordDto();
        rentalRecordDto.setRentalId(rent.getRentalId());
        rentalRecordDto.setUserDto(user);
        rentalRecordDto.setBookDto(book);
        rentalRecordDto.setRentalDate(rent.getRentalDate());
        rentalRecordDto.setRentalExpired(rent.getRentalExpired());
        rentalRecordDto.setRentalEnded(rent.getRentalEnded());
        rentalRecordDto.setBookingDate(rent.getBookingDate());
        return rentalRecordDto;
    }

}
