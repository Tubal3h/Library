package it.mapper.response;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.dto.response.BookRecordsJoinDtoResponse;

@Component
public class BookRecordsJoinDtoResponseMapper implements RowMapper<BookRecordsJoinDtoResponse> {

	@Override
	public BookRecordsJoinDtoResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		BookRecordsJoinDtoResponse bookRecordsJoinDtoResponse = new BookRecordsJoinDtoResponse();
		
		bookRecordsJoinDtoResponse.setBookId(rs.getInt("book_id"));
		bookRecordsJoinDtoResponse.setRentalId(rs.getInt("rental_id"));
		bookRecordsJoinDtoResponse.setUserName(rs.getString("user_name"));
		bookRecordsJoinDtoResponse.setUserLastName(rs.getString("user_last_name"));
		bookRecordsJoinDtoResponse.setBookTitle(rs.getString("title"));
		bookRecordsJoinDtoResponse.setBookingDate(rs.getDate("booking_date") != null ? rs.getDate("booking_date").toLocalDate() : null);
		bookRecordsJoinDtoResponse.setRentalDate(rs.getDate("rental_date") != null ? rs.getDate("rental_date").toLocalDate() : null);
		bookRecordsJoinDtoResponse.setRentalExpired(rs.getDate("rental_expired") != null ? rs.getDate("rental_expired").toLocalDate() : null);
		bookRecordsJoinDtoResponse.setRentalEnded(rs.getDate("rental_ended") != null ? rs.getDate("rental_ended").toLocalDate() : null);
		
		return bookRecordsJoinDtoResponse;
	}

}
