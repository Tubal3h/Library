package it.mapper.response;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.Author;
import it.entity.BookName;
import it.entity.Category;
import it.entity.Publisher;
import it.entity.join.EditionJoin;
import it.entity.join.RentalRecordJoin;

/**
 * Mapper per convertire i risultati di una query con JOIN tra rental_record,
 * books, edition, books_names, author, publisher e category
 * in oggetti {@link RentalRecordJoin}, eliminando la necessità di query aggiuntive
 * per i dati del libro associato al noleggio.
 */
@Component
public class RentalRecordJoinResponseRowMapper implements RowMapper<RentalRecordJoin> {

    /**
     * Mappa una riga del ResultSet in un oggetto RentalRecordJoin.
     *
     * @param rs     il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto RentalRecordJoin mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
    /**
     * Metodo statico per mappare una riga del ResultSet in un oggetto RentalRecordJoin.
     * 
     * @param rs Il ResultSet contenente i dati del record
     * @return Oggetto RentalRecordJoin mappato
     * @throws SQLException Se si verifica un errore durante la conversione
     */
    public static RentalRecordJoin map(ResultSet rs) throws SQLException {
        EditionJoin editionJoin = EditionJoinResponseRowMapper.map(rs);

        RentalRecordJoin rentalRecordJoin = new RentalRecordJoin();
        rentalRecordJoin.setRentalId(rs.getInt("rentalId"));
        rentalRecordJoin.setEditionJoin(editionJoin);
        rentalRecordJoin.setRentalDate(rs.getDate("rentalDate").toLocalDate());
        rentalRecordJoin.setRentalExpired(rs.getDate("rentalExpired").toLocalDate());
        rentalRecordJoin.setRentalEnded(rs.getDate("rentalEnded").toLocalDate());

        return rentalRecordJoin;
    }

    @Override
    public RentalRecordJoin mapRow(ResultSet rs, int rowNum) throws SQLException {
        return map(rs);
    }
}
