package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.RentalRecord;
import it.entity.RentalRecordJoin;
import it.mapper.RentRecordRowMapper;
import it.mapper.RentalRecordJoinRowMapper;

/**
 * Repository per la gestione dei record di noleggio (prestiti) nel database.
 */
@Repository
public class RentRecordRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RentRecordRowMapper rentRecordRowMapper;
    private final RentalRecordJoinRowMapper rentalRecordJoinRowMapper;

    /**
     * Costruttore per RentRecordRepository.
     *
     * @param jdbcTemplate             Il template JDBC per le operazioni sul database
     * @param rentRecordRowMapper      Mapper per convertire i record del database in oggetti RentalRecord
     * @param rentalRecordJoinRowMapper Mapper per le query aggregate con JOIN su libri ed edizioni
     */
    public RentRecordRepository(JdbcTemplate jdbcTemplate, RentRecordRowMapper rentRecordRowMapper,
            RentalRecordJoinRowMapper rentalRecordJoinRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rentRecordRowMapper = rentRecordRowMapper;
        this.rentalRecordJoinRowMapper = rentalRecordJoinRowMapper;
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
     * Conta il numero totale di noleggi attivi (non ancora conclusi) nel sistema.
     *
     * @return Numero di noleggi con {@code rental_ended} nullo
     */
    public int countRents() {
        String sql = "SELECT COUNT(*) FROM rental_record where rental_ended is null";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Conta il numero di noleggi attivi per un utente specifico.
     *
     * @param userId ID dell'utente
     * @return Numero di noleggi attivi dell'utente specificato
     */
    public int countRentsByUserId(int userId) {
        String sql = "SELECT COUNT(*) FROM rental_record where users_id = ? and rental_ended is null";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    /**
     * Recupera tutti i noleggi attivi con i dati completi del libro in una singola query.
     * Risolve il problema N+1 eseguendo un JOIN direttamente nel database.
     *
     * @return Lista di {@link RentalRecordJoin} con i dati del noleggio e del libro associato
     */
    public List<RentalRecordJoin> getActiveRents() {
        String sql = """
                SELECT
                    r.rental_id, r.users_id, r.book_id,
                    r.rental_date, r.rental_expired, r.rental_ended,
                    bn.title,
                    CONCAT(a.author_name, ' ', a.author_last_name) AS author_full_name,
                    u.user_name, 
                    u.user_last_name,
                    p.publisher_name,
                    e.publishing_date,
                    c.category_name,
                    e.isbn
                FROM rental_record r
                JOIN books b        ON r.book_id       = b.book_id
                JOIN edition e      ON b.edition_id    = e.edition_id
                JOIN books_names bn ON e.book_name_id  = bn.book_name_id
                JOIN author a       ON e.author_id     = a.author_id
                JOIN publisher p    ON e.publisher_id  = p.publisher_id
                JOIN category c     ON e.category_id   = c.category_id
                JOIN users u		ON r.users_id      = u.users_id
                WHERE r.rental_ended IS NULL
                """;
        return jdbcTemplate.query(sql, rentalRecordJoinRowMapper);
    }

    /**
     * Recupera i noleggi attivi di un utente specifico con i dati completi del libro in una singola query.
     * Risolve il problema N+1 eseguendo un JOIN direttamente nel database.
     *
     * @param userId ID dell'utente
     * @return Lista di {@link RentalRecordJoin} con i dati del noleggio e del libro associato
     */
    public List<RentalRecordJoin> getActiveRentsByUserId(int userId) {
        String sql = """
                SELECT
                    r.rental_id, r.users_id, r.book_id,
                    r.rental_date, r.rental_expired, r.rental_ended,
                    bn.title,
                    CONCAT(a.author_name, ' ', a.author_last_name) AS author_full_name,
                    u.user_name, 
                    u.user_last_name,
                    p.publisher_name,
                    e.publishing_date,
                    c.category_name,
                    e.isbn
                FROM rental_record r
                JOIN books b        ON r.book_id       = b.book_id
                JOIN edition e      ON b.edition_id    = e.edition_id
                JOIN books_names bn ON e.book_name_id  = bn.book_name_id
                JOIN author a       ON e.author_id     = a.author_id
                JOIN publisher p    ON e.publisher_id  = p.publisher_id
                JOIN category c     ON e.category_id   = c.category_id
                JOIN users u        ON r.users_id      = u.users_id
                WHERE r.rental_ended IS NULL
                  AND r.users_id = ?
                """;
        return jdbcTemplate.query(sql, rentalRecordJoinRowMapper, userId);
    }

    /**
     * Crea un nuovo record di noleggio nel database e aggiorna lo stato del libro.
     *
     * @param rental Entità {@link RentalRecord} contenente i dati del noleggio da inserire
     */
    public void createRental(RentalRecord rental) {
        String sql = """
            INSERT INTO
                rental_record
                (users_id, book_id, rental_date, rental_expired, rental_ended)
            VALUES
                (?, ?, ?, ?, ?)
        """;
        updateRentalStatus(rental.getBookId());
        jdbcTemplate.update(sql, rental.getUserId(), rental.getBookId(), rental.getRentalDate(),
                rental.getRentalExpired(), rental.getRentalEnded());
    }

    /**
     * Chiude un noleggio attivo registrando la data di restituzione e aggiornando lo stato del libro.
     *
     * @param bookId ID del libro restituito
     * @param rentId ID del record di noleggio da chiudere
     */
    public void endRental(int bookId, int rentId) {
        updateRentalEnded(rentId);
        updateRentalStatus(bookId);
    }

    /**
     * Aggiorna lo stato del libro alternandolo tra "disponibilita" e "in prestito".
     *
     * @param bookId ID del libro di cui aggiornare lo stato
     */
    private void updateRentalStatus(int bookId) {
        String sql = """
                UPDATE books
                SET status = CASE
                    WHEN status = 'disponibilita' THEN 'in prestito'
                    WHEN status = 'in prestito' THEN 'disponibilita'
                END
                WHERE book_id = ?
                """;
        jdbcTemplate.update(sql, bookId);
    }

    /**
     * Registra la data di restituzione effettiva per il noleggio specificato.
     *
     * @param rentId ID del record di noleggio da aggiornare
     */
    private void updateRentalEnded(int rentId) {
        LocalDate date = LocalDate.now();
        String sql = """
                UPDATE rental_record
                SET rental_ended = ?
                WHERE rental_id = ?
                """;
        jdbcTemplate.update(sql, date, rentId);
    }
}
