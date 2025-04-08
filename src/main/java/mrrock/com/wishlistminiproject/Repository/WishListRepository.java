package mrrock.com.wishlistminiproject.Repository;

import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Models.Wishlist;
import mrrock.com.wishlistminiproject.Rowmappers.WishlistRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class WishListRepository implements CrudOperations<Wishlist, String> {

    private JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Wishlist createNewWishList(String name, int userID){

        String sql = "Insert into WISHLIST (WISHLIST_USER_ID,WISHLIST_NAME,WISHLIST_ID) values (?,?,?)";
        int rowsAffected = jdbcTemplate.update(sql,userID,name,UUID.randomUUID().toString());
        if (rowsAffected>0){
            Wishlist wishlist = new Wishlist();
            wishlist.setName(name);
            wishlist.setUserID(userID);
            return wishlist;
        }
        return null;
    }
    public List<Wishlist> getAllwishListsFromUserID(int id){
        String sql = "Select * from WISHLIST where WISHLIST_USER_ID = ?";
        return jdbcTemplate.query(sql,new WishlistRowMapper(),id);
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

    @Override
    public Wishlist findById(String id) {
        String sql = "Select * from wishlist where WISHLIST_ID = ?";

        List<Wishlist> wishlists = jdbcTemplate.query(sql,new WishlistRowMapper(),id);
        if (!wishlists.isEmpty()){
            return wishlists.getFirst();
        }
        return null;
    }
}
