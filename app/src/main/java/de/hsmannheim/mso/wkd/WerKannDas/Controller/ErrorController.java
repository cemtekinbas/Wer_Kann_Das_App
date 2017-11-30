package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ErrorController {

    @RequestMapping(value = "/403")
    public String show403(Model model)
    {
        return "403";
    }

    @RequestMapping(value = "/404")
    public String show404(Model model)
    {
        return "404";
    }

}
