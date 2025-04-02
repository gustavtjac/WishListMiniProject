package mrrock.com.wishlistminiproject.Service;

import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Models.Wishlist;
import mrrock.com.wishlistminiproject.Repository.UserRepository;
import mrrock.com.wishlistminiproject.Repository.WishListRepository;
import mrrock.com.wishlistminiproject.Repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private WishRepository wishRepository;


    public List<Wishlist> getAllwishListsFromUserID(int id){
        List<Wishlist> userWishList = wishRepository.getAllwishListsFromUserID(id);
        for (Wishlist wishlist : userWishList){
            wishlist.setWishList(wishRepository.getWishFromWishlistID(wishlist.getId()));
        }
        return userWishList;
    }

    public List<Wish> getAllWishesFromWishListID(String id) {
        return wishRepository.getWishFromWishlistID(id);
    }

    public Wishlist createNewWishList(String name,int userID){
        return wishListRepository.createNewWishList(name,userID);
    }

    public Wishlist getWishlistFromID(String id) {
        return wishListRepository.getWishlistFromID(id);
    }

    public boolean deleteWishlist(String WishlistID){
        return wishListRepository.deleteWishlist(WishlistID);
    }

    public Wishlist updateWishListFromID(Wishlist wishlist){
        return wishListRepository.updateWishListFromID(wishlist);
    }



}
