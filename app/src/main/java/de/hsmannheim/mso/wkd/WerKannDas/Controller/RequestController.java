package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Models.*;
import de.hsmannheim.mso.wkd.WerKannDas.Services.ChatService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.RequestResponseService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.RequestService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RequestController {

    @Autowired
    UserService userService;

    @Autowired
    RequestService requestService;

    @Autowired
    private ChatService chatService;

    @Autowired
    RequestResponseService requestResponseService;

    @Autowired
    ChatController chatController;


    protected class ViewChat
    {
        public String userTo;
        public int userId;
        public String requestId;
        public int unreadMessages;
    }

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public String newRequest(Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        Request request = new Request(-1, user.getPk(), "", "", false, Date.valueOf(LocalDate.now()), RequestState.OPEN);
        model.addAttribute("user", user);
        model.addAttribute("newRequest", request);
        return "anfrageErstellen";
    }

    @RequestMapping(value = "/request", method = RequestMethod.POST)
    public String createRequest(Request newRequest, Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);

        newRequest.setCreateDate(Date.valueOf(LocalDate.now()));
        newRequest.setState(RequestState.OPEN);
        newRequest.setFromUserFk(user.getPk());
        newRequest.setPremium(false);

        Request request = requestService.save(newRequest, user);
        model.addAttribute("user", user);
        if (request != null) {
            model.addAttribute("newRequest", request);
            model.addAttribute("success", true);
            return "redirect:/";
        } else {
            model.addAttribute("newRequest", newRequest);
            model.addAttribute("success", false);
            return "anfrageErstellen";
        }

    }

    @RequestMapping(value = "/request/{requestId}", method = RequestMethod.GET)
    public String showRequest(@PathVariable("requestId") int requestId, Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        Request request = requestService.getByID(requestId, user);
        model.addAttribute("user", user);
        if (request.getFromUserFk() == user.getPk()) {
            model.addAttribute("newRequest", request);
            return "anfrageErstellen";
        } else {
            RequestResponse response = requestResponseService.getByUserIDAndRequestId(user.getPk(), request.getPk());
            if (response != null) {
                model.addAttribute("response", response);
            }
            User owner = userService.getByID(request.getFromUserFk());
            model.addAttribute("owner", owner);
            model.addAttribute("request", request);
            return "anfrageDetail";
        }
    }

    @RequestMapping(value = "/request/{requestId}", method = RequestMethod.POST)
    public String editRequest(@PathVariable("requestId") int requestId, Request requestData, Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        Request request = requestService.save(requestData, user);
        model.addAttribute("user", user);
        if (request != null) {
            model.addAttribute("success", true);
            model.addAttribute("request", request);
        } else {
            model.addAttribute("success", false);
            model.addAttribute("newRequest", requestData);
        }
        return "anfrageDetail";
    }

    @RequestMapping(value = "/request_done/{requestId}", method = RequestMethod.GET)
    public String finishRequest(@PathVariable("requestId") int requestId, Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        List<Chat> chats = chatService.getByRequestID(requestId);
        List<ViewChat> viewChats = generateViewChats(chats, user);
        model.addAttribute("user", user);
        model.addAttribute("chats", viewChats);
        model.addAttribute("requestId", requestId);
        return "requestDoneUebersicht";
    }


    @RequestMapping(value = "/request_done/{requestId}/{userId}", method = RequestMethod.GET)
    public String finishRequest(@PathVariable("requestId") int requestId, @PathVariable("userId") int user2Id, Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        User helpingUser = userService.getByID(user2Id);
        Request request = requestService.getByID(requestId, user);
        RequestResponse response = requestResponseService.getByUserIDAndRequestId(helpingUser.getPk(),requestId);
        if (user.getPk() == request.getFromUserFk() && response != null){
            request.setState(RequestState.FULFILLED);
            requestService.save(request,user);
            response.setSuccess(true);
            requestResponseService.save(response);
        }
        model.addAttribute("user", user);
        return "redirect:/";
    }

    private List<ViewChat> generateViewChats(List<Chat> chats, User currentUser) {
        List<ViewChat> viewChats = new ArrayList<>(chats.size());;
        for(Chat c : chats)
        {
            ViewChat vc = new ViewChat();
            vc.requestId = c.getRequest_fk() + "";
            Request request = requestService.getByID(c.getRequest_fk(), currentUser);
            if(request.getFromUserFk() == c.getUserFks()[0])
            {
                vc.userId = c.getUserFks()[1];
                User toUser = userService.getByID(c.getUserFks()[1]);
                vc.userTo = toUser.getUser_name();
                vc.unreadMessages = chatService.getUnreadCount(currentUser.getPk(), toUser.getPk(), request.getPk());
            }
            else
            {
                vc.userId = c.getUserFks()[0];
                User toUser = userService.getByID(c.getUserFks()[0]);
                vc.userTo = toUser.getUser_name();
                vc.unreadMessages = chatService.getUnreadCount(currentUser.getPk(), toUser.getPk(), request.getPk());
            }
            viewChats.add(vc);
        }
        return viewChats;
    }


    @RequestMapping(value = "/request/{requestId}/{responseValue}", method = RequestMethod.GET)
    public String openChat(@PathVariable("requestId") int requestId, @PathVariable("responseValue") boolean can, Model model) {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        Request request = requestService.getByID(requestId, user);
        if (user.getPk() != request.getFromUserFk()) {
            RequestResponse response = new RequestResponse(requestId, user.getPk(), Date.valueOf(LocalDate.now()), can, false);
            requestResponseService.save(response);
            return chatController.sendChatMessage(requestId, request.getFromUserFk(), "Ich kann's!", model);
        }
        return showRequest(requestId, model);
    }

}
