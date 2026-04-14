package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.RentalRecord;
import it.mapper.RentRecordRowMapper;

/**
 * Repository per la gestione dei record di noleggio (prestiti) nel database.
 */
@Repository
public class RentRecordRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RentRecordRowMapper rentRecordRowMapper;

    /**
     * Costruttore per RentRepository.
     * 
     * @param jdbcTemplate Il template JDBC per le operazioni sul database
     */
    public RentRecordRepository(JdbcTemplate jdbcTemplate, RentRecordRowMapper rentRecordRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rentRecordRowMapper = rentRecordRowMapper;
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

        return jdbcTemplate.query(sql, rentRecordRowMapper);
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
     * Conta il numero totale di record di noleggio nel sistema per un utente
     * specifico.
     * 
     * @param userId ID dell'utente
     * @return Il numero totale di record di noleggio nel sistema per un utente
     *         specifico
     */

    public int countRentsByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM rental_record where users_id = ? and rental_ended is null";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    /**
     * Crea un nuovo record di noleggio nel database.
     * 
     * @param rental Record di noleggio da creare
     * @return Il record di noleggio creato
     */

    public RentalRecord createRental(RentalRecord rental) {
        String sql = """
            INSERT INTO 
                rental_record 
                (users_id, book_id, rental_date, rental_expired, rental_ended) 
            VALUES 
                (?, ?, ?, ?, ?)
        """;
        jdbcTemplate.update(sql, rental.getUserId(), rental.getBookId(), rental.getRentalDate(),
                rental.getRentalExpired(), rental.getRentalEnded());
        return rental;
    }
}
