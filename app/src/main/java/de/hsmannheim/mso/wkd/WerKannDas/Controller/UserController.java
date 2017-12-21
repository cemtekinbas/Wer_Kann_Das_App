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
        model.addAttribute("viewUser", user);
        return "user";
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable("id") String userId, Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        User viewUser = userService.getByID(Integer.parseInt(userId));
        model.addAttribute("user", user);
        model.addAttribute("viewUser", viewUser);
        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String editUser(User user, Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User currentUser = userService.getByName(username);
        User savedUser = userService.save(user);
        if(savedUser != null) {
            model.addAttribute("success", true);
            model.addAttribute("user", savedUser);
            model.addAttribute("viewUser", savedUser);
        }
        else
        {
            model.addAttribute("success", false);
            model.addAttribute("user", currentUser);
            model.addAttribute("viewUser", currentUser);
        }
        return "user";
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String registerUser(Model model)
    {
        User newUser = new User();
        model.addAttribute("user", newUser);
        return "registrieren";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String newUser(User user, Model model)
    {
        User savedUser = userService.save(user);
        if(savedUser != null) {
            model.addAttribute("success", true);
            model.addAttribute("user", savedUser);
            model.addAttribute("viewUser", savedUser);
            return "redirect:/";
        }
        else {
            model.addAttribute("success", false);
            model.addAttribute("user", user);
            return "registrieren";
        }
    }
}
