package mrrock.com.wishlistminiproject.Controller;


import jakarta.servlet.http.HttpSession;
import mrrock.com.wishlistminiproject.Models.User;
import mrrock.com.wishlistminiproject.Models.Wish;
import mrrock.com.wishlistminiproject.Models.Wishlist;
import mrrock.com.wishlistminiproject.Service.UserService;
import mrrock.com.wishlistminiproject.Service.WishListService;
import mrrock.com.wishlistminiproject.Service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
public class WishlistController {

    private final UserService userService;
    private final WishListService wishListService;
    private final WishService wishService;

    public WishlistController(UserService userService, WishListService wishListService, WishService wishService) {
        this.userService = userService;
        this.wishListService = wishListService;
        this.wishService = wishService;
    }

    // Endpoint til at vise vores landingpage :)
    @GetMapping("/")
    public String landingPage(HttpSession session, Model model) {
        if (session.getAttribute("user") != null) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("user", session.getAttribute("user"));
            return "landingpage";
        }
        model.addAttribute("loggedIn", false);
        return "landingpage";
    }

    @GetMapping("/about")
    public String aboutPage(Model model, HttpSession session) {

        model.addAttribute("loggedIn", false);
        if (session.getAttribute("user") != null) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("user", session.getAttribute("user"));
        }
        return "about";
    }

    @GetMapping("/contact")
    public String contactPage(Model model, HttpSession session) {

        model.addAttribute("loggedIn", false);
        if (session.getAttribute("user") != null) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("user", session.getAttribute("user"));
        }
        return "contact";
    }

    //endpoint til at kunne tilgå loginsiden, Hvis man allerede er logget ind bliver du smidt hen til overview siden ;)
    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model, @RequestParam(value = "error", required = false) String error) {
        User user = (User) session.getAttribute("user");
        if (session.getAttribute("user") == null) {
            model.addAttribute("error", error);
            return "loginPage";
        }
        return "redirect:/" + user.getUsername();
    }

    //endpoint til at kunne tilgå registersiden, Hvis man allerede er logget ind bliver du smidt hen til overview siden ;)
    @GetMapping("/register")
    public String registerPage(HttpSession session, Model model, @RequestParam(value = "error", required = false) String error) {
        if (session.getAttribute("user") == null) {
            model.addAttribute("error", error);
            return "registerPage";
        }
        User user = (User) session.getAttribute("user");
        return "redirect:/" + user.getUsername();
    }

    //endpoint til at authenticate at oplysningerne matcher i databasen og på den måde blive logget ind
    @PostMapping("/login")
    public String requestLogIn(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        User authedUser = userService.authenticateLogin(username, password);
        if (authedUser != null) {
            session.setAttribute("user", authedUser);
            return "redirect:/" + authedUser.getUsername();
        }
        redirectAttributes.addAttribute("error", "Invalid Username or Password, try again");
        return "redirect:/login";
    }

    //endpoint til at oprette en bruger, den tjekker om det findes i forvejen og authenticater derefter
    @PostMapping("/register")
    public String requestRegister(@RequestParam String username, @RequestParam String password, @RequestParam String name, HttpSession session, RedirectAttributes redirectAttributes) {
        User newUser = userService.registerNewUser(username, password, name);
        if (newUser != null) {
            session.setAttribute("user", newUser);
            return "redirect:/" + newUser.getUsername();
        }
        redirectAttributes.addAttribute("error", "Invalid Username or Password, try again");
        return "redirect:/register";
    }

    @GetMapping("/{username}")
    public String overviewPage(HttpSession session, Model model, @PathVariable String username) {
        model.addAttribute("owner", false);
        model.addAttribute("loggedInUser", null);
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            model.addAttribute("owner", user.getUsername().equalsIgnoreCase(username));
            model.addAttribute("loggedInUser", session.getAttribute("user"));
        }
        model.addAttribute("user", userService.getUserFromUsername(username));
        model.addAttribute("ønskelister", wishListService.getAllwishListsFromUserID(userService.getUserFromUsername(username).getId()));
        return "overview";
    }

    @PostMapping("logoff")
    public String logOff(HttpSession session) {
        session.setAttribute("user", null);
        return "redirect:/";
    }

    @GetMapping("/createnewlist")
    public String createNewListSite(HttpSession session, Model model, @RequestParam(value = "error", required = false) String error) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        model.addAttribute("error", error);
        model.addAttribute("loggedInUser", session.getAttribute("user"));

        return "newWishlist";
    }

    @PostMapping("/createnewlist")
    public String newListRequest(@RequestParam String name, @RequestParam int userID, RedirectAttributes redirectAttributes, HttpSession session) {
        User user = (User) session.getAttribute("user");

        Wishlist wishlist = wishListService.createNewWishList(name, userID);
        if (wishlist == null) {
            redirectAttributes.addAttribute("error", "Something Went Wrong Try Again");
            return "redirect:/createnewlist";
        }
        return "redirect:/" + user.getUsername();
    }

    @GetMapping("/{username}/wishlist/{id}")
    public String showWishList(HttpSession session, Model model, @PathVariable String id, @PathVariable String username) {

        model.addAttribute("owner", false);
        if (userService.checkIfUserOwnList(id, (User) session.getAttribute("user"))) {
            model.addAttribute("owner", true);
        }
        model.addAttribute("loggedInUser", session.getAttribute("user"));
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        model.addAttribute("user", userService.getUserFromUsername(username));
        model.addAttribute("wish", wishListService.getAllWishesFromWishListID(id));
        model.addAttribute("wishlist", wishListService.getWishlistFromID(id));
        return "wishlist";
    }

    @GetMapping("/{id}/createnewwish")
    public String createNewWishSite(@PathVariable String id, HttpSession session, Model model, @RequestParam(value = "error", required = false) String error) {

        if (session.getAttribute("user") == null || !userService.checkIfUserOwnList(id, (User) session.getAttribute("user"))) {
            return "redirect:/login";
        }

        model.addAttribute("loggedInUser", session.getAttribute("user"));
        model.addAttribute("error", error);
        model.addAttribute("wishlistID", id);
        return "newWish";
    }

    @PostMapping("/createnewwish")
    public String newWishRequest(@ModelAttribute Wish wish, RedirectAttributes redirectAttributes, HttpSession session) {
        Wish newWish = wishService.createNewWish(wish);
        if (newWish == null) {
            redirectAttributes.addAttribute("error", "Something Went Wrong Try Again");
            return "redirect:/createnewlist";
        }
        return "redirect:/" + ((User) session.getAttribute("user")).getUsername() + "/wishlist/" + wish.getWishlistID();
    }

    @GetMapping("/{username}/wishlist/{wishlistID}/wish/{wishID}")
    public String viewWishPage(@PathVariable String wishID, HttpSession session, Model model, @PathVariable String username, @PathVariable String wishlistID) {

        model.addAttribute("wishID", wishID);
        model.addAttribute("owner", userService.checkIfUserOwnsWish((User) session.getAttribute("user"), wishID));
        model.addAttribute("ownerUsername", username);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("wish", wishService.getWishFromID(wishID));
        model.addAttribute("loggedIn", session.getAttribute("user") != null);
        return "wish";
    }


    @PostMapping("wishlist/delete/{wishlistID}")
    public String deleteWishlist(@PathVariable String wishlistID, HttpSession session) {
        if (userService.checkIfUserOwnList(wishlistID, (User) session.getAttribute("user"))) {
            wishListService.deleteWishlist(wishlistID);
        }
        return "redirect:/" + ((User) session.getAttribute("user")).getUsername();
    }


    @PostMapping("/delete/{wishID}")
    public String deleteWish(@PathVariable String wishID, HttpSession session, @RequestParam String wishListID) {


        if (userService.checkIfUserOwnsWish((User) session.getAttribute("user"), wishID)) {
            wishService.deleteWishFromID(wishID);
        }

        return "redirect:/" + ((User) session.getAttribute("user")).getUsername() + "/wishlist/" + wishListID;
    }

    @GetMapping("/edit/wishlist/{wishListID}")
    public String editWishListPage(Model model, HttpSession session, @PathVariable String wishListID) {
        model.addAttribute("loggedInUser", session.getAttribute("user"));
        if (userService.checkIfUserOwnList(wishListID, (User) session.getAttribute("user"))) {
            model.addAttribute("wishlist", wishListService.getWishlistFromID(wishListID));
            model.addAttribute("owner", true);
            return "editWishlist";
        }


        return "redirect:/login";
    }

    @PostMapping("/editwishlist")
    public String editWishListRequest(HttpSession session, @RequestParam String name, @RequestParam int userID, @RequestParam String id) {

        Wishlist wishlist = new Wishlist();
        wishlist.setName(name);
        wishlist.setUserID(userID);
        wishlist.setId(id);
        if (userService.checkIfUserOwnList(wishlist.getId(), (User) session.getAttribute("user"))) {
            wishListService.updateWishListFromID(wishlist);
        }

        return "redirect:/" + ((User) session.getAttribute("user")).getUsername();
    }

    @GetMapping("/edit/wish/{wishID}")
    public String editWishPage(Model model, HttpSession session, @PathVariable String wishID) {

        if (userService.checkIfUserOwnsWish((User) session.getAttribute("user"), wishID)) {
            model.addAttribute("wish", wishService.getWishFromID(wishID));
            model.addAttribute("owner", true);
            model.addAttribute("loggedInUser", session.getAttribute("user"));

            return "editWish";
        }

        return "redirect:/" + ((User) session.getAttribute("user")).getUsername();
    }

    @PostMapping("/editwish")
    public String editWishRequest(HttpSession session, @ModelAttribute Wish wish) {
        if (userService.checkIfUserOwnsWish((User) session.getAttribute("user"), wish.getId())) {
            wishService.updateWishFromID(wish);
        }

        return "redirect:/" + ((User) session.getAttribute("user")).getUsername() + "/wishlist/" + wish.getWishlistID();
    }

    @PostMapping("/reservewish")
    public String reserveWishRequest(HttpSession session, @RequestParam String wishID, @RequestParam String usernameFromOwner) {
        if (session.getAttribute("user") != null && !wishService.getWishFromID(wishID).isReserved()) {
            wishService.reserveWishFromId(wishID);
            return "redirect:/" + usernameFromOwner + "/wishlist/" + wishService.getWishFromID(wishID).getWishlistID() + "/wish/" + wishID;
        }
        return "redirect:/" + usernameFromOwner + "/wishlist/" + wishService.getWishFromID(wishID).getWishlistID() + "/wish/" + wishID;
    }


}
