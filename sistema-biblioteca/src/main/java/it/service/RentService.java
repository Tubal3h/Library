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
import it.entity.join.RentalRecordJoin;
import it.entity.BookName;
import it.entity.Author;
import it.entity.Publisher;
import it.entity.Category;
import it.entity.Book;
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
     * @return Lista di {@link RentDto} rappresentanti tutti i noleggi non ancora conclusi
     */
    
    @Transactional(readOnly = true)
    private List<RentalRecordDto> getRentedAllRents() {
        return rentRepository.getActiveRents().stream()
            .map(this::toRentalRecordDto)
            .toList();
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
        authorDto.setAuthorFullName(rent.getBook().getEdition().getAuthor().getAuthorFullName());

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
        book.setPublisherName(rent.getPublisherName());
        book.setPublishingDate(rent.getPublicationDate());
        book.setCategoryName(rent.getCategoryName());
        book.setIsbn(rent.getIsbnCode());
        book.setStatus(rent.getStatus());

        RentDto dto = new RentDto();
        dto.setRentId(rent.getRentalId());
        dto.setUserId(rent.getUserId());
        dto.setBookId(rent.getBookId());
        dto.setBookHistory(book);
        dto.setUser(user);
        dto.setRentalDate(rent.getRentalDate());
        dto.setRentalExpired(rent.getRentalExpired());
        dto.setRentalEnded(rent.getRentalEnded());
        dto.setBookingDate(rent.getBookingDate());
        return dto;
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
     * @throws RuntimeException se si verifica un errore durante la creazione del noleggio
     */
    

    @Transactional
    public void createBookedDate(RentalRecordDto rentDto) throws RuntimeException {
        try {
            RentalRecord rental = new RentalRecord();
            rental.setUserId(rentDto.getUserDto().getUserId());
            rental.setBookId(rentDto.getBookDto().getBookId());
            rental.setBookingDate(LocalDate.now());
            rentRepository.createABookedDate(rental);
            rentRepository.updateStatusToLend(rentDto.getBookId());
        
        } catch (Exception e) {
            System.out.println("Eccezione nella repository: " + e.getMessage());
            throw new RuntimeException("impossibile effettuare la prenotazione.");
        }
    }
    
    @Transactional
    public void createRental(RentalRecordDto rentDto, Boolean confirmed) throws RuntimeException {
    	if(rentDto != null) {
    		if(confirmed) {
    			try {
    				RentalRecord rental = new RentalRecord();
    				rental.setRentalId(rentDto.getRentId());
    				rental.setRentalDate(LocalDate.now());
    				rental.setRentalExpired(LocalDate.now().plusDays(14));;
    				rentRepository.createRental(rental);
    				rentRepository.updateRentalStatusOk(rentDto.getBookId());
    				
    			}catch(Exception e) {
    				System.out.println("eccezione aggiunta rentalRecord");
    				throw new RuntimeException("impossibile effettuare la prenotazione");
    			}
    		}else {
    			try {
    				RentalRecord rental = new RentalRecord();
    				rental.setRentalId(rentDto.getRentId());
    				rental.setBookId(rentDto.getBookId());
    				rentRepository.deleteRentalById(rental.getRentalId());
    				rentRepository.updateRentalStatusNotOk(rental.getBookId());
    			}catch(Exception e) {
    				System.out.println("eccezione aggiunta delete");
    			}
    		}
    	}
    }
    
    /**
     * Segna il libro come consegnato (inizia il prestito).
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
     * @param search Il termine di ricerca per il nome del libro
     * @param userRole Il ruolo dell'utente che effettua la ricerca
     * @param userId L'ID dell'utente che effettua la ricerca
     * @return Lista di RentDto contenente le informazioni condensate dei prestiti filtrati per nome del libro
     */
    
    @Transactional(readOnly = true)
    public List<RentalRecordDto> getBookListByName(String search, String userRole, int userId) {
		List<RentalRecordDto> myList = new ArrayList<>();
		
        if(userRole.equals("role_user")) {
			myList = getRentedBooksByUserId(userId);
		}else {
            myList = getRentedAllRents();
        }
		
        List<RentalRecordDto> filteredList = new ArrayList<>();
		if(search != null && !search.isBlank()) {
			String [] arraySearch = search.toLowerCase().trim().split("\\s+");
			for(RentalRecordDto rent : myList) {
				String userName = rent.getUser().getUserName();
				String userLastName = rent.getUser().getUserLastName();
				String title = rent.getBookHistory().getTitle();
                String bookAuthorFullName = rent.getBookHistory().getAuthorFullName();
                String bookPublisher = rent.getBookHistory().getPublisherName();
                String bookCategory = rent.getBookHistory().getCategoryName();
				String finalString = (userName + " " + userLastName + " " +  title + " " +  bookAuthorFullName + " " +  bookPublisher + " " +  bookCategory).toLowerCase();
				System.out.println("finalString: " + finalString);
                boolean allMatch = true;

				for(String s : arraySearch) {
					System.out.println("nome " + userName);
					System.out.println("nome autore: ");
					System.out.println("stringa " + arraySearch[0]);
					if(!(finalString.contains(s))) {
						allMatch = false;
						break;
					}
				}
				if(allMatch) {					
					filteredList.add(rent);
				}	
			}
		
		}else {
			return myList;
		}
		if(filteredList.isEmpty() || filteredList == null) {
			return myList;
		}else {
			return filteredList;
		}
	}
    @Transactional(readOnly = true)
    public List<RentalRecordDto> getBookRecords(int bookId) throws HistoryNotFoundException {
        return rentRepository.getBookRecords(bookId);
    }

    @Transactional(readOnly = true)
    public List<RentalRecordDto> getUserRecords(int userId) throws HistoryNotFoundException {
        return rentRepository.getUserRecords(userId);
    }

    @Transactional(readOnly = true)
    public List<RentalRecordDto> bookHistory(Integer bookId) throws HistoryNotFoundException {
        List<RentalRecordDto> myList = new ArrayList<>();
        if(bookId != null) {
            myList = rentRepository.getBookRecords(bookId);
        }
        return myList;
    }
}
