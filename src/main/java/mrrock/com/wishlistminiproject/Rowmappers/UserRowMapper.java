package mrrock.com.wishlistminiproject.Rowmappers;

import mrrock.com.wishlistminiproject.Models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("USER_ID"));
        user.setUsername(rs.getString("USER_USERNAME"));
        user.setPassword(rs.getString("USER_PASSWORD"));
        user.setName(rs.getString("USER_NAME"));
        return user;
    }
}
