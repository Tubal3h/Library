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
        rentalRecord.setRentalId(rs.getInt("rentalId"));
        rentalRecord.setBook(book);
        rentalRecord.setUser(user);
        rentalRecord.setRentalDate(rs.getDate("rentalDate") != null ? rs.getDate("rentalDate").toLocalDate() : null);
        rentalRecord.setRentalExpired(rs.getDate("rentalExpired") != null ? rs.getDate("rentalExpired").toLocalDate() : null);
        rentalRecord.setRentalEnded(rs.getDate("rentalEnded") != null ? rs.getDate("rentalEnded").toLocalDate() : null);
        rentalRecord.setBookingDate(rs.getDate("bookingDate") != null ? rs.getDate("bookingDate").toLocalDate() : null);

        return rentalRecord;
    }


    @Override
    public RentalRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        return map(rs);
    }

}
