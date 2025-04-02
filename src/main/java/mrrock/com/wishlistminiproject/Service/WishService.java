package mrrock.com.wishlistminiproject.Service;

import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Repository.UserRepository;
import mrrock.com.wishlistminiproject.Repository.WishListRepository;
import mrrock.com.wishlistminiproject.Repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishService {
    @Autowired
    private WishRepository wishRepository;
    public Wish createNewWish(Wish wish){
        return wishRepository.createNewWish(wish);
    }

    public Wish getWishFromID(String wishID){
        return wishRepository.getWishFromID(wishID);
    }
    public boolean deleteWishFromID(String WishID){
        return wishRepository.deleteWishFromID(WishID);
    }
    public Wish updateWishFromID(Wish wish){
        return wishRepository.updateWishFromID(wish);
    }
    public Wish reserveWishFromId(String wishID){
        return wishRepository.reserveWishFromId(wishID);
    }

}
