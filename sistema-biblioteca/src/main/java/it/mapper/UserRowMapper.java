package it.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import it.model.User;

@Component
public class UserRowMapper implements RowMapper<User> {
    
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
