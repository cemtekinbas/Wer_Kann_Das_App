package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    public class UserData
    {

    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUser(Model model)
    {
        return "user";

    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable("id") String userId, Model model)
    {
        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, params = {"user"})
    public String editUser(@RequestParam("user") UserData user, Model model)
    {
        return "user";
    }
}
