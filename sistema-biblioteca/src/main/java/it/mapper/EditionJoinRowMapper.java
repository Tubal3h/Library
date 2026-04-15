package it.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.EditionJoin;

/**
 * Mapper per convertire i record del database in oggetti EditionJoin.
 */
@Component
public class EditionJoinRowMapper implements RowMapper<EditionJoin> {

    /**
     * Converte un record del database in un oggetto EditionJoin.
     * 
     * @param rs Il ResultSet contenente i dati del record
     * @param rowNum Il numero della riga
     * @return Oggetto EditionJoin contenente i dati del record
     * @throws SQLException Se si verifica un errore durante la conversione
     */
    @Override
    public EditionJoin mapRow(ResultSet rs, int rowNum) throws SQLException {
        EditionJoin editionJoin = new EditionJoin();
        editionJoin.setEditionId(rs.getInt("edition_id"));
        editionJoin.setBookId(rs.getInt("book_id"));
        editionJoin.setBookName(rs.getString("book_name"));
        editionJoin.setAuthor(rs.getString("author_name"));
        editionJoin.setPublisher(rs.getString("publisher_name"));
        editionJoin.setCategory(rs.getString("category_name"));
        editionJoin.setPublishingDate(rs.getDate("publishing_date").toLocalDate());
        editionJoin.setIsbn(rs.getString("isbn"));
        editionJoin.setQuantity(rs.getInt("quantity"));
        editionJoin.setStatus(rs.getString("status"));
        return editionJoin;
    }
}
