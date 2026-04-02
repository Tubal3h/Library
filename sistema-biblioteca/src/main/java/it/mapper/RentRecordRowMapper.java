package it.mapper;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.model.RentalRecord;

@Component
public class RentRecordRowMapper implements RowMapper<RentalRecord> {

    @Override
    public RentalRecord mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Date rentalDateSql = rs.getDate("rent_date");
        Date rentalExpiredSql = rs.getDate("expire_date");
        Date rentalEndedSql = rs.getDate("return_date");

        LocalDate rentalDate = rentalDateSql != null ? rentalDateSql.toLocalDate() : null;
        LocalDate rentalExpired = rentalExpiredSql != null ? rentalExpiredSql.toLocalDate() : null;
        LocalDate rentalEnded = rentalEndedSql != null ? rentalEndedSql.toLocalDate() : null;

        return new RentalRecord(
            rs.getInt("rent_id"),
            rs.getInt("user_id"),
            rs.getInt("book_id"),
            rentalDate,
            rentalExpired,
            rentalEnded
        );
    }
}