package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.RentalRecord;

/**
 * Repository per la gestione dei record di noleggio (prestiti) nel database.
 */
@Repository
public class RentRepository {
    private final JdbcTemplate jdbcTemplate;

    /**
     * Costruttore per RentRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public RentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Recupera la lista di tutti i record di noleggio presenti nel database.
     * 
     * @return Lista di tutti i record di noleggio
     */
    public List<RentalRecord> getAllRents() {
        String sql = """
            SELECT r.rental_id,
                   r.book_id,
                   r.users_id,
                   r.rental_date,
                   r.rental_ended,
                   r.rental_expired
            FROM rental_record r
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RentalRecord record = new RentalRecord();
            record.setRentalId(rs.getInt("rental_id"));
            record.setBookId(rs.getInt("book_id"));
            record.setUserId(rs.getInt("users_id"));
            record.setRentalDate(rs.getDate("rental_date").toLocalDate());
            record.setRentalExpired(rs.getDate("rental_expired").toLocalDate());
            record.setRentalEnded(rs.getDate("rental_ended") != null ? rs.getDate("rental_ended").toLocalDate() : null);
            return record;
        });
    }

    /**
     * Conta il numero totale di record di noleggio nel sistema.
     * 
     * @return Il numero totale di record di noleggio nel sistema
     */

    public int countRents() {
        String sql = "SELECT COUNT(*) FROM rental_record where rental_ended is null";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Conta il numero totale di record di noleggio nel sistema per un utente specifico.
     * 
     * @param userId ID dell'utente
     * @return Il numero totale di record di noleggio nel sistema per un utente specifico
     */

    public int countRentsByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM rental_record where users_id = ? and rental_ended is null";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }
}
