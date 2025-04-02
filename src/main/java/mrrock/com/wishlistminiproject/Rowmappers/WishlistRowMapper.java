package mrrock.com.wishlistminiproject.Rowmappers;

import mrrock.com.wishlistminiproject.Models.Wishlist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishlistRowMapper implements RowMapper<Wishlist> {
    @Override
    public Wishlist mapRow(ResultSet rs, int rowNum) throws SQLException {
        Wishlist wishlist = new Wishlist();
        wishlist.setName(rs.getString("WISHLIST_NAME"));
        wishlist.setId(rs.getString("WISHLIST_ID"));
        wishlist.setUserID(rs.getInt("WISHLIST_USER_ID"));
        return wishlist;
    }
}
