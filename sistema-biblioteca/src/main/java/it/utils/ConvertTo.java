package it.utils;

import it.dto.AuthorDto;
import it.dto.BookDto;
import it.dto.BookNameDto;
import it.dto.CategoryDto;
import it.dto.EditionDto;
import it.dto.PublisherDto;
import it.dto.RentalRecordDto;
import it.dto.UserDto;
import it.entity.Author;
import it.entity.Book;
import it.entity.BookName;
import it.entity.Category;
import it.entity.Edition;
import it.entity.Publisher;
import it.entity.RentalRecord;
import it.entity.User;

import org.springframework.stereotype.Component;


@Component
public class ConvertTo {
    
    public BookDto convertToBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setBookId(book.getBookId());
        bookDto.setEditionDto(convertToEditionDto(book.getEdition()));
        bookDto.setStatus(book.getStatus());
        return bookDto;
    }

    public AuthorDto convertToAuthorDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setAuthorId(author.getAuthorId());
        dto.setAuthorName(author.getAuthorName());
        dto.setAuthorLastName(author.getAuthorLastName());
        return dto;
    }

    public BookNameDto convertToBookNameDto(BookName bookName) {
        BookNameDto dto = new BookNameDto();
        dto.setBookNameId(bookName.getBookNameId());
        dto.setTitle(bookName.getTitle());
        return dto;
    }

    public CategoryDto convertToCategoryDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

    public PublisherDto convertToPublisherDto(Publisher publisher) {
        PublisherDto dto = new PublisherDto();
        dto.setPublisherId(publisher.getPublisherId());
        dto.setPublisherName(publisher.getPublisherName());
        return dto;
    }

    public EditionDto convertToEditionDto(Edition edition) {
        EditionDto dto = new EditionDto();
        dto.setEditionId(edition.getEditionId());
        dto.setBookNameDto(convertToBookNameDto(edition.getBookName()));
        dto.setAuthorDto(convertToAuthorDto(edition.getAuthor()));
        dto.setCategoryDto(convertToCategoryDto(edition.getCategory()));
        dto.setPublisherDto(convertToPublisherDto(edition.getPublisher()));
        dto.setPublishingDate(edition.getPublishingDate());
        dto.setIsbn(edition.getIsbn());
        dto.setQuantity(edition.getQuantity());
        return dto;
    }

    public UserDto convertToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setUserName(user.getUserName());
        userDto.setUserLastName(user.getUserLastName());
        return userDto;
    }

    public RentalRecordDto convertToRentalRecordDto(RentalRecord rent) {
        RentalRecordDto rentalRecordDto = new RentalRecordDto();
        rentalRecordDto.setRentalId(rent.getRentalId());
        rentalRecordDto.setUserDto(convertToUserDto(rent.getUser()));
        rentalRecordDto.setBookDto(convertToBookDto(rent.getBook()));
        rentalRecordDto.setRentalDate(rent.getRentalDate());
        rentalRecordDto.setRentalExpired(rent.getRentalExpired());
        rentalRecordDto.setRentalEnded(rent.getRentalEnded());
        rentalRecordDto.setBookingDate(rent.getBookingDate());
        return rentalRecordDto;
    }

}
