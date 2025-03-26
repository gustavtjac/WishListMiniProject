package mrrock.com.wishlistminiproject.Controller;


import jakarta.servlet.http.HttpSession;
import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
public class WishlistController {
    private User user;


    @Autowired
private UserService userService;
    // Endpoint til at vise vores landingpage :)
    @GetMapping("/")
    public String landingPage() {
        return "landingpage";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session,Model model,@RequestParam(value = "error", required = false) String error) {
        if ((User) session.getAttribute("user") == null) {
            model.addAttribute("error",error);
            return "redirect:/overview";
        }
        return "loginPage";
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session,Model model) {
        if ((User) session.getAttribute("user") == null) {
            return "redirect:/overview";
        }
        return "registerPage";
    }

    @PostMapping("/login")
    public String requestLogIn(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        User authedUser = userService.authenticateLogin(username,password);
if (authedUser!= null){
    session.setAttribute("user",authedUser);
    return "redirect:/overview";
}
redirectAttributes.addAttribute("error","Invalid Username or Password, try again");
        return "redirect:/login";
    }

    @PostMapping("/register")
    public String requestRegister(@RequestParam String username, @RequestParam String password, @RequestParam String name) {
        return "";
    }


}
