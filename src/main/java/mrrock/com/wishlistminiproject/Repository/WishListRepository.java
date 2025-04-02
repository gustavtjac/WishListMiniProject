package mrrock.com.wishlistminiproject.Repository;

import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Models.Wishlist;
import mrrock.com.wishlistminiproject.Rowmappers.WishRowMapper;
import mrrock.com.wishlistminiproject.Rowmappers.WishlistRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishListRepository {

    private JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Wishlist createNewWishList(String name, int userID){

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

    public Wishlist getWishlistFromID(String id) {
        String sql = "Select * from wishlist where WISHLIST_ID = ?";

        List<Wishlist> wishlists = jdbcTemplate.query(sql,new WishlistRowMapper(),id);
        if (!wishlists.isEmpty()){
            return wishlists.getFirst();
        }
        return null;
    }
    public boolean checkIfUserOwnList(String listId, User user){
        String sql = "Select * from wishlist where WISHLIST_USER_ID = ?";
        if (user != null){
            List<Wishlist> wishlistList = jdbcTemplate.query(sql,new WishlistRowMapper(),user.getId());
            for (Wishlist wishlist : wishlistList){
                if (wishlist.getId().equalsIgnoreCase(listId)){
                    return true;
                }
            }
        }

        return false;
    }
    public boolean deleteWishlist(String wishlistID) {
        String wishSql = "DELETE FROM wish WHERE WISH_WISHLIST_ID = ?";
        jdbcTemplate.update(wishSql, wishlistID);
        String sql = "DELETE FROM wishlist WHERE WISHLIST_ID = ?";
        int rowsaffectedWishlist = jdbcTemplate.update(sql, wishlistID);

        if (rowsaffectedWishlist > 0) {
            return true;
        }
        return false;
    }

    public Wishlist updateWishListFromID(Wishlist wishlist) {
        String sql = "UPDATE WISHLIST SET WISHLIST_NAME = ? where wishlist_ID = ?";
        jdbcTemplate.update(sql, wishlist.getName(), wishlist.getId());
        return wishlist;
    }






}
