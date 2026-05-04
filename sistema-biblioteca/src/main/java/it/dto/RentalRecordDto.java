package it.dto;

import java.time.LocalDate;


/**
 * Data Transfer Object per la gestione dei dati di RentalRecordDto.
 */
public class RentalRecordDto {
    private int rentalId;
    private BookDto bookDto;
    private UserDto userDto;
    private LocalDate bookingDate;
    private LocalDate rentalDate;
    private LocalDate rentalExpired;
    private LocalDate rentalEnded;
    
    public RentalRecordDto() {}

    public RentalRecordDto(int rentalId, LocalDate bookingDate, LocalDate rentalDate, LocalDate rentalExpired, LocalDate rentalEnded, UserDto userDto, BookDto bookDto) {
        this.rentalId = rentalId;
        this.bookDto = bookDto;
        this.userDto = userDto;
        this.bookingDate = bookingDate;
        this.rentalDate = rentalDate;
        this.rentalExpired = rentalExpired;
        this.rentalEnded = rentalEnded;
    }

    public int getRentalId() {
        return rentalId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public BookDto getBookDto() {
        return bookDto;
    }

    public void setBookDto(BookDto bookDto) {
        this.bookDto = bookDto;
    }

    public  UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getRentalExpired() {
        return rentalExpired;
    }

    public void setRentalExpired(LocalDate rentalExpired) {
        this.rentalExpired = rentalExpired;
    }

    public LocalDate getRentalEnded() {
        return rentalEnded;
    }

    public void setRentalEnded(LocalDate rentalEnded) {
        this.rentalEnded = rentalEnded;
    }

    @Override
    public String toString() {
        return "RentalRecordJoinDto [rentalId=" + rentalId + ", bookDto=" + bookDto + ", userDto=" + userDto
                + ", bookingDate=" + bookingDate + ", rentalDate=" + rentalDate + ", rentalExpired=" + rentalExpired + ", rentalEnded=" + rentalEnded
                + "]";
    }

    
}
