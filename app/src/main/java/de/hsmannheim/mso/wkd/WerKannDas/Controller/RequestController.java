package de.hsmannheim.mso.wkd.WerKannDas.Controller;

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
        return "request";
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public String createRequest(Model model)
    {
        return "request";
    }

    @RequestMapping(value = "/request/{requestId}", method = RequestMethod.GET, params = {"requestId"})
    public String showRequest(@PathVariable("requestId") String requestId, Model model)
    {
        return "request";
    }

    @RequestMapping(value = "/request/{requestId}", method = RequestMethod.POST, params = {"requestId"})
    public String editRequest(@PathVariable("requestId") String requestId, @RequestParam("request") RequestData request, Model model)
    {
        return "request";
    }

}
