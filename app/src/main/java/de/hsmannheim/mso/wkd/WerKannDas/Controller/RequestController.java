package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Request;
import de.hsmannheim.mso.wkd.WerKannDas.Models.RequestState;
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

import java.sql.Date;
import java.time.LocalDate;

@Controller
public class RequestController {

    @Autowired
    UserService userService;

    @Autowired
    RequestService requestService;

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public String newRequest(Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        Request request = new Request(-1,user.getPk(), "", "", false, Date.valueOf(LocalDate.now()), RequestState.OPEN);
        model.addAttribute("user", user);
        model.addAttribute("newRequest", request);
        return "request";
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public String createRequest(@RequestParam("newRequest") Request requestData, Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        Request request = requestService.save(requestData);
        model.addAttribute("user", user);
        model.addAttribute("request", request);
        return "request";
    }

    @RequestMapping(value = "/request/{requestId}", method = RequestMethod.GET, params = {"requestId"})
    public String showRequest(@PathVariable("requestId") String requestId, Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        Request request = requestService.getByID(Integer.parseInt(requestId));
        model.addAttribute("user", user);
        model.addAttribute("request", request);
        return "request";
    }

    @RequestMapping(value = "/request/{requestId}", method = RequestMethod.POST, params = {"requestId"})
    public String editRequest(@PathVariable("requestId") String requestId, @RequestParam("request") Request requestData, Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        Request request = requestService.save(requestData);
        model.addAttribute("user", user);
        model.addAttribute("success", request != null);
        model.addAttribute("request", request);
        return "request";
    }

}
