package mrrock.com.wishlistminiproject.Repository;


import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Models.Wishlist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:h2init.sql"}
)
@SpringBootTest
@Transactional
@Rollback(true)
public class WishRepositoryTest {
    @Autowired
 private WishRepository wishRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishListRepository wishListRepository;

@Test
    public void makeNewWishFromStart() throws SQLException {
//Arrange
        //Starter med at lave en user
        userRepository.registerNewUser("testUser","123","testUserName");

        //henter den nye user fra DB
        User newlyCreatedUser = userRepository.findById("testUser");
        //Opretter ny Liste til den nye bruger
        wishListRepository.createNewWishList("testListe",newlyCreatedUser.getId());
        //Henter alle liste fra brugeren (han har kun en )
        List<Wishlist> wishlistList = wishListRepository.getAllwishListsFromUserID(newlyCreatedUser.getId());
        //vi opretter et nyt ønske
        Wish newWish = new Wish();
        newWish.setName("testØnske");
        newWish.setDescription("testØnskeDesc");
        newWish.setPrice(99);
        //setter ønskets ØnskeListeID til at være den nye liste
        newWish.setWishlistID(wishlistList.get(0).getId());
        wishRepository.createNewWish(newWish);
        //Henter alle ønsker fra et ønskeliste id (der er kun 1 ønske)
        List<Wish> wishList = wishRepository.getWishFromWishlistID(wishlistList.get(0).getId());


        //assert
        Assertions.assertEquals(1,wishList.size());
        Assertions.assertEquals("testØnske",wishList.get(0).getName());
        Assertions.assertEquals("testØnskeDesc",wishList.get(0).getDescription());
        Assertions.assertEquals(99,wishList.get(0).getPrice());
        Assertions.assertEquals(wishlistList.get(0).getId(),wishList.get(0).getWishlistID());






















    }
}
