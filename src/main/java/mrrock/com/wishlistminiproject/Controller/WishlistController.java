package mrrock.com.wishlistminiproject.Controller;


import jakarta.servlet.http.HttpSession;
import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Models.Wishlist;
import mrrock.com.wishlistminiproject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller()
public class WishlistController {


    @Autowired
private UserService userService;
    // Endpoint til at vise vores landingpage :)
    @GetMapping("/")
    public String landingPage(HttpSession session,Model model) {
        if(session.getAttribute("user") !=null){
            model.addAttribute("loggedIn",true);
            return "landingpage";
        }

        model.addAttribute("loggedIn",false);
        return "landingpage";
    }

    //endpoint til at kunne tilgå loginsiden, Hvis man allerede er logget ind bliver du smidt hen til overview siden ;)
    @GetMapping("/login")
    public String loginPage(HttpSession session,Model model,@RequestParam(value = "error", required = false) String error) {
        if (session.getAttribute("user") == null) {
            model.addAttribute("error",error);
            return "loginPage";
        }
        return "redirect:/overview";
    }
    //endpoint til at kunne tilgå registersiden, Hvis man allerede er logget ind bliver du smidt hen til overview siden ;)
    @GetMapping("/register")
    public String registerPage(HttpSession session,Model model,@RequestParam(value = "error", required = false) String error) {
        if (session.getAttribute("user") == null) {
            model.addAttribute("error",error);
            return "registerPage";
        }
        return "redirect:/overview";
    }

    //endpoint til at authenticate at oplysningerne matcher i databasen og på den måde blive logget ind
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

    //endpoint til at oprette en bruger, den tjekker om det findes i forvejen og authenticater derefter
    @PostMapping("/register")
    public String requestRegister(@RequestParam String username, @RequestParam String password, @RequestParam String name,HttpSession session, RedirectAttributes redirectAttributes) {
        User newUser = userService.registerNewUser(username,password,name);
        if (newUser!=null) {
            session.setAttribute("user", newUser);
            return "redirect:/overview";
        }
        redirectAttributes.addAttribute("error","Invalid Username or Password, try again");
        return "redirect:/register";
    }
    @GetMapping("/overview")
    public String overviewPage(HttpSession session,Model model){
        if (session.getAttribute("user")==null){
            return "redirect:/login";
        }
        model.addAttribute("user",session.getAttribute("user"));
        model.addAttribute("ønskelister",userService.getAllwishListsFromUserID(((User) session.getAttribute("user")).getId()));
        return "overview";
    }

    @PostMapping("logoff")
    public String logOff(HttpSession session){
        session.setAttribute("user",null);
        return "redirect:/";
    }

    @GetMapping("/createnewlist")
    public String createNewListSite(HttpSession session,Model model, @RequestParam(value = "error", required = false) String error){
        if (session.getAttribute("user")==null){
            return "redirect:/login";
        }
        model.addAttribute("error",error);
        model.addAttribute("user",(User) session.getAttribute("user"));
        return "newWishlist";
    }

    @PostMapping("/createnewlist")
    public String newListRequest(@RequestParam String name, @RequestParam int userID, RedirectAttributes redirectAttributes){
        Wishlist wishlist = userService.createNewWishList(name,userID);
        if (wishlist==null){
            redirectAttributes.addAttribute("error","Something Went Wrong Try Again");
            return "redirect:/createnewlist";
        }
        return "redirect:/overview";
    }

    @GetMapping("/wishlist/{id}")
    public String showWishList(HttpSession session, Model model, @PathVariable int id) {

        model.addAttribute("owner",false);
        if (userService.checkIfUserOwnList(id,(User) session.getAttribute("user"))){
            model.addAttribute("owner",true);
        }


        model.addAttribute("user",(User) session.getAttribute("user"));
        model.addAttribute("wish", userService.getAllWishesFromWishListID(id));
        model.addAttribute("wishlist", userService.getWishlistFromID(id));
        return "wishlist";
    }

    @GetMapping("/{id}/createnewwish")
    public String createNewWishSite(@PathVariable int id,HttpSession session,Model model, @RequestParam(value = "error", required = false) String error){

        if (session.getAttribute("user")==null|| !userService.checkIfUserOwnList(id,(User) session.getAttribute("user"))){
            return "redirect:/login";
        }
        model.addAttribute("error",error);
        model.addAttribute("wishlistID",id);
        return "newWish";
    }
    @PostMapping("/createnewwish")
    public String newWishRequest(@ModelAttribute Wish wish,RedirectAttributes redirectAttributes){
        Wish newWish =userService.createNewWish(wish);
        if (newWish==null){
            redirectAttributes.addAttribute("error","Something Went Wrong Try Again");
            return "redirect:/createnewlist";
        }
        return "redirect:/wishlist/" + wish.getWishlistID();
    }





}
