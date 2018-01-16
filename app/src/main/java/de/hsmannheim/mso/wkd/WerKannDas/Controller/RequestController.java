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
        return "anfrageErstellen";
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public String createRequest(Request newRequest, Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);

        newRequest.setCreateDate(Date.valueOf(LocalDate.now()));
        newRequest.setState(RequestState.OPEN);
        newRequest.setFromUserFk(user.getPk());
        newRequest.setPremium(false);

        Request request = requestService.save(newRequest);
        model.addAttribute("user", user);
        if(request != null) {
            model.addAttribute("newRequest", request);
            model.addAttribute("success", true);
            return "redirect:/";
        }
        else
        {
            model.addAttribute("newRequest", newRequest);
            model.addAttribute("success", false);
            return "anfrageErstellen";
        }

    }

    @RequestMapping(value = "/request/{requestId}", method = RequestMethod.GET)
    public String showRequest(@PathVariable("requestId") int requestId, Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        Request request = requestService.getByID(requestId);
        model.addAttribute("user", user);
        if(request.getFromUserFk() == user.getPk())
        {
            model.addAttribute("newRequest", request);
            return "anfrageErstellen";
        }
        else {
            model.addAttribute("request", request);
            return "anfrageDetail";
        }
    }

    @RequestMapping(value = "/request/{requestId}", method = RequestMethod.POST)
    public String editRequest(@PathVariable("requestId") int requestId, Request requestData, Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        Request request = requestService.save(requestData);
        model.addAttribute("user", user);
        if(request != null) {
            model.addAttribute("success", true);
            model.addAttribute("request", request);
        }
        else
        {
            model.addAttribute("success", false);
            model.addAttribute("newRequest", requestData);
        }
        return "anfrageDetail";
    }

}
