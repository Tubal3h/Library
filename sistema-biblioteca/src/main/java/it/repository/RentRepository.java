package it.repository;

import java.util.List;

import it.model.dto.BookCatalogDto;
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
            SELECT r.rental_id,
                r.book_id,
                bn.title,
                CONCAT(a.author_name, ' ', a.author_last_name) AS author_full_name,
                p.publisher_name,
                b.publishing_date,
                i.code AS isbn_code,
                c.category_name,
                b.status,
                r.users_id,
                r.rental_date,
                r.rental_ended,
                r.rental_expired
            FROM rental_record r
            JOIN books b ON r.book_id = b.book_id
            JOIN users u ON r.users_id = u.users_id
            JOIN books_names bn ON b.title_id = bn.book_name_id
            JOIN author a ON b.author_id = a.author_id
            JOIN publisher p ON b.publisher_id = p.publisher_id
            JOIN category c ON b.category_id = c.category_id
            JOIN isbn i ON b.isbn_id = i.isbn_id

            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RentDto dto = new RentDto();
            dto.setBookId(rs.getInt("book_id"));
            dto.setUserId(rs.getInt("users_id"));
            dto.setRentId(rs.getInt("rental_id"));
            dto.setBook(new BookCatalogDto(
                rs.getInt("book_id"),
                rs.getString("title"),
                rs.getString("author_full_name"),
                rs.getDate("publishing_date").toLocalDate(),
                rs.getString("publisher_name"),
                rs.getString("isbn_code"),
                rs.getString("category_name"),
                rs.getString("status")
            ));
            dto.setRentalDate(rs.getDate("rental_date").toLocalDate() );
            dto.setRentalExpired(rs.getDate("rental_expired").toLocalDate() );
            dto.setRentalEnded(
                rs.getDate("rental_ended") != null ? rs.getDate("rental_ended").toLocalDate() : null
            );
            return dto;
        });
    }

    // public List<RentDto> findRentsByUserId(int userId) {
    //     String sql = """
    //         SELECT r.rental_id,
    //                r.book_id,
    //                r.users_id,
    //                r.rental_date,
    //                r.rental_ended,
    //                r.rental_expired
    //         FROM rents r
    //         WHERE r.user_id = ?
    //         """;

    //     return jdbcTemplate.query(sql, (rs, rowNum) -> {
    //         RentDto dto = new RentDto();
    //         dto.setBookId(rs.getInt("book_id"));
    //         dto.setUserId(rs.getInt("user_id"));
    //         dto.setRentalDate(rs.getDate("rental_date"));
    //         dto.setRentalExpired(rs.getDate("rental_expired"));
    //         dto.setRentalEnded(rs.getDate("rental_ended"));
    //         return dto;
    //     });
    // }
}
