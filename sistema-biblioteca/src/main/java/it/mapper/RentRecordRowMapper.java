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
        RentalRecord rentalRecord = new RentalRecord();
        rentalRecord.setRentalId(rs.getInt("rental_id"));
        rentalRecord.setBookId(rs.getInt("book_id"));
        rentalRecord.setUserId(rs.getInt("users_id"));
        rentalRecord.setRentalDate(rs.getDate("rental_date").toLocalDate());
        rentalRecord.setRentalExpired(rs.getDate("rental_expired").toLocalDate());
        rentalRecord.setRentalEnded(rs.getDate("rental_ended") != null ? rs.getDate("rental_ended").toLocalDate() : null);
        return rentalRecord;
    }
}

