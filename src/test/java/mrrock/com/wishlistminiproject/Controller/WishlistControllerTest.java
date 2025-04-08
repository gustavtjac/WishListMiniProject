package mrrock.com.wishlistminiproject.Controller;

import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Service.UserService;
import mrrock.com.wishlistminiproject.Service.WishListService;
import mrrock.com.wishlistminiproject.Service.WishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(WishlistController.class)
public class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private WishListService wishListService;

    @MockitoBean
    private WishService wishService;


    @BeforeEach
    void setUp() {

    }


    @Test
    void showFrontPage() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("landingpage"));
    }


    @Test
    void showLoginPageWhileNotLoggedIn() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("loginPage"));
    }

    @Test
    void showLoginPageWhileLoggedIn() throws Exception {
        User testUser = new User();
        testUser.setName("testuser");
        testUser.setPassword("123");
        testUser.setId(1);
        testUser.setUsername("testusername");
        mockMvc.perform(get("/login").sessionAttr("user", testUser)).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/" + testUser.getUsername()));
    }


}

