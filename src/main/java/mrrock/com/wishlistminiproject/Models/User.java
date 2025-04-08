package mrrock.com.wishlistminiproject.Models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private int id;
    private String name;
    private String username;
    private String password;

    public User() {
    }

}
