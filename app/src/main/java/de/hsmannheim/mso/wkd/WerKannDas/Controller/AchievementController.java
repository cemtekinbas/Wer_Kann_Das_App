package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Achievement;
import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import de.hsmannheim.mso.wkd.WerKannDas.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
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
    @Autowired
    private RequestService requestService;
    @Autowired
    private RequestResponseService requestResponseService;

    @RequestMapping(value = "/achievements", method = RequestMethod.GET)
    public String showAchievemnts(Model model)
    {
        List<Achievement> achievements;
        String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(userName);
        updateAchievments(user);
        achievements = userAchievmentService.getByID(user);
        model.addAttribute("achievements", achievements);
        List<String> achivementIcons = new ArrayList<String>(8);
        for(int i = 0; i < 8; i++)
        {
            achivementIcons.add("\uf29c");
        }
        for(Achievement a : achievements)
        {
            achivementIcons.set(a.getPk(), a.getIcon_path());
        }
        model.addAttribute("achievements", achivementIcons);
        model.addAttribute("user", user);
        return "achievements";
    }

    private void updateAchievments(User user) {
        int gotHelp = requestService.countMyFullfilledRequests(user);
        int helped = requestResponseService.countMyHelpedRequests(user);
        List<Achievement> userHasAchievements = userAchievmentService.getByID(user);
        List<Achievement> achievements = achievementService.getAll();
        for (Achievement achievement:achievements) {
            if (!userHasAchievements.contains(achievement)){
                if (achievement.getName()=="Erst-Helfer" && helped > 0){
                    userAchievmentService.save(user, achievement);
                }

            }
        }
    }

    @RequestMapping(value = "/achievements/{id}", method = RequestMethod.GET)
    public String showAchievemnts(@PathVariable("id") int id, Model model)
    {
        Achievement achievement = achievementService.getByID(id);
        String userName = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(userName);
        model.addAttribute("achievement", achievement);
        model.addAttribute("user", user);
        return "achievement";
    }

}
