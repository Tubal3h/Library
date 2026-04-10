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
 */
@Service
public class RentService {
    private final RentRecordRepository rentRepository;
    private final BookService bookService;

    /**
     * Costruttore per RentService.
     * 
     * @param rentRepository Repository per i record di noleggio
     * @param bookService Servizio per la gestione dei libri
     */
    public RentService(RentRecordRepository rentRepository, BookService bookService) {
        this.rentRepository = rentRepository;
        this.bookService = bookService;
    }
    
    /**
     * Recupera i prestiti attivi di un utente specifico.
     * 
     * @param userId ID dell'utente
     * @return Lista di DTO rappresentanti i prestiti attivi
     */
    public List<RentDto> getRentedBooksByUserId(int userId) {
        return rentRepository.getAllRents().stream()
            .filter(rent -> rent.getUserId() == userId && rent.getRentalEnded() == null)
            .map(this::toRentDto)
            .toList();
    }

    /**
     * Converte un'entità RentalRecordView in un DTO RentDto.
     * 
     * @param rent Record di noleggio da convertire.
     * @return DTO convertito
     */
    private RentDto toRentDto(RentalRecord rent) {
        System.out.println("rent: " + rent);
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
        System.out.println("rentservicedto: " + dto);
        return dto;
    }

    /**
     * Recupera il numero totale di record di noleggio nel sistema.
     * 
     * @return Il numero totale di record di noleggio nel sistema
     */

    public int getTotalRents() {
        int rents = rentRepository.countRents();
        return rents;
    }   

    /**
     * Recupera il numero totale di record di noleggio nel sistema per un utente specifico.
     * 
     * @param userId ID dell'utente
     * @return Il numero totale di record di noleggio nel sistema per un utente specifico
     */

    public int getTotalRentsByUserId(int userId) {
        int rents = rentRepository.countRentsByUserId(userId);
        return rents;
    }

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
}

