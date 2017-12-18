package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Request;
import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import de.hsmannheim.mso.wkd.WerKannDas.Services.RequestService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RequestListController {

    @Autowired
    UserService userService;

    @Autowired
    RequestService requestService;

    @RequestMapping(value="/user/{userId}/requests", method = RequestMethod.GET, params = {"userId"})
    public String showUserDasboard(@PathVariable("userId") String userId, Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        User viewUser = userService.getByID(Integer.parseInt(userId));
        List<Request> requestList = requestService.getByUser(viewUser);
        model.addAttribute("user", user);
        model.addAttribute("viewUser", viewUser);
        model.addAttribute("requestList", requestList);
        return "hello";
    }
}
