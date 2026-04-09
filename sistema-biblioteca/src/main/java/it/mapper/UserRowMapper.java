package it.mapper;

/* -------------------------------------------------------------------------- */
/*                                   MAPPER                                   */
/* -------------------------------------------------------------------------- */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.entity.User;

/**
 * Mapper per convertire i record del database della tabella users in oggetti Entity User.
 */
@Component
public class UserRowMapper implements RowMapper<User> {
    
    /**
     * Mappa una riga del ResultSet in un oggetto User.
     * 
     * @param rs il ResultSet da cui estrarre i dati
     * @param rowNum il numero della riga corrente
     * @return L'oggetto User mappato dalla riga del database
     * @throws SQLException in caso di errori con il database
     */
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
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


