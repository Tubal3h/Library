package it.service;

import java.util.List;
import it.model.dto.RentDto;
import it.repository.RentRepository;

import org.springframework.stereotype.Service;

@Service
public class RentService {
    private final RentRepository rentRepository;

    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }
    
    public List<RentDto> getRentedBooksByUserId(int userId) {
        List<RentDto> rents = rentRepository.findAllRents();
        System.out.println("All rents: " + rents);
        List<RentDto> ActiveRents = rents.stream()
            .filter(rent -> rent.getRentalEnded() == null && rent.getUserId() == userId)
            .toList();
        System.out.println("Active rents for user " + userId + ": " + ActiveRents);
        return ActiveRents;
    }

}
