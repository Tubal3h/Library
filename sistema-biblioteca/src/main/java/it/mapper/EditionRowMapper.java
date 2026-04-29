package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.Author;
import it.entity.BookName;
import it.entity.Edition;
import it.entity.Publisher;

/**
 * Mapper per convertire i record del database della tabella edition in oggetti Entity Edition.
 */
@Component
public class EditionRowMapper implements RowMapper<Edition> {
    
    /**
     * Mappa una riga del ResultSet in un oggetto Edition.
     * 
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto Edition mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
    @Override
    public Edition mapRow(ResultSet rs, int rowNum) throws SQLException {
        
    	BookName bookName= new BookName();
        bookName.setBookNameId(rs.getInt("book_name_id"));
        
        Author author = new Author();
        author.setAuthorId(rs.getInt("author_id"));
        
        Publisher publisher = new Publisher();
        publisher.setPublisherId(rs.getInt("publisher_id"));
    	
        Edition edition = new Edition();
        edition.setEditionId(rs.getInt("edition_id"));
    	edition.setBookName(bookName);
    	edition.setAuthor(author);
    	edition.setPublisher(publisher);
        edition.setPublishingDate(rs.getDate("publishing_date").toLocalDate());
        edition.setIsbn(rs.getString("isbn"));
        return edition;
    }
}

