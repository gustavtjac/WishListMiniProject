package mrrock.com.wishlistminiproject.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class WishlistController {


    // Endpoint til at vise vores landingpage :)
    @GetMapping("/")
    public String landingPage(){
        return "landingpage";
    }

}
