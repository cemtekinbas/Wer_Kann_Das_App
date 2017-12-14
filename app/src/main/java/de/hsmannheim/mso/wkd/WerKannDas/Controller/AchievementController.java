package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Achievement;
import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import de.hsmannheim.mso.wkd.WerKannDas.Services.AchievementService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.UserAchievementMapperService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserAchievementMapperService userAchievmentService;
    @Autowired
    private AchievementService achievementService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/achievements", method = RequestMethod.GET)
    public String showAchievemnts(Model model)
    {
        List<Achievement> achievements = new LinkedList<Achievement>();
        String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(userName);
        userAchievmentService.getByID(user);
        model.addAttribute("achievements", achievements);

        return "achievements";
    }

    @RequestMapping(value = "/achievements/{id}", method = RequestMethod.GET)
    public String showAchievemnts(@PathVariable("id") String id, Model model)
    {
        Achievement achievement = achievementService.getByID(Integer.parseInt(id));
        model.addAttribute("achievment", achievement);
        return "achievement";
    }

}
