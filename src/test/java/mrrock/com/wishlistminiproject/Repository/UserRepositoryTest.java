package mrrock.com.wishlistminiproject.Repository;

import mrrock.com.wishlistminiproject.Models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Sql(
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:h2init.sql"}
)
@SpringBootTest
@Transactional
@Rollback(true)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findById() throws SQLException {

        //arrange
        User newUser = userRepository.registerNewUser("testUser", "123", "testUserName");

        //Act
        User foundUser = userRepository.findById("testUser");

        //Assert
        assertNotNull(foundUser);
        Assertions.assertEquals(newUser.getName(), foundUser.getName());
        Assertions.assertEquals(newUser.getId(), foundUser.getId());

    }

}
