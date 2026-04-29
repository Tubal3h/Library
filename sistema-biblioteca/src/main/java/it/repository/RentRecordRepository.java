package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.RentalRecord;
import it.exception.HistoryNotFoundException;
import it.mapper.RentRecordRowMapper;
import it.mapper.response.BookRecordJoinResponseRowMapper;
import it.mapper.response.RentalRecordJoinResponseRowMapper;
import it.repository.interfaces.RentRecordRepositoryInterface;

/**
 * Repository per la gestione dei record di noleggio (prestiti) nel database.
 */
@Repository
public class RentRecordRepository implements RentRecordRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;
    private final RentRecordRowMapper rentRecordRowMapper;
    private final RentalRecordJoinResponseRowMapper rentalRecordJoinRowMapper;
    private final BookRecordJoinResponseRowMapper bookRecordJoinResponseRowMapper;

    /**
     * Costruttore per RentRecordRepository.
     *
     * @param jdbcTemplate              Il template JDBC per le operazioni sul
     *                                  database
     * @param rentRecordRowMapper       Mapper per convertire i record del database
     *                                  in oggetti RentalRecord
     * @param rentalRecordJoinRowMapper Mapper per le query aggregate con JOIN su
     *                                  libri ed edizioni
     */
    public RentRecordRepository(JdbcTemplate jdbcTemplate, RentRecordRowMapper rentRecordRowMapper,
            RentalRecordJoinResponseRowMapper rentalRecordJoinRowMapper,
            BookRecordJoinResponseRowMapper bookRecordJoinResponseRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rentRecordRowMapper = rentRecordRowMapper;
        this.rentalRecordJoinRowMapper = rentalRecordJoinRowMapper;
        this.bookRecordJoinResponseRowMapper = bookRecordJoinResponseRowMapper;
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
     * Conta il numero totale di copie prese in prestito.
     *
     * @return Numero di copie in prestito
     */
    public int countRents() {
        String sql = "SELECT COUNT(*) FROM books where status = 'in prestito'";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * Conta il numero totale di copie prese in prestito dagli utenti
     * 
     * @return Numero di copie prese in prestito
     */
    public int countBorrowedBooks() {
        String sql = "SELECT COUNT(*) FROM books where status = 'prenotato'";
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
     * Recupera tutti i noleggi attivi con i dati completi del libro in una singola
     * query.
     * Risolve il problema N+1 eseguendo un JOIN direttamente nel database.
     *
     * @return Lista di {@link RentalRecord} con i dati del noleggio e del libro
     *         associato
     */
    public List<RentalRecord> getActiveRents() {
        String sql = """
                SELECT
                    r.rental_id, r.users_id, r.book_id,
                    r.rental_date, r.rental_expired, r.rental_ended, r.booking_date,
                    u.user_name, u.user_last_name, u.email, u.pass, u.roles,
                    b.status,
                    e.edition_id, e.book_name_id, e.author_id, e.publisher_id, e.category_id, e.publishing_date, e.isbn,
                    bn.title,
                    a.author_name, a.author_last_name,
                    p.publisher_name,
                    c.category_name,
                    0 AS quantity
                FROM rental_record r
                JOIN books b        ON r.book_id       = b.book_id
                JOIN edition e      ON b.edition_id    = e.edition_id
                JOIN books_names bn ON e.book_name_id  = bn.book_name_id
                JOIN author a       ON e.author_id     = a.author_id
                JOIN publisher p    ON e.publisher_id  = p.publisher_id
                JOIN category c     ON e.category_id   = c.category_id
                JOIN users u        ON r.users_id      = u.users_id
                WHERE r.rental_ended IS NULL
                ORDER BY r.rental_date DESC
                """;

        try {
            return jdbcTemplate.query(sql, rentalRecordJoinRowMapper);
        } catch (DataAccessException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * Recupera i noleggi attivi di un utente specifico con i dati completi del
     * libro in una singola query.
     * Risolve il problema N+1 eseguendo un JOIN direttamente nel database.
     *
     * @param userId ID dell'utente
     * @return Lista di {@link RentalRecord} con i dati del noleggio e del libro
     *         associato
     */
    public List<RentalRecord> getActiveRentsByUserId(int userId) {
        String sql = """
                SELECT
                    r.rental_id, r.users_id, r.book_id,
                    r.rental_date, r.rental_expired, r.rental_ended, r.booking_date,
                    u.user_name, u.user_last_name, u.email, u.pass, u.roles,
                    b.status,
                    e.edition_id, e.book_name_id, e.author_id, e.publisher_id, e.category_id, e.publishing_date, e.isbn,
                    bn.title,
                    a.author_name, a.author_last_name,
                    p.publisher_name,
                    c.category_name,
                    0 AS quantity
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
                ORDER BY r.rental_date DESC
                """;

        try {
            return jdbcTemplate.query(sql, rentalRecordJoinRowMapper, userId);

        } catch (DataAccessException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException();
        }
    }

    /**
     * Crea un nuovo record di noleggio nel database e aggiorna lo stato del libro.
     *
     * @param rental Entità {@link RentalRecord} contenente i dati del noleggio da
     *               inserire
     */
    public void createRental(RentalRecord rental) {
        String sql = """
                UPDATE rental_record
                SET rental_date = ?,
                    rental_expired = ?
                WHERE rental_id = ?
                """;
        jdbcTemplate.update(sql, rental.getRentalDate(), rental.getRentalExpired(), rental.getRentalId());
    }

    public void createABookedDate(RentalRecord rental) {
        String sql = """
                INSERT INTO rental_record (users_id, book_id, booking_date)
                VALUES (?, ?, ?)
                """;
        jdbcTemplate.update(sql, rental.getUser().getUserId(), rental.getBook().getBookId(), rental.getBookingDate());
    }

    /**
     * Chiude un noleggio attivo registrando la data di restituzione e aggiornando
     * lo stato del libro.
     *
     * @param bookId ID del libro restituito
     * @param rentId ID del record di noleggio da chiudere
     */
    public void endRental(int bookId, int rentId) {
        updateRentalEnded(rentId);
        updateRentalStatusOk(bookId);
    }

    /**
     * Aggiorna lo stato del libro alternandolo tra "disponibilita" e "in prestito".
     *
     * @param bookId ID del libro di cui aggiornare lo stato
     */
    public void updateRentalStatusOk(int bookId) {
        String sql = """
                UPDATE books
                SET status = CASE
                    WHEN status = 'disponibilita' THEN 'prenotato'
                    WHEN status = 'prenotato' THEN 'in prestito'
                    WHEN status = 'in prestito' THEN 'disponibilita'
                END
                WHERE book_id = ?
                """;
        jdbcTemplate.update(sql, bookId);
    }

    public void updateRentalStatusNotOk(int bookId) {
        String sql = """
                UPDATE books
                SET status = CASE
                WHEN status = 'prenotato' THEN 'disponibilita'
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

    @Override
    public void updateStatusToLend(int bookId) {
        String sql = """
                UPDATE books
                SET status = 'prenotato'
                WHERE status = 'disponibilita'
                AND book_id = ?
                """;

        jdbcTemplate.update(sql, bookId);
    }

    @Override
    public void deleteRentalById(int rentId) {
        String sql = """
                DELETE FROM rental_record
                WHERE rental_id = ?
                """;
        jdbcTemplate.update(sql, rentId);
    }

    @Override
    public List<RentalRecord> getBookRecords(int bookId) throws HistoryNotFoundException {
        String sql = """
                		SELECT
                            r.rental_id, r.users_id, r.book_id,
                            r.rental_date, r.rental_expired, r.rental_ended, r.booking_date,
                            u.user_name, u.user_last_name, u.email, u.pass, u.roles,
                            b.status,
                            e.edition_id, e.book_name_id, e.author_id, e.publisher_id, e.category_id, e.publishing_date, e.isbn,
                            bn.title,
                            a.author_name, a.author_last_name,
                            p.publisher_name,
                            c.category_name,
                            0 AS quantity
                		FROM rental_record r
                		LEFT JOIN users u ON r.users_id = u.users_id
                		LEFT JOIN books b ON b.book_id = r.book_id
                        LEFT JOIN edition e ON b.edition_id = e.edition_id
                        LEFT JOIN books_names bn ON e.book_name_id = bn.book_name_id
                        LEFT JOIN author a ON e.author_id = a.author_id
                        LEFT JOIN publisher p ON e.publisher_id = p.publisher_id
                        LEFT JOIN category c ON e.category_id = c.category_id
                		WHERE r.book_id = ?
                        ORDER BY r.rental_id DESC
                """;

        try {
            return jdbcTemplate.query(sql, bookRecordJoinResponseRowMapper, bookId);
        } catch (DataAccessException ex) {
            System.out.println(ex.getMessage());
            throw new HistoryNotFoundException("errore nel cercare i dati");
        }
    }

    public List<RentalRecord> getUserRecords(int userId) throws HistoryNotFoundException {
        String sql = """
                        SELECT
                            r.rental_id, r.users_id, r.book_id,
                            r.rental_date, r.rental_expired, r.rental_ended, r.booking_date,
                            u.user_name, u.user_last_name, u.email, u.pass, u.roles,
                            b.status,
                            e.edition_id, e.book_name_id, e.author_id, e.publisher_id, e.category_id, e.publishing_date, e.isbn,
                            bn.title,
                            a.author_name, a.author_last_name,
                            p.publisher_name,
                            c.category_name,
                            0 AS quantity
                        FROM rental_record r
                        LEFT JOIN users u ON r.users_id = u.users_id
                        LEFT JOIN books b ON b.book_id = r.book_id
                        LEFT JOIN edition e ON b.edition_id = e.edition_id
                        LEFT JOIN books_names bn ON e.book_name_id = bn.book_name_id
                        LEFT JOIN author a ON e.author_id = a.author_id
                        LEFT JOIN publisher p ON e.publisher_id = p.publisher_id
                        LEFT JOIN category c ON e.category_id = c.category_id
                        WHERE r.users_id = ?
                        ORDER BY r.rental_id DESC
                """;

        try {
            return jdbcTemplate.query(sql, bookRecordJoinResponseRowMapper, userId);
        } catch (DataAccessException ex) {
            System.out.println(ex.getMessage());
            throw new HistoryNotFoundException("errore nel cercare i dati dell'utente");
        }
    }

}
