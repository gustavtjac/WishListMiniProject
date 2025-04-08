package mrrock.com.wishlistminiproject.Service;

import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Repository.UserRepository;
import mrrock.com.wishlistminiproject.Repository.WishListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class UserServiceTest {


    @Test
    void getUserByUsername_ReturnsUser() {
        WishListRepository mockWishRepo = mock(WishListRepository.class);
        UserRepository mockRepo = mock(UserRepository.class);
        UserService userService = new UserService(mockRepo, mockWishRepo);
        User testUser = new User();
        testUser.setUsername("findMyUser");
        testUser.setName("testName");
        testUser.setPassword("123");

        when(mockRepo.findById("findMyUser")).thenReturn(testUser);


        User result = userService.getUserFromUsername("findMyUser");
        assertEquals(testUser.getUsername(), result.getUsername());
        assertEquals(testUser.getName(), result.getName());
        assertEquals(testUser.getId(), result.getId());
        verify(mockRepo, times(1)).findById(testUser.getUsername());
    }


}
