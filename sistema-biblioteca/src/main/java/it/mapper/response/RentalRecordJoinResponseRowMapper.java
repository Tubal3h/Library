package it.mapper.response;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.Book;
import it.entity.RentalRecord;
import it.entity.User;
import it.mapper.UserRowMapper;

/**
 * Mapper per convertire i risultati di una query con JOIN tra rental_record,
 * books, edition, books_names, author, publisher e category
 * in oggetti {@link RentalRecord}, eliminando la necessità di query
 * aggiuntive per i dati del libro associato al noleggio.
 */
@Component
/**
 * Mapper per convertire i record del database in oggetti di tipo RentalRecordJoinResponseRowMapper.
 */
public class RentalRecordJoinResponseRowMapper implements RowMapper<RentalRecord> {

    /**
     * Metodo statico per mappare una riga del ResultSet in un oggetto
     * RentalRecordJoin.
     * 
     * @param rs Il ResultSet contenente i dati del record
     * @return Oggetto RentalRecordJoin mappato
     * @throws SQLException Se si verifica un errore durante la conversione
     */
    public static RentalRecord map(ResultSet rs) throws SQLException {
        Book book = BookJoinResponseRowMapper.map(rs);
        User user = UserRowMapper.map(rs);

        RentalRecord rentalRecord = new RentalRecord();
        rentalRecord.setRentalId(rs.getInt("rental_id"));
        rentalRecord.setBook(book);
        rentalRecord.setUser(user);
        rentalRecord.setRentalDate(rs.getDate("rental_date") != null ? rs.getDate("rental_date").toLocalDate() : null);
        rentalRecord.setRentalExpired(rs.getDate("rental_expired") != null ? rs.getDate("rental_expired").toLocalDate() : null);
        rentalRecord.setRentalEnded(rs.getDate("rental_ended") != null ? rs.getDate("rental_ended").toLocalDate() : null);
        rentalRecord.setBookingDate(rs.getDate("booking_date") != null ? rs.getDate("booking_date").toLocalDate() : null);

        return rentalRecord;
    }


    @Override
    public RentalRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        return map(rs);
    }

}
