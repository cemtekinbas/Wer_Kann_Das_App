package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import de.hsmannheim.mso.wkd.WerKannDas.Services.SettingService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SettingController {

    @Autowired
    UserService userService;

    @Autowired
    SettingService settingService;

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    public String showUser(Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        model.addAttribute("user", user);
        return "settings";
    }
}

