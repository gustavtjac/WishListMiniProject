package mrrock.com.wishlistminiproject.Models;

import java.util.List;

public class Wishlist {
    private String id;
    private String name;
    private int userID;
    private List<Wish> wishList;

    public Wishlist() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<Wish> getWishList() {
        return wishList;
    }

    public void setWishList(List<Wish> wishList) {
        this.wishList = wishList;
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
