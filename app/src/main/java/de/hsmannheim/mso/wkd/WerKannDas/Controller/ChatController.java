package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Chat;
import de.hsmannheim.mso.wkd.WerKannDas.Models.ChatMessage;
import de.hsmannheim.mso.wkd.WerKannDas.Models.Request;
import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import de.hsmannheim.mso.wkd.WerKannDas.Services.ChatService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.RequestService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private RequestService requestService;

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String viewChat(Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        List<Chat> chats = chatService.getByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("chats", chats);
        return "chat";
    }

    @RequestMapping(value = "/chat/{requestId}", method = RequestMethod.GET)
    public String openChat(@PathVariable("requestId") int requestId, Model model)
    {
        //requires two users (from and to), optional a list of chats is returned (if user is owner of request and therefore can have chats with multiple users)
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        List<Chat> chats = chatService.getByRequestID(requestId);
        model.addAttribute("user", user);
        model.addAttribute("chats", chats);
        return "chat";
    }

    @RequestMapping(value = "/chat/{requestId}/{userId}", method = RequestMethod.POST)
    public String sendChatMessage(@PathVariable("requestId") int requestId, @PathVariable("userId") int user2Id, String message, Model model)
    {
        // requires two users (from and to)
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User userFrom = userService.getByName(username);
        User userTo = userService.getByID(user2Id);
        Request request = requestService.getByID(requestId);
        boolean success;
        if(userFrom.getPk() == request.getFromUserFk())
        {
            success = chatService.addChatMessage(userTo, userFrom, requestId, message);
        }
        else
        {
            success = chatService.addChatMessage(userFrom, userTo, requestId, message);
        }
        Chat chat = chatService.getByUsersAndRequestID(userTo, requestId);
        model.addAttribute("user", userFrom);
        model.addAttribute("user2", userTo);
        model.addAttribute("chat", chat);
        model.addAttribute("success", success);
        return "chat";
    }

    @RequestMapping(value = "/chat/{requestId}/{userId}/{skip}", method = RequestMethod.GET)
    @ResponseBody
    public List<ChatMessage> newMessages(@PathVariable("userId") int userId, @PathVariable("requestId") int requestId, @PathVariable("skip") int skip, Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User userTo = userService.getByID(userId);
        Chat chat = chatService.getByUsersAndRequestID(userTo, requestId);

        if(skip < chat.getChatMessages().size()) {
            return chat.getChatMessages().subList(skip, chat.getChatMessages().size());
        }
        else
        {
            return new ArrayList<ChatMessage>();
        }
    }
}
