package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Achievment;
import de.hsmannheim.mso.wkd.WerKannDas.Services.AchievmentService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedList;
import java.util.List;

@Controller
public class AchievementController {

    @Autowired
    private AchievmentService achievmentService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/achievements", method = RequestMethod.GET)
    public String showAchievemnts(Model model)
    {
        List<Achievment> achievments = new LinkedList<Achievment>();
        String userName = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //de.hsmannheim.mso.wkd.WerKannDas.Models.User user = userService.getByName(userName);
        //achievmentService.getAchievements(userName);
        model.addAttribute("achievements", achievments);

        return "achievements";
    }

    @RequestMapping(value = "/achievements/{id}", method = RequestMethod.GET)
    public String showAchievemnts(@PathVariable("id") String id, Model model)
    {
        //Achievment achievment = achievmentService.getByID(id);
        //model.addAttribute("achievment", achievment);
        return "achievement";
    }

}
