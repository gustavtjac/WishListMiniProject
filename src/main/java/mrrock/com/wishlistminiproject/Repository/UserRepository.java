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

       List<Wishlist> wishlists = jdbcTemplate.query(sql,new WishlistRowMapper(),id);
       if (!wishlists.isEmpty()){
           return wishlists.getFirst();
       }
       return null;
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
        if (user != null){
            List<Wishlist> wishlistList = jdbcTemplate.query(sql,new WishlistRowMapper(),user.getId());
            for (Wishlist wishlist : wishlistList){
                if (wishlist.getId() == listId){
                    return true;
                }
            }
        }

        return false;
    }

    public Wish getWishFromID(int wishID){
        String sql = "Select * from wish where WISH_ID = ?";
        List<Wish> wishlist = jdbcTemplate.query(sql,new WishRowMapper(),wishID);
        if (!wishlist.isEmpty()){
            return wishlist.getFirst();
        }
        return null;
    }

    public boolean checkIfUserOwnWish(User user, int wishID){
        if (user==null){
            return false;
        }

        String sqlGetLists = "Select * from wishlist where WISHLIST_USER_ID = ?";
String sqlGetWish = "Select * from wish where WISH_ID = ?";

List<Wish> list = jdbcTemplate.query(sqlGetWish,new WishRowMapper(),wishID);

for (Wishlist wishlist : jdbcTemplate.query(sqlGetLists,new WishlistRowMapper(),user.getId())){
    if (wishlist.getId()==list.getFirst().getWishlistID()){
        return true;
    }
}

return false;
    }

    public boolean deleteWishlist(int wishlistID){
        String wishSql= "DELETE FROM wish WHERE WISH_WISHLIST_ID = ?";
         jdbcTemplate.update(wishSql, wishlistID);
            String sql = "DELETE FROM wishlist WHERE WISHLIST_ID = ?";
            int rowsaffectedWishlist = jdbcTemplate.update(sql, wishlistID);

            if(rowsaffectedWishlist >0) {
                return true;
            }
        return false;
    }


    public boolean deleteWishFromID(int wishID){
        String wishSql= "DELETE FROM wish WHERE WISH_ID = ?";
        int rowsaffectedWish = jdbcTemplate.update(wishSql, wishID);

        if(rowsaffectedWish >0) {
            return true;
        }
        return false;
    }

    public Wish updateWishFromID(Wish wish) {
        String sql = "UPDATE WISH SET WISH_NAME = ?, WISH_DESC = ?, WISH_IMGURL =?, WISH_URL=?,WISH_PRICE =? where WISH_ID = ?";
        jdbcTemplate.update(sql, wish.getName(), wish.getDescription(), wish.getImgURL(), wish.getWishURL(), wish.getPrice(), wish.getId());
        return wish;
    }


    public Wishlist updateWishListFromID(Wishlist wishlist){
        String sql = "UPDATE WISHLIST SET WISHLIST_NAME = ? where wishlist_ID = ?";
        jdbcTemplate.update(sql,wishlist.getName() ,wishlist.getId());
        return wishlist;
    }

    public User getUserFromUsername(String username){
        String sql = "Select * from user where user_username = ?";
        List<User> userList = jdbcTemplate.query(sql,new UserRowMapper(),username);
        if (!userList.isEmpty()){
            return userList.getFirst();
        }
        return null;
    }

    public Wish reserveWishFromId(int wishID){
        String sql = "UPDATE WISH SET WISH_RESERVED = 1 where WISH_ID = ?";
        int rowsAffected = jdbcTemplate.update(sql,wishID);
        if (rowsAffected>0){
            return getWishFromID(wishID);
        }
        return null;
    }
}
