package melmon.user;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserManager userManager = new UserManager();


    @RequestMapping("/register")
    public String register(@RequestParam int userId) {
        if (userManager.registerUser(userId)) {
            return "User " + userId + " was registered";
        } else {
            return "User with id " + userId + " already exists";
        }
    }

    @RequestMapping("/buy")
    public String buy(@RequestParam int userId, @RequestParam String name, @RequestParam int quantity) {
        try {
            userManager.buyStock(userId, name, quantity);
        } catch (Exception e) {
            return e.getMessage();
        }
        return String.format("User %d successfully bought %d of stocks %s", userId, quantity, name);
    }

    @RequestMapping("/sell")
    public String sell(@RequestParam int userId, @RequestParam String name, @RequestParam int quantity) {
        try {
            userManager.sellStock(userId, name, quantity);
        } catch (Exception e) {
            return e.getMessage();
        }
        return String.format("User %d successfully sold %d of stocks %s", userId, quantity, name);
    }

    @RequestMapping("/add_money")
    public String addMoney(@RequestParam int userId, @RequestParam int additional) {
        try {
            userManager.addUserBalance(userId, additional);
        } catch (Exception e) {
            return e.getMessage();
        }
        return String.format("User %d successfully added %d to their account", userId, additional);

    }

    @RequestMapping("/value")
    public String value(@RequestParam int userId) {
        int value;
        try {
            value = userManager.getUserValue(userId);
        } catch (Exception e) {
            return e.getMessage();
        }
        return Integer.toString(value);
    }

    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam int userId) {
        String info;
        try {
            info = userManager.getUserInfo(userId);
        } catch (Exception e) {
            return e.getMessage();
        }
        return info;
    }


}