package it.mapper.response;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.Author;
import it.entity.Book;
import it.entity.BookName;
import it.entity.Publisher;
import it.entity.Category;
import it.entity.join.EditionJoin;
import it.mapper.AuthorRowMapper;
import it.mapper.BookNameRowMapper;
import it.mapper.BookRowMapper;
import it.mapper.CategoryRowMapper;
import it.mapper.PublisherRowMapper;

/**
 * Mapper per convertire i record del database in oggetti EditionJoin.
 */
@Component
public class EditionJoinResponseRowMapper implements RowMapper<EditionJoin> {

    /**
     * Converte un record del database in un oggetto EditionJoin.
     * 
     * @param rs Il ResultSet contenente i dati del record
     * @param rowNum Il numero della riga
     * @return Oggetto EditionJoin contenente i dati del record
     * @throws SQLException Se si verifica un errore durante la conversione
     */
    /**
     * Metodo statico per mappare una riga del ResultSet in un oggetto EditionJoin.
     * Utile per essere chiamato da altri mapper che contengono un EditionJoin.
     * 
     * @param rs Il ResultSet contenente i dati del record
     * @return Oggetto EditionJoin contenente i dati del record
     * @throws SQLException Se si verifica un errore durante la conversione
     */
    public static EditionJoin map(ResultSet rs) throws SQLException {
        EditionJoin editionJoin = new EditionJoin();
        editionJoin.setEditionId(rs.getInt("editionId"));
        editionJoin.setAuthor(AuthorRowMapper.map(rs));
        editionJoin.setBook(BookRowMapper.map(rs));
        editionJoin.setBookName(BookNameRowMapper.map(rs));
        editionJoin.setCategory(CategoryRowMapper.map(rs));
        editionJoin.setPublisher(PublisherRowMapper.map(rs));
        editionJoin.setPublishingDate(rs.getDate("publishingDate").toLocalDate());
        editionJoin.setIsbn(rs.getString("isbn"));
        editionJoin.setQuantity(rs.getInt("quantity"));
        return editionJoin;
    }

    @Override
    public EditionJoin mapRow(ResultSet rs, int rowNum) throws SQLException {
        return map(rs);
    }
}
