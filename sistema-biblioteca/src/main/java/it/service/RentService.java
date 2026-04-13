package it.service;

/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import it.dto.RentDto;
import it.entity.RentalRecord;
import it.exception.BookNotFoundException;
import it.repository.RentRecordRepository;

/**
 * Servizio per la gestione dei prestiti dei libri.
 * Coordina le operazioni di noleggio tra il repository e il servizio dei libri.
 */
@Service
public class RentService {

    private final RentRecordRepository rentRepository;
    private final BookService bookService;

    /**
     * Costruttore per RentService.
     *
     * @param rentRepository Repository per i record di noleggio
     * @param bookService    Servizio per la gestione dei libri
     */
    public RentService(RentRecordRepository rentRepository, BookService bookService) {
        this.rentRepository = rentRepository;
        this.bookService = bookService;
    }

    /**
     * Recupera i prestiti attivi di un utente specifico.
     *
     * @param userId ID dell'utente
     * @return Lista di {@link RentDto} rappresentanti i prestiti attivi dell'utente
     */
    public List<RentDto> getRentedBooksByUserId(int userId) {
        return rentRepository.getAllRents().stream()
            .filter(rent -> rent.getUserId() == userId && rent.getRentalEnded() == null)
            .map(this::toRentDto)
            .toList();
    }

    /**
     * Recupera tutti i prestiti attivi nel sistema (per uso amministrativo).
     *
     * @return Lista di {@link RentDto} rappresentanti tutti i noleggi non ancora conclusi
     */
    public List<RentDto> getRentedAllRents() {
        return rentRepository.getAllRents().stream()
            .filter(rent -> rent.getRentalEnded() == null)
            .map(this::toRentDto)
            .toList();
    }

    /**
     * Converte un'entità {@link RentalRecord} in un DTO {@link RentDto},
     * arricchendola con le informazioni del libro associato.
     *
     * @param rent Record di noleggio da convertire
     * @return DTO convertito con i dati del libro inclusi
     * @throws BookNotFoundException se il libro associato al noleggio non viene trovato
     */
    private RentDto toRentDto(RentalRecord rent) {
        RentDto dto = new RentDto();
        dto.setRentId(rent.getRentalId());
        dto.setUserId(rent.getUserId());
        dto.setBookId(rent.getBookId());
        try {
            dto.setBook(bookService.getBookById(rent.getBookId()));
        } catch (BookNotFoundException e) {
            throw new BookNotFoundException("Libro non trovato con l'ID: " + rent.getBookId());
        }
        dto.setRentalDate(rent.getRentalDate());
        dto.setRentalExpired(rent.getRentalExpired());
        dto.setRentalEnded(rent.getRentalEnded());
        return dto;
    }

    /**
     * Recupera il numero totale di noleggi attivi nel sistema.
     *
     * @return Numero totale di prestiti non ancora conclusi
     */
    public int getTotalRents() {
        return rentRepository.countRents();
    }

    /**
     * Recupera il numero di noleggi attivi per un utente specifico.
     *
     * @param userId ID dell'utente
     * @return Numero di prestiti attivi dell'utente specificato
     */
    public int getTotalRentsByUserId(int userId) {
        return rentRepository.countRentsByUserId(userId);
    }

    /**
     * Crea un nuovo noleggio impostando la data odierna come inizio
     * e la scadenza a 14 giorni dalla data corrente.
     *
     * @param rentDto DTO contenente i dati del noleggio (userId, bookId)
     * @throws RuntimeException se si verifica un errore durante la creazione del noleggio
     */
    public void createRental(RentDto rentDto) {
        try {
            RentalRecord rental = new RentalRecord();
            rental.setUserId(rentDto.getUserId());
            rental.setBookId(rentDto.getBookId());
            rental.setRentalDate(LocalDate.now());
            rental.setRentalExpired(LocalDate.now().plusDays(14));
            rental.setRentalEnded(null);
            rentRepository.createRental(rental);
        } catch (Exception e) {
            System.out.println("Eccezione nella repository: " + e.getMessage());
            throw new RuntimeException("Impossibile creare il noleggio in questo momento.");
        }
    }

    /**
     * Aggiorna lo stato di un noleggio registrando la restituzione del libro.
     *
     * @param bookId ID del libro restituito
     * @param rentId ID del record di noleggio da chiudere
     */
    public void updateStatus(int bookId, int rentId) {
        rentRepository.endRental(bookId, rentId);
    }
}
