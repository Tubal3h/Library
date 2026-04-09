package it.service;

import java.util.List;

import it.dto.RentDto;
import it.model.RentalRecord;
import it.repository.RentRepository;
import it.repository.BookRepository;

import org.springframework.stereotype.Service;

@Service
public class RentService {
    private final RentRepository rentRepository;

    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }
    
    public List<RentDto> getRentedBooksByUserId(int userId) {
        List<RentalRecord> rents = rentRepository.findAllRents();
        System.out.println("All rents: " + rents);
        List<RentDto> ActiveRents = rents.stream()
            .filter(rent -> rent.getUserId() == userId && rent.getRentalEnded() == null)
            .map(rent -> {
                RentDto dto = new RentDto();
                dto.setRentId(rent.getRentalId());
                dto.setUserId(rent.getUserId());
                dto.setBookId(rent.getBookId());
                dto.setBook(
                    

                );
                dto.setRentalDate(rent.getRentalDate());
                dto.setRentalExpired(rent.getRentalExpired());
                dto.setRentalEnded(rent.getRentalEnded());
                return dto;
            })
            .toList();
        System.out.println("Active rents for user " + userId + ": " + ActiveRents);
        return ActiveRents;
    }

}
