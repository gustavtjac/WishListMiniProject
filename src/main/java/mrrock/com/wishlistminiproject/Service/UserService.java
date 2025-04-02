package mrrock.com.wishlistminiproject.Service;

import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Models.Wishlist;
import mrrock.com.wishlistminiproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
@Autowired
    private UserRepository userRepository;

    public User authenticateLogin(String username,String password){
        List<User> authenticatedUser = userRepository.authenticateLogin(username,password);
        if (authenticatedUser.isEmpty()){
            return null;
        }else {
            return authenticatedUser.getFirst();
        }

    }

    public Wish createNewWish(Wish wish){
        return userRepository.createNewWish(wish);
    }


    public List<Wishlist> getAllwishListsFromUserID(int id){
        List<Wishlist> userWishList = userRepository.getAllwishListsFromUserID(id);
        for (Wishlist wishlist : userWishList){
            wishlist.setWishList(userRepository.getWishFromWishlistID(wishlist.getId()));
        }
        return userWishList;
    }

    public List<Wish> getAllWishesFromWishListID(String id) {
        return userRepository.getWishFromWishlistID(id);
    }

    public User registerNewUser(String username,String password,String name){
      return userRepository.registerNewUser(username,password,name);
    }

    public Wishlist createNewWishList(String name,int userID){
       return userRepository.createNewWishList(name,userID);
    }

    public Wishlist getWishlistFromID(String id) {
        return userRepository.getWishlistFromID(id);
    }

    public boolean checkIfUserOwnList(String listId,User user){
        return userRepository.checkIfUserOwnList(listId,user);
    }

    public Wish getWishFromID(String wishID){

        return userRepository.getWishFromID(wishID);
    }

    public boolean checkIfUserOwnsWish(User user, String wishID){
        return userRepository.checkIfUserOwnWish(user,wishID);
    }

    public boolean deleteWishlist(String WishlistID){
        return userRepository.deleteWishlist(WishlistID);
    }


    public boolean deleteWishFromID(String WishID){
        return userRepository.deleteWishFromID(WishID);
    }

public Wish updateWishFromID(Wish wish){
        return userRepository.updateWishFromID(wish);
}
public Wishlist updateWishListFromID(Wishlist wishlist){
    return userRepository.updateWishListFromID(wishlist);
}

public User getUserFromUsername(String username){
       return userRepository.getUserFromUsername(username);
}

public Wish reserveWishFromId(String wishID){
      return userRepository.reserveWishFromId(wishID);
}

}
