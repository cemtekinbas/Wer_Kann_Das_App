package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

	@RequestMapping(value={"","/"}, method=RequestMethod.GET)
	public String dashboard(Model model) {
	    String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	    //User user = userService.GetByName(username);
	    //List<Request> requests = requestService.GetList();
		//model.addAttribute("requestList", requests)
	    return "home";
	}

    @RequestMapping(value={"","/"}, method=RequestMethod.GET, params = {"search"})
    public String dashboardSearch(@RequestParam("search") String search, Model model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userService.GetByName(username);
        //List<Request> requests = requestService.GetList(search);
        //model.addAttribute("requestList", requests)
	    return "home";
    }

    @RequestMapping(value={"","/"}, method=RequestMethod.GET, params = {"sort"})
    public String dashboardSort(@RequestParam("sort") String sort, Model model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userService.GetByName(username);
        //List<Request> requests = requestService.GetList();
        switch(sort)
        {
            case "up":
                break;
            case "down":
                break;
        }
        //model.addAttribute("requestList", requests)
	    return "home";
    }

    @RequestMapping(value={"","/"}, method=RequestMethod.GET, params = {"search", "sort"})
    public String dashboard(@RequestParam("search") String search, @RequestParam("sort") String sort, Model model) {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userService.GetByName(username);
        //List<Request> requests = requestService.GetList(search);
        switch(sort)
        {
            case "up":
                break;
            case "down":
                break;
        }
        //model.addAttribute("requestList", requests)
        return "home";
    }

}
