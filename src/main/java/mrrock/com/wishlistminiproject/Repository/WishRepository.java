package mrrock.com.wishlistminiproject.Repository;

import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Rowmappers.WishRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class WishRepository implements CrudOperations<Wish,String>{

    private JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Wish> getWishFromWishlistID(String id){
        String sql = "Select * from WISH where WISH_WISHLIST_ID = ?";
        return jdbcTemplate.query(sql,new WishRowMapper(),id);
    }

    public Wish createNewWish(Wish wish){
        String sql = "Insert into WISH (WISH_WISHLIST_ID,WISH_NAME,WISH_DESC,WISH_IMGURL,WISH_PRICE,WISH_URL,WISH_ID) values (?,?,?,?,?,?,?)";
        int rowsAffected = jdbcTemplate.update(sql,wish.getWishlistID(),wish.getName(),wish.getDescription(),wish.getImgURL(),wish.getPrice(),wish.getWishURL(), UUID.randomUUID().toString());
        if (rowsAffected>0){
            return wish;
        }
        return null;
    }

    public Wish reserveWishFromId(String wishID){
        String sql = "UPDATE WISH SET WISH_RESERVED = 1 where WISH_ID = ?";
        int rowsAffected = jdbcTemplate.update(sql,wishID);
        if (rowsAffected>0){
            return findById(wishID);
        }
        return null;
    }

    public boolean deleteWishFromID(String wishID) {
        String wishSql = "DELETE FROM WISH WHERE WISH_ID = ?";
        int rowsaffectedWish = jdbcTemplate.update(wishSql, wishID);

        if (rowsaffectedWish > 0) {
            return true;
        }
        return false;
    }

    public Wish updateWishFromID(Wish wish) {
        String sql = "UPDATE WISH SET WISH_NAME = ?, WISH_DESC = ?, WISH_IMGURL =?, WISH_URL=?,WISH_PRICE =? where WISH_ID = ?";
        jdbcTemplate.update(sql, wish.getName(), wish.getDescription(), wish.getImgURL(), wish.getWishURL(), wish.getPrice(), wish.getId());
        return wish;
    }

    @Override
    public Wish findById(String wishID) {
        String sql = "Select * from WISH where WISH_ID = ?";
        List<Wish> wishlist = jdbcTemplate.query(sql,new WishRowMapper(),wishID);
        if (!wishlist.isEmpty()){
            return wishlist.getFirst();
        }
        return null;
    }
}

