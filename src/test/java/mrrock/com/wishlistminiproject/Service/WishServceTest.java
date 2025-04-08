package mrrock.com.wishlistminiproject.Service;

import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Models.Wishlist;
import mrrock.com.wishlistminiproject.Repository.WishListRepository;
import mrrock.com.wishlistminiproject.Repository.WishRepository;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class WishServceTest {


    @Test
    void getWishListFromID_ShouldReturnWishList(){
        WishRepository mockRepo = mock(WishRepository.class);
        WishService wishService = new WishService(mockRepo);
        Wish testWish = new Wish();
        testWish.setId(UUID.randomUUID().toString());
        testWish.setName("TestØnske");
        testWish.setPrice(100);

        when(mockRepo.findById(testWish.getId())).thenReturn(testWish);


        Wish result = mockRepo.findById(testWish.getId());


        assertEquals(testWish.getId(),result.getId());
        assertEquals(testWish.getName(), result.getName());
        assertEquals(testWish.getPrice(),result.getPrice());
        verify(mockRepo,times(1)).findById(testWish.getId());







    }
}
