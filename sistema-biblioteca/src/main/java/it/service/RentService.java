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
        
        return rentRepository.findRentsByUserId(userId);
    }

}
