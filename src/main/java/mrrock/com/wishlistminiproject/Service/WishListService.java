package mrrock.com.wishlistminiproject.Service;

import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Models.Wishlist;
import mrrock.com.wishlistminiproject.Repository.WishListRepository;
import mrrock.com.wishlistminiproject.Repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    private final WishRepository wishRepository;

    public WishListService(WishListRepository wishListRepository, WishRepository wishRepository) {
        this.wishListRepository = wishListRepository;
        this.wishRepository = wishRepository;
    }

    public List<Wishlist> getAllwishListsFromUserID(int id) {
        List<Wishlist> userWishList = wishListRepository.getAllwishListsFromUserID(id);
        for (Wishlist wishlist : userWishList) {
            wishlist.setWishList(wishRepository.getWishFromWishlistID(wishlist.getId()));
        }
        return userWishList;
    }

    public List<Wish> getAllWishesFromWishListID(String id) {
        return wishRepository.getWishFromWishlistID(id);
    }

    public Wishlist createNewWishList(String name, int userID) {
        return wishListRepository.createNewWishList(name, userID);
    }

    public Wishlist getWishlistFromID(String id) {
        return wishListRepository.findById(id);
    }

    public boolean deleteWishlist(String WishlistID) {
        return wishListRepository.deleteWishlist(WishlistID);
    }

    public Wishlist updateWishListFromID(Wishlist wishlist) {
        return wishListRepository.updateWishListFromID(wishlist);
    }


}
