package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Chat;
import de.hsmannheim.mso.wkd.WerKannDas.Models.ChatMessage;
import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import de.hsmannheim.mso.wkd.WerKannDas.Services.ChatService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String viewChat(Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        List<Chat> chats = chatService.getByUser(user);
        model.addAttribute("chats", chats);
        return "chat";
    }

    @RequestMapping(value = "/chat/{requestId}", method = RequestMethod.GET)
    public String openChat(@PathVariable("requestId") String requestId, Model model)
    {
        //TODO requires two users (from and to), optional a list of chats is returned (if user is owner of request and therefore can have chats with multiple users)
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        List<Chat> chats = chatService.getByRequestID(user, requestId);
        model.addAttribute("chat", chats);
        //model.addAttribute("chat", chat);
        return "chat";
    }

    @RequestMapping(value = "/chat/{requestId}", method = RequestMethod.POST, params = {"message"})
    public String sendChatMessage(@PathVariable("requestId") String requestId, @RequestParam("message") String message, Model model)
    {
        //TODO requires two users (from and to)
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        //Chat chat = chatService.getByRequestID(user, requestId);
        //boolean success = chatService.addChatMessage(chat, message);
        //model.addAttribute("chat", chat);
        //model.addAttribute("success", success);
        return "chat";
    }

    @RequestMapping(value = "/chat/{requestId}/{skip}", method = RequestMethod.GET)
    @ResponseBody
    public List<ChatMessage> newMessages(@PathVariable("requestId") String requestId, @PathVariable("skip") String skip, Model model)
    {

        return null;
    }
}
