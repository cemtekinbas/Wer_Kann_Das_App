package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

	@RequestMapping(value={"","/"}, method=RequestMethod.GET)
	public String dashboard(Model model) {
		return "home";
	}

    @RequestMapping(value={"","/"}, method=RequestMethod.GET, params = {"search"})
    public String dashboardSearch(@RequestParam("search") String search, Model model) {
        return "home";
    }

    @RequestMapping(value={"","/"}, method=RequestMethod.GET, params = {"sort"})
    public String dashboardSort(@RequestParam("sort") String sort, Model model) {
        return "home";
    }

    @RequestMapping(value={"","/"}, method=RequestMethod.GET, params = {"search", "sort"})
    public String dashboard(@RequestParam("search") String search, @RequestParam("sort") String sort, Model model) {
        return "home";
    }

}
