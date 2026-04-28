package it.mapper.response;

/* -------------------------------------------------------------------------- */
/*                                  MAPPER                                    */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.User;
import it.entity.join.BookRecordJoin;
import it.entity.join.RentalRecordJoin;
import it.mapper.UserRowMapper;

/**
 * RowMapper per la conversione di un record del database in un oggetto {@link BookRecordJoin}.
 * Esegue la mappatura dei risultati delle query aggregate che coinvolgono più tabelle.
 */
@Component
public class BookRecordJoinResponseRowMapper implements RowMapper<BookRecordJoin> {

    /**
     * Mappa una riga del ResultSet a un oggetto {@link BookRecordJoin}.
     *
     * @param rs     ResultSet contenente i dati del libro
     * @param rowNum Numero della riga corrente
     * @return Oggetto {@link BookRecordJoin} mappato
     * @throws SQLException Se si verifica un errore durante l'accesso ai dati del ResultSet
     */
    @Override
    public BookRecordJoin mapRow(ResultSet rs, int rowNum) throws SQLException {
        RentalRecordJoin rentalRecordJoin = RentalRecordJoinResponseRowMapper.map(rs);
        User user = UserRowMapper.map(rs);

        BookRecordJoin bookRecordJoin = new BookRecordJoin();
        bookRecordJoin.setRentalRecordJoin(rentalRecordJoin);
        bookRecordJoin.setUser(user);

        return bookRecordJoin;
    }
}
