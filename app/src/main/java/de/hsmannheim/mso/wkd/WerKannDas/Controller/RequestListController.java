package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestListController {

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public String showDashboard(Model model)
    {

        return "dashboard";
    }

    @RequestMapping(value="/user/{userId}/requests", method = RequestMethod.GET, params = {"userId"})
    public String showUserDasboard(@PathVariable("userId") String userId, Model model)
    {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userService.GetByName(username);
        //User viewUser = userService.GetByID(userId);
        //List<Request> requestList = requestService.getByUser(viewUser);
        //model.addAttribute("viewUser", viewUser);
        //model.addAttribute("requestList", requestList);
        return "dasboard";
    }
}
