package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AchievementController {

    @RequestMapping(value = "/achievements", method = RequestMethod.GET)
    public String showAchievemnts(Model model)
    {
        return "achievements";
    }

    @RequestMapping(value = "/achievements/{id}", method = RequestMethod.GET)
    public String showAchievemnts(@PathVariable("id") String id, Model model)
    {
        return "achievement";
    }

}
