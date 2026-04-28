package it.dto.join;

import java.time.LocalDate;

import it.dto.EditionDto;
import it.dto.UserDto;

public class RentalRecordJoinDto {
    private int rentalId;
    private EditionDto editionDto;
    private UserDto userDto;
    private LocalDate rentalDate;
    private LocalDate rentalExpired;
    private LocalDate rentalEnded;
    
    public RentalRecordJoinDto() {}

    public RentalRecordJoinDto(int rentalId, EditionDto editionDto, UserDto userDto, LocalDate rentalDate, LocalDate rentalExpired, LocalDate rentalEnded) {
        this.rentalId = rentalId;
        this.editionDto = editionDto;
        this.userDto = userDto;
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

    public EditionDto getEditionDto() {
        return editionDto;
    }

    public void setEditionDto(EditionDto editionDto) {
        this.editionDto = editionDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
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
        return "RentalRecordJoinDto [rentalId=" + rentalId + ", editionDto=" + editionDto + ", userDto=" + userDto
                + ", rentalDate=" + rentalDate + ", rentalExpired=" + rentalExpired + ", rentalEnded=" + rentalEnded
                + "]";
    }

    
}
