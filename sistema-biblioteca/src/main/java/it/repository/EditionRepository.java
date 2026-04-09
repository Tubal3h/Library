package it.repository;


import it.mapper.EditionRowMapper;
import java.time.LocalDate;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import it.model.Book;
import it.model.Edition;

@Repository
public class EditionRepository {
    private final EditionRowMapper editionRowMapper;
    private final JdbcTemplate jdbcTemplate;
    
    public EditionRepository(JdbcTemplate jdbcTemplate, EditionRowMapper editionRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.editionRowMapper = editionRowMapper;
    }

    public List<Edition> GetAllEditions() {
        String sql = "SELECT  * FROM edition";
        return jdbcTemplate.query(sql,  (rs, rowNum) -> {
            Edition edition = new Edition();
            edition.setEditionId(rs.getInt("edition_id"));
            edition.setBookNameId(rs.getInt("book_name_id"));
            edition.setAuthorId(rs.getInt("author_id"));
            edition.setPublisherId(rs.getInt("publisher_id"));
            edition.setPublishingDate(rs.getDate("publishing_date").toLocalDate());
            edition.setIsbn(rs.getString("isbn"));
            return edition;
        });
    }

public Edition getEditionById(int editionId) {
    String sql = "SELECT * FROM edition WHERE edition_id = ?";
    return jdbcTemplate.queryForObject(sql, editionRowMapper, editionId);
}


}
