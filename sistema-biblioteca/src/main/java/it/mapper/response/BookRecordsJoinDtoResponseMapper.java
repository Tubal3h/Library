package it.mapper.response;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import it.dto.response.BookRecordsJoinDtoResponse;

public class BookRecordsJoinDtoResponseMapper implements RowMapper<BookRecordsJoinDtoResponse> {

	@Override
	public BookRecordsJoinDtoResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		BookRecordsJoinDtoResponse bookRecordsJoinDtoResponse = new BookRecordsJoinDtoResponse();
		
		bookRecordsJoinDtoResponse.setBookId(rs.getInt("book_id"));
		bookRecordsJoinDtoResponse.setRentalId(rs.getInt("rental_id"));
		bookRecordsJoinDtoResponse.setUserName(rs.getString("user_name"));
		bookRecordsJoinDtoResponse.setUserLastName(rs.getString("user_last_name"));
		bookRecordsJoinDtoResponse.setRentalDate(rs.getDate("rental_date").toLocalDate());
		bookRecordsJoinDtoResponse.setRentalExpired(rs.getDate("rental_expired").toLocalDate());
		bookRecordsJoinDtoResponse.setRentalExpired(rs.getDate("rental_ended").toLocalDate());
		
		
		return bookRecordsJoinDtoResponse;
	}

}
