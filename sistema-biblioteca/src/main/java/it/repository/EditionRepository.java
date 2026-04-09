package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */


import it.entity.Edition;
import it.mapper.EditionRowMapper;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EditionRepository {
    private final EditionRowMapper editionRowMapper;
    private final JdbcTemplate jdbcTemplate;
    
    public EditionRepository(JdbcTemplate jdbcTemplate, EditionRowMapper editionRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.editionRowMapper = editionRowMapper;
    }

    /**
     * @return Lista di tutte le edizioni disponibili
     */
    public List<Edition> getAllEditions() {
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

    /**
     * @param editionId l'ID dell'edizione
     * @return L'oggetto Edition corrispondente all'ID fornito
     */
public Edition getEditionById(int editionId) {
    String sql = "SELECT * FROM edition WHERE edition_id = ?";
    return jdbcTemplate.queryForObject(sql, editionRowMapper, editionId);
}


}

