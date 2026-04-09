package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.User;

@Component
public class UserRowMapper implements RowMapper<User> {
    
    /**
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto User mappato dalla riga del database
     * @throws java.sql.SQLException in caso di errori con il database
     */
    @Override
    public User mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        User user = new User();
        user.setUserId(rs.getInt("users_id"));
        user.setUserName(rs.getString("user_name"));
        user.setUserSurname(rs.getString("user_last_name"));
        user.setUserEmail(rs.getString("email"));
        user.setUserPassword(rs.getString("pass"));
        user.setUserRole(rs.getString("roles"));
        return user;
    }
}

