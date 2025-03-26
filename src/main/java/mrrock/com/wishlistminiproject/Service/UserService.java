package mrrock.com.wishlistminiproject.Service;

import mrrock.com.wishlistminiproject.Models.User;
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


    public User registerNewUser(String username,String password,String name){
      return userRepository.registerNewUser(username,password,name);

    }


}
