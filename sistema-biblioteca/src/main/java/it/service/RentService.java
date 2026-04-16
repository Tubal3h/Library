package it.service;

import java.util.ArrayList;

/* -------------------------------------------------------------------------- */
/*                                   SERVICE                                  */
/* -------------------------------------------------------------------------- */

import java.util.List;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import it.dto.BookDto;
import it.dto.RentDto;
import it.entity.RentalRecord;
import it.entity.RentalRecordJoin;
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
    private List<RentDto> getRentedBooksByUserId(int userId) {
        return rentRepository.getActiveRentsByUserId(userId).stream()
            .map(this::toRentDto)
            .toList();
    }

    /**
     * Recupera tutti i prestiti attivi nel sistema (per uso amministrativo).
     * Esegue una singola query con JOIN per evitare il problema N+1.
     *
     * @return Lista di {@link RentDto} rappresentanti tutti i noleggi non ancora conclusi
     */
    private List<RentDto> getRentedAllRents() {
        return rentRepository.getActiveRents().stream()
            .map(this::toRentDto)
            .toList();
    }

    /**
     * Converte un'entità {@link RentalRecordJoin} in un DTO {@link RentDto}.
     * Tutti i dati del libro sono già inclusi nell'entità senza ulteriori query.
     *
     * @param rent Record di noleggio aggregato da convertire
     * @return DTO convertito con i dati del libro inclusi
     */
    private RentDto toRentDto(RentalRecordJoin rent) {
        BookDto book = new BookDto();
        book.setBookId(rent.getBookId());
        book.setTitle(rent.getBookName());
        book.setAuthorFullName(rent.getAuthorFullName());
        book.setPublisherName(rent.getPublisherName());
        book.setPublishingDate(rent.getPublicationDate());
        book.setCategoryName(rent.getCategoryName());
        book.setIsbnCode(rent.getIsbnCode());

        RentDto dto = new RentDto();
        dto.setRentId(rent.getRentalId());
        dto.setUserId(rent.getUserId());
        dto.setBookId(rent.getBookId());
        dto.setBook(book);
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

    /**
     * Recupera una lista di prestiti filtrata per nome del libro.
     * 
     * @param search Il termine di ricerca per il nome del libro
     * @param userRole Il ruolo dell'utente che effettua la ricerca
     * @param userId L'ID dell'utente che effettua la ricerca
     * @return Lista di RentDto contenente le informazioni condensate dei prestiti filtrati per nome del libro
     */
    public List<RentDto> getBookListByName(String search, String userRole, int userId) {
		List<RentDto> myList = new ArrayList<>();
        if(userRole.equals("role_user")) {
			myList = getRentedBooksByUserId(userId);
		}else {
            myList = getRentedAllRents();
        }
		List<RentDto> filteredList = new ArrayList<>();
		if(search != null && !search.isBlank()) {
			for(RentDto rent : myList) {
				if(rent.getBook().getTitle().replaceAll("\\s+","").toLowerCase().equals(search.replaceAll("\\s+","").toLowerCase())) {
					filteredList.add(rent);
				}
			}	
		}
		if(filteredList.isEmpty() || filteredList == null) {
			return myList;
		}else {
			return filteredList;
		}
	}
}
