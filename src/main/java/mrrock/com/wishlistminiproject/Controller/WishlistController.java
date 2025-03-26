package mrrock.com.wishlistminiproject.Controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
public class WishlistController {


    // Endpoint til at vise vores landingpage :)
    @GetMapping("/")
    public String landingPage(){
        return "landingpage";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session){
        return "loginPage";

    }
    @GetMapping("/register")
    public String registerPage(){
        return "registerPage";
    }
    @PostMapping("/login")
    public String requestLogIn(@RequestParam String username, @RequestParam String password){
        return "";
    }
    @PostMapping("/register")
    public String requestRegister(@RequestParam String username, @RequestParam String password,@RequestParam String name){
        return "";
    }



}
