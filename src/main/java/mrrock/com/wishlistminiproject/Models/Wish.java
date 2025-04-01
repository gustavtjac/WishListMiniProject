package mrrock.com.wishlistminiproject.Models;

import java.util.List;

public class Wish {
    private int id;
    private int wishlistID;
    private String name;
    private String description;
    private String imgURL;
    private double price;
    private String wishURL;
    private boolean reserved;

    public Wish() {
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWishlistID() {
        return wishlistID;
    }

    public void setWishlistID(int wishlistID) {
        this.wishlistID = wishlistID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getWishURL() {
        return wishURL;
    }

    public void setWishURL(String wishURL) {
        this.wishURL = wishURL;
    }

    @Override
    public String toString() {
        return "Wish{" +
                "id=" + id +
                ", wishlistID=" + wishlistID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imgURL='" + imgURL + '\'' +
                ", price=" + price +
                ", wishURL='" + wishURL + '\'' +
                '}';
    }
}
