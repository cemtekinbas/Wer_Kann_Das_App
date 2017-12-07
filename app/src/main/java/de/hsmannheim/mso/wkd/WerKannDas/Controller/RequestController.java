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
public class RequestController {

    public class RequestData
    {

    }

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public String newRequest(Model model)
    {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userService.GetByName(username);
        model.addAttribute("newRequest", new RequestData());
        return "request";
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public String createRequest(@RequestParam("newRequest") RequestData requestData, Model model)
    {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userService.GetByName(username);
        //Request request = requestService.saveRequest(requestData);
        //model.addAttribute("request", request);
        return "request";
    }

    @RequestMapping(value = "/request/{requestId}", method = RequestMethod.GET, params = {"requestId"})
    public String showRequest(@PathVariable("requestId") String requestId, Model model)
    {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userService.GetByName(username);
        //Request request = requestService.GetByID(requestId);
        //model.addAttribute("request", request);
        return "request";
    }

    @RequestMapping(value = "/request/{requestId}", method = RequestMethod.POST, params = {"requestId"})
    public String editRequest(@PathVariable("requestId") String requestId, @RequestParam("request") RequestData requestData, Model model)
    {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userService.GetByName(username);
        //Request request = requestService.saveRequest(requestData);
        //model.addAttribute("success", success);
        //model.addAttribute("request", request);
        return "request";
    }

}
