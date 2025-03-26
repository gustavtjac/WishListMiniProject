package mrrock.com.wishlistminiproject.Repository;


import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Rowmappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public List<User> authenticateLogin(String username, String password){
       String sql = "select * from user where USER_USERNAME = ? and USER_PASSWORD = ?";
      return jdbcTemplate.query(sql,new UserRowMapper(),username,password);
    }




}
