package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.RentalRecord;

/**
 * Mapper per convertire i record del database della tabella rental_record in oggetti Entity RentalRecord.
 */
@Component
public class RentRecordRowMapper implements RowMapper<RentalRecord> {

    /**
     * Mappa una riga del ResultSet in un oggetto RentalRecord.
     * 
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto RentalRecord mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
    @Override
    public RentalRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
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
