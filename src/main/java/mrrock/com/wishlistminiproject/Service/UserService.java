package mrrock.com.wishlistminiproject.Service;

import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Repository.UserRepository;
import mrrock.com.wishlistminiproject.Repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final WishListRepository wishListRepository;

    public UserService(UserRepository userRepository, WishListRepository wishListRepository) {
        this.userRepository = userRepository;
        this.wishListRepository = wishListRepository;
    }



    public User authenticateLogin(String username, String password){
        List<User> authenticatedUser = userRepository.authenticateLogin(username,password);
        if (authenticatedUser.isEmpty()){
            return null;
        }else {
            return authenticatedUser.getFirst();
        }

    }

    public User registerNewUser(String username,String password,String name){
      return userRepository.registerNewUser(username,password,name);
    }



    public boolean checkIfUserOwnList(String listId,User user){
        return wishListRepository.checkIfUserOwnList(listId,user);
    }


    public boolean checkIfUserOwnsWish(User user, String wishID){
        return userRepository.checkIfUserOwnWish(user,wishID);
    }


public User getUserFromUsername(String username){
       return userRepository.findById(username);
}



}
