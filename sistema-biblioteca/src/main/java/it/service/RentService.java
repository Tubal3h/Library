package it.service;

/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.stereotype.Service;

import it.dto.RentDto;
import it.entity.RentalRecord;
import it.repository.RentRepository;

/**
 * Servizio per la gestione dei prestiti dei libri.
 */
@Service
public class RentService {
    private final RentRepository rentRepository;
    private final BookService bookService;

    /**
     * Costruttore per RentService.
     * 
     * @param rentRepository Repository per i record di noleggio
     * @param bookService Servizio per la gestione dei libri
     */
    public RentService(RentRepository rentRepository, BookService bookService) {
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
     * Converte un'entità RentalRecord in un DTO RentDto.
     * 
     * @param rent Record di noleggio da convertire
     * @return DTO convertito
     */
    private RentDto toRentDto(RentalRecord rent) {
        RentDto dto = new RentDto();
        dto.setRentId(rent.getRentalId());
        dto.setUserId(rent.getUserId());
        dto.setBookId(rent.getBookId());
        dto.setBook(bookService.getBookById(rent.getBookId()));
        dto.setRentalDate(rent.getRentalDate());
        dto.setRentalExpired(rent.getRentalExpired());
        dto.setRentalEnded(rent.getRentalEnded());
        return dto;
    }
}

