package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.RentalRecordJoin;

/**
 * Mapper per convertire i risultati di una query con JOIN tra rental_record,
 * books, edition, books_names, author, publisher e category
 * in oggetti {@link RentalRecordJoin}, eliminando la necessità di query aggiuntive
 * per i dati del libro associato al noleggio.
 */
@Component
public class RentalRecordJoinRowMapper implements RowMapper<RentalRecordJoin> {

    /**
     * Mappa una riga del ResultSet in un oggetto RentalRecordJoin.
     *
     * @param rs     il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto RentalRecordJoin mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
    @Override
    public RentalRecordJoin mapRow(ResultSet rs, int rowNum) throws SQLException {
        RentalRecordJoin record = new RentalRecordJoin();
        record.setRentalId(rs.getInt("rental_id"));
        record.setUserId(rs.getInt("users_id"));
        record.setBookId(rs.getInt("book_id"));
        record.setBookName(rs.getString("title"));
        record.setAuthorFullName(rs.getString("author_full_name"));
        record.setUserName(rs.getString("user_name"));
        record.setUserLastName(rs.getString("user_last_name"));
        record.setPublisherName(rs.getString("publisher_name"));
        record.setPublicationDate(rs.getDate("publishing_date").toLocalDate());
        record.setCategoryName(rs.getString("category_name"));
        record.setIsbn(rs.getString("isbn"));
        record.setStatus(rs.getString("status"));
        record.setBookingDate(rs.getDate("booking_date") != null ? rs.getDate("booking_date").toLocalDate() : null);
        record.setRentalDate(rs.getDate("rental_date") != null ? rs.getDate("rental_date").toLocalDate() : null);
        record.setRentalExpired(rs.getDate("rental_expired") != null ? rs.getDate("rental_expired").toLocalDate() : null);
        record.setRentalEnded(rs.getDate("rental_ended") != null
                ? rs.getDate("rental_ended").toLocalDate()
                : null);
        return record;
    }
}
