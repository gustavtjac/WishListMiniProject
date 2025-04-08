package mrrock.com.wishlistminiproject.Models;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Wish {
    private String id;
    private String wishlistID;
    private String name;
    private String description;
    private String imgURL;
    private double price;
    private String wishURL;
    private boolean reserved;

    public Wish() {
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
