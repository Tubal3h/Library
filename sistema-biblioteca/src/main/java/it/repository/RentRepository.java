package it.repository;

/* -------------------------------------------------------------------------- */
/*                                 REPOSITORY                                 */
/* -------------------------------------------------------------------------- */

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import it.entity.RentalRecord;

@Repository
public class RentRepository {
    private final JdbcTemplate jdbcTemplate;

    public RentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
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

}

