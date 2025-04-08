package mrrock.com.wishlistminiproject.Repository;

import mrrock.com.wishlistminiproject.Models.User;
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
public class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired UserRepository userRepository;

    @Test
    public void checkIfUserOwnsWishList() throws SQLException {
        //Laver en ny bruger til testen
        //Arrange
User testUser = userRepository.registerNewUser("tester","123","tester");

//opretter en ny ønskeliste med foreignkeyen af den nye bruger
        Wishlist newWishList = wishListRepository.createNewWishList("testListe",userRepository.findById("tester").getId());

        //Act
        List<Wishlist> denNyeBrugersØnsker = wishListRepository.getAllwishListsFromUserID(userRepository.findById("tester").getId());

        //Assert
        Assertions.assertNotEquals("Gamingliste",denNyeBrugersØnsker.get(0).getName());
       Assertions.assertEquals("testListe", denNyeBrugersØnsker.get(0).getName());
       assertNotNull(denNyeBrugersØnsker);









    }



}
