package mrrock.com.wishlistminiproject.Rowmappers;

import mrrock.com.wishlistminiproject.Models.Wish;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WishRowMapper implements RowMapper<Wish> {
    @Override
    public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {
       Wish wish = new Wish();

       wish.setId(rs.getString("WISH_ID"));
        wish.setName(rs.getString("WISH_NAME"));
        wish.setWishURL(rs.getString("WISH_URL"));
        wish.setImgURL(rs.getString("WISH_IMGURL"));
        wish.setPrice(rs.getDouble("WISH_PRICE"));
        wish.setDescription(rs.getString("WISH_DESC"));
        wish.setWishlistID(rs.getString("WISH_WISHLIST_ID"));
        wish.setReserved(rs.getBoolean("WISH_RESERVED"));
        return wish;
    }
}
