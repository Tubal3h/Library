package it.service;

import java.util.List;

import it.dto.RentDto;
import it.entity.RentalRecord;
import it.repository.RentRepository;


import org.springframework.stereotype.Service;

@Service
public class RentService {
    private final RentRepository rentRepository;
    private final BookService bookService;

    public RentService(RentRepository rentRepository, BookService bookService) {
        this.rentRepository = rentRepository;
        this.bookService = bookService;
    }
    
    public List<RentDto> getRentedBooksByUserId(int userId) {
        return rentRepository.getAllRents().stream()
            .filter(rent -> rent.getUserId() == userId && rent.getRentalEnded() == null)
            .map(this::toRentDto)
            .toList();
    }

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
