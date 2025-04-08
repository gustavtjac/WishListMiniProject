package mrrock.com.wishlistminiproject.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Wishlist {
    private String id;
    private String name;
    private int userID;
    private List<Wish> wishList;

    public Wishlist() {
    }

    @Override
    public String   toString() {
        return "Wishlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userID=" + userID +
                ", wishList=" + wishList +
                '}';
    }
}
