package it.repository;

import java.util.List;

import it.model.dto.RentDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RentRepository {
    private final JdbcTemplate jdbcTemplate;

    public RentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RentDto> findAllRents() {
        String sql = """
            SELECT r.rent_id,
                   r.book_id,
                   r.user_id,
                   r.rent_date,
                   r.return_date,
                   r.rental_expired
            FROM rents r
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RentDto dto = new RentDto();
            dto.setBookId(rs.getInt("book_id"));
            dto.setUserId(rs.getInt("user_id"));
            dto.setRentalDate(rs.getDate("rent_date"));
            dto.setRentalExpired(rs.getDate("rental_expired"));
            dto.setRentalEnded(rs.getDate("return_date"));
            return dto;
        });
    }

    public List<RentDto> findRentsByUserId(int userId) {
        String sql = """
            SELECT r.rent_id,
                   r.book_id,
                   r.user_id,
                   r.rent_date,
                   r.return_date,
                   r.rental_expired
            FROM rents r
            WHERE r.user_id = ?
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RentDto dto = new RentDto();
            dto.setBookId(rs.getInt("book_id"));
            dto.setUserId(rs.getInt("user_id"));
            dto.setRentalDate(rs.getDate("rent_date"));
            dto.setRentalExpired(rs.getDate("rental_expired"));
            dto.setRentalEnded(rs.getDate("return_date"));
            return dto;
        });
    }
}
