package mrrock.com.wishlistminiproject.Service;

import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Repository.WishRepository;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository =wishRepository;
    }

    public Wish createNewWish(Wish wish){
        return wishRepository.createNewWish(wish);
    }

    public Wish getWishFromID(String wishID){
        return wishRepository.findById(wishID);
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
