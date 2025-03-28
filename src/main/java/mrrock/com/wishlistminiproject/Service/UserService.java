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

    public List<Wish> getAllWishesFromWishListID(int id) {
        return userRepository.getWishFromWishlistID(id);
    }


    public User registerNewUser(String username,String password,String name){
      return userRepository.registerNewUser(username,password,name);
    }

    public Wishlist createNewWishList(String name,int userID){
       return userRepository.createNewWishList(name,userID);
    }

    public Wishlist getWishlistFromID(int id) {
        return userRepository.getWishlistFromID(id);
    }


    public boolean checkIfUserOwnList(int listId,User user){
        return userRepository.checkIfUserOwnList(listId,user);
    }

    public Wish getWishFromID(int wishID){

        return userRepository.getWishFromID(wishID);
    }



    public boolean checkIfUserOwnsWish(User user, int wishID){
        return userRepository.checkIfUserOwnWish(user,wishID);
    }
}
