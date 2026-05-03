package it.mapper.response;

/* -------------------------------------------------------------------------- */
/*                                  MAPPER                                    */
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
 * RowMapper per la conversione di un record del database in un oggetto {@link BookRecordJoin}.
 * Esegue la mappatura dei risultati delle query aggregate che coinvolgono più tabelle.
 */
@Component
/**
 * Mapper per convertire i record del database in oggetti di tipo BookRecordJoinResponseRowMapper.
 */
public class BookRecordJoinResponseRowMapper implements RowMapper<RentalRecord> {

    /**
     * Mappa una riga del ResultSet a un oggetto {@link RentalRecord}.
     *
     * @param rs     ResultSet contenente i dati del libro
     * @param rowNum Numero della riga corrente
     * @return Oggetto {@link RentalRecord} mappato
     * @throws SQLException Se si verifica un errore durante l'accesso ai dati del ResultSet
     */
    @Override
    public RentalRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        RentalRecord rentalRecordJoin = RentalRecordJoinResponseRowMapper.map(rs);
        User user = UserRowMapper.map(rs);
        Book book = BookJoinResponseRowMapper.map(rs);

        RentalRecord rentalRecord = new RentalRecord();
        rentalRecord.setRentalId(rentalRecordJoin.getRentalId());
        rentalRecord.setRentalDate(rentalRecordJoin.getRentalDate());
        rentalRecord.setRentalExpired(rentalRecordJoin.getRentalExpired());
        rentalRecord.setRentalEnded(rentalRecordJoin.getRentalEnded());
        rentalRecord.setBookingDate(rentalRecordJoin.getBookingDate());
        rentalRecord.setUser(user);
        rentalRecord.setBook(book);

        return rentalRecord;
    }
}
