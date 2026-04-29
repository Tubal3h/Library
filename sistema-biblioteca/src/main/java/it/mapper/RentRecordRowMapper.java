package it.mapper;

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
        
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        
        User user = new User();
        user.setUserId(rs.getInt("users_id"));
        
        rentalRecord.setBook(book);
        rentalRecord.setUser(user);
        rentalRecord.setRentalId(rs.getInt("rental_id"));
        rentalRecord.setRentalDate(rs.getDate("rental_date") != null ? rs.getDate("rental_date").toLocalDate() : null);
        rentalRecord.setRentalExpired(rs.getDate("rental_expired") != null ? rs.getDate("rental_expired").toLocalDate() : null);
        rentalRecord.setRentalEnded(rs.getDate("rental_ended") != null ? rs.getDate("rental_ended").toLocalDate() : null);
        return rentalRecord;
    }
}

