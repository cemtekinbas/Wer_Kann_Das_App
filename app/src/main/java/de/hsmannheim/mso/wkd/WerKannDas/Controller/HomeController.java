package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Request;
import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import de.hsmannheim.mso.wkd.WerKannDas.Services.RequestService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    RequestService requestService;

	@RequestMapping(value={"","/"}, method=RequestMethod.GET)
	public String dashboard(Model model) {
	    String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	    User user = userService.getByName(username);
	    List<Request> requests = requestService.getList();
		model.addAttribute("requestList", requests);
	    return "home";
	}

    @RequestMapping(value={"","/"}, method=RequestMethod.GET, params = {"search"})
    public String dashboardSearch(@RequestParam("search") String search, Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        List<Request> requests = requestService.getList(search);
        model.addAttribute("requestList", requests)
	    return "home";
    }

    @RequestMapping(value={"","/"}, method=RequestMethod.GET, params = {"sort"})
    public String dashboardSort(@RequestParam("sort") String sort, Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        List<Request> requests = requestService.getList();
        switch(sort)
        {
            case "up":
                break;
            case "down":
                break;
        }
        model.addAttribute("requestList", requests)
	    return "home";
    }

    @RequestMapping(value={"","/"}, method=RequestMethod.GET, params = {"search", "sort"})
    public String dashboard(@RequestParam("search") String search, @RequestParam("sort") String sort, Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        List<Request> requests = requestService.getList(search);
        switch(sort)
        {
            case "up":
                break;
            case "down":
                break;
        }
        model.addAttribute("requestList", requests)
        return "home";
    }

}
