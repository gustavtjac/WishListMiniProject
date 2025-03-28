package mrrock.com.wishlistminiproject.Repository;


import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Models.Wishlist;
import mrrock.com.wishlistminiproject.Rowmappers.UserRowMapper;
import mrrock.com.wishlistminiproject.Rowmappers.WishRowMapper;
import mrrock.com.wishlistminiproject.Rowmappers.WishlistRowMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Repository
public class UserRepository {
    @Value("${spring.datasource.username}")
    String db_name;
    @Value("${spring.datasource.url}")
    String db_url;
    @Value("${spring.datasource.password}")
    String db_psw;

    private JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> authenticateLogin(String username, String password){
       String sql = "select * from user where USER_USERNAME = ? and USER_PASSWORD = ?";
      return jdbcTemplate.query(sql,new UserRowMapper(),username,password);
    }

    public User registerNewUser(String username,String password,String name){
       String sql="Insert into USER (USER_USERNAME,USER_PASSWORD,USER_NAME) values (?,?,?)";
       try {
           int rowsAffected = jdbcTemplate.update(sql,username,password,name);
           if (rowsAffected>0){
               return authenticateLogin(username,password).get(0);
           }else {
               return null;
           }
       }catch (Exception e){
return null;
       }

    }
    public List<Wishlist> getAllwishListsFromUserID(int id){
        String sql = "Select * from wishlist where WISHLIST_USER_ID = ?";
        return jdbcTemplate.query(sql,new WishlistRowMapper(),id);
    }

    public List<Wish> getWishFromWishlistID(int id){
        String sql = "Select * from wish where WISH_WISHLIST_ID = ?";
        return jdbcTemplate.query(sql,new WishRowMapper(),id);
    }

    public Wishlist createNewWishList(String name,int userID){

        String sql = "Insert into WISHLIST (WISHLIST_USER_ID,WISHLIST_NAME) values (?,?)";
        int rowsAffected = jdbcTemplate.update(sql,userID,name);
       if (rowsAffected>0){
           Wishlist wishlist = new Wishlist();
           wishlist.setName(name);
           wishlist.setUserID(userID);
           return wishlist;
        }
       return null;
    }

    public Wishlist getWishlistFromID(int id) {
        String sql = "Select * from wishlist where WISHLIST_ID = ?";
        return jdbcTemplate.query(sql,new WishlistRowMapper(),id).get(0);
    }

    public Wish createNewWish(Wish wish){
        String sql = "Insert into WISH (WISH_WISHLIST_ID,WISH_NAME,WISH_DESC,WISH_IMGURL,WISH_PRICE,WISH_URL) values (?,?,?,?,?,?)";
        int rowsAffected = jdbcTemplate.update(sql,wish.getWishlistID(),wish.getName(),wish.getDescription(),wish.getImgURL(),wish.getPrice(),wish.getWishURL());
        if (rowsAffected>0){
            return wish;
        }
        return null;
    }


    public boolean checkIfUserOwnList(int listId, User user){
        String sql = "Select * from wishlist where WISHLIST_USER_ID = ?";
        List<Wishlist> wishlistList = jdbcTemplate.query(sql,new WishlistRowMapper(),user.getId());
        for (Wishlist wishlist : wishlistList){
            if (wishlist.getId() == listId){
                return true;
            }
        }

        return false;
    }

}
