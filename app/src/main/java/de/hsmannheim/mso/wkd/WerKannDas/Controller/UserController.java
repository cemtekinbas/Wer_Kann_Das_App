package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUser(Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable("id") String userId, Model model)
    {
        User user = userService.getByID(Integer.parseInt(userId));
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, params = {"user"})
    public String editUser(@RequestParam("user") User user, Model model)
    {
        User savedUser = userService.save(user);
        model.addAttribute("success", savedUser != null);
        return "user";
    }
}
