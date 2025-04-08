package mrrock.com.wishlistminiproject.Repository;


import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Models.Wishlist;
import mrrock.com.wishlistminiproject.Rowmappers.UserRowMapper;
import mrrock.com.wishlistminiproject.Rowmappers.WishRowMapper;
import mrrock.com.wishlistminiproject.Rowmappers.WishlistRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class UserRepository implements CrudOperations<User, String> {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> authenticateLogin(String username, String password) {
        // SQL query to fetch the stored hashed password for the given username
        String sql = "SELECT * FROM USERS WHERE USER_USERNAME = ?";

        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{username}, new UserRowMapper());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            assert user != null;
            if (passwordEncoder.matches(password, user.getPassword())) {
                return List.of(user);
            } else {

                return List.of();
            }
        } catch (Exception e) {

            return List.of();
        }
    }

    public User registerNewUser(String username, String password, String name) {
        String sql = "Insert into USERS (USER_USERNAME,USER_PASSWORD,USER_NAME) values (?,?,?)";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        try {
            int rowsAffected = jdbcTemplate.update(sql, username, hashedPassword, name);
            if (rowsAffected > 0) {

                return authenticateLogin(username, password).get(0);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

    }


    public boolean checkIfUserOwnWish(User user, String wishID) {
        if (user == null) {
            return false;
        }

        String sqlGetLists = "Select * from wishlist where WISHLIST_USER_ID = ?";
        String sqlGetWish = "Select * from wish where WISH_ID = ?";

        List<Wish> list = jdbcTemplate.query(sqlGetWish, new WishRowMapper(), wishID);

        for (Wishlist wishlist : jdbcTemplate.query(sqlGetLists, new WishlistRowMapper(), user.getId())) {
            if (wishlist.getId().equalsIgnoreCase(list.getFirst().getWishlistID())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public User findById(String username) {
        String sql = "SELECT * FROM USERS WHERE USER_USERNAME = ?";
        List<User> userList = jdbcTemplate.query(sql, new UserRowMapper(), username);
        if (!userList.isEmpty()) {
            return userList.getFirst();
        }
        return null;
    }
}


