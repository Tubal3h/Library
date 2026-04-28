package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                  MAPPER                                    */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.BookHistoryJoin;

/**
 * RowMapper per la conversione di un record del database in un oggetto {@link BookHistoryJoin}.
 * Esegue la mappatura dei risultati delle query aggregate che coinvolgono più tabelle.
 */
@Component
public class BookHistoryJoinRowMapper implements RowMapper<BookHistoryJoin> {

    /**
     * Mappa una riga del ResultSet a un oggetto {@link BookHistoryJoin}.
     *
     * @param rs     ResultSet contenente i dati del libro
     * @param rowNum Numero della riga corrente
     * @return Oggetto {@link BookHistoryJoin} mappato
     * @throws SQLException Se si verifica un errore durante l'accesso ai dati del ResultSet
     */
    @Override
    public BookHistoryJoin mapRow(ResultSet rs, int rowNum) throws SQLException {
        BookHistoryJoin bookHistoryJoin = new BookHistoryJoin();
        
        bookHistoryJoin.setEditionId(rs.getInt("edition_id"));
        bookHistoryJoin.setBookId(rs.getInt("book_id"));
        bookHistoryJoin.setBookName(rs.getString("book_name"));
        bookHistoryJoin.setAuthorName(rs.getString("author_name"));
        bookHistoryJoin.setAuthorLastName(rs.getString("author_last_name"));
        bookHistoryJoin.setPublisherName(rs.getString("publisher_name"));
        bookHistoryJoin.setPublicationDate(rs.getDate("publishing_date").toLocalDate());
        bookHistoryJoin.setCategoryName(rs.getString("category_name"));
        bookHistoryJoin.setIsbn(rs.getString("isbn"));
        bookHistoryJoin.setStatus(rs.getString("status"));
        bookHistoryJoin.setUserName(rs.getString("user_name"));
        bookHistoryJoin.setUserLastName(rs.getString("user_last_name"));

        return bookHistoryJoin;
    }
}
