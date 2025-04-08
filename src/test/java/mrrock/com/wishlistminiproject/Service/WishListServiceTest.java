package mrrock.com.wishlistminiproject.Service;


import mrrock.com.wishlistminiproject.Models.Wishlist;
import mrrock.com.wishlistminiproject.Repository.WishListRepository;
import mrrock.com.wishlistminiproject.Repository.WishRepository;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class WishListServiceTest {


    @Test
    void getWishListFromID_ShouldReturnWishList() {

        WishRepository mockWishRepo = mock(WishRepository.class);
        WishListRepository mockRepo = mock(WishListRepository.class);
        WishListService wishListService = new WishListService(mockRepo, mockWishRepo);
        Wishlist testWishList = new Wishlist();
        testWishList.setId(UUID.randomUUID().toString());
        testWishList.setName("TestListe");
        testWishList.setUserID(2);

        when(mockRepo.findById(testWishList.getId())).thenReturn(testWishList);


        Wishlist result = mockRepo.findById(testWishList.getId());


        assertEquals(testWishList.getId(), result.getId());
        assertEquals(testWishList.getName(), result.getName());
        assertEquals(testWishList.getUserID(), result.getUserID());
        verify(mockRepo, times(1)).findById(testWishList.getId());


    }
}
