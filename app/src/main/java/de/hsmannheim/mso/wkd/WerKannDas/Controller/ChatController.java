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

    protected class ViewChat
    {
        public String userTo;
        public int userId;
        public String requestId;
        public int unreadMessages;
    }

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String viewChat(Model model)
    {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        List<Chat> chats = chatService.getByUser(user);
        List<ViewChat> viewChats = generateViewChats(chats, user);
        model.addAttribute("user", user);
        model.addAttribute("chats", viewChats);
        return "chatUebersicht";
    }

    @RequestMapping(value = "/chat/{requestId}", method = RequestMethod.GET)
    public String openChat(@PathVariable("requestId") int requestId, Model model)
    {
        //requires two users (from and to), optional a list of chats is returned (if user is owner of request and therefore can have chats with multiple users)
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        List<Chat> chats = chatService.getByRequestID(requestId);
        List<ViewChat> viewChats = generateViewChats(chats, user);
        model.addAttribute("user", user);
        model.addAttribute("chats", viewChats);
        return "chatUebersicht";
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

    @RequestMapping(value = "/chat/{requestId}/{userId}", method = RequestMethod.GET)
    public String openChat(@PathVariable("requestId") int requestId, @PathVariable("userId") int user2Id, Model model)
    {
        //requires two users (from and to), optional a list of chats is returned (if user is owner of request and therefore can have chats with multiple users)
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        User userTo = userService.getByID(user2Id);
        Request request = requestService.getByID(requestId, user);
        Chat chat = chatService.getByUsersAndRequestID(userTo, requestId);
        if (chat == null)
        {
            chat = new Chat(requestId, new int[]{user.getPk(), userTo.getPk()});
        }
        if(request.getFromUserFk() == user.getPk())
        {
            model.addAttribute("user", user);
            model.addAttribute("user2", userTo);
            model.addAttribute("userId", userTo.getPk()+"");
        }
        else
        {
            model.addAttribute("user", user);
            userTo = userService.getByID(request.getFromUserFk());
            model.addAttribute("user2", userTo);
            model.addAttribute("userId", user.getPk()+"");
        }
        model.addAttribute("chat", chat);

        chatService.setRead(user.getPk(), userTo.getPk(), requestId);

        return "chat";
    }

    @RequestMapping(value = "/chat/{requestId}/{userId}", method = RequestMethod.POST)
    public String sendChatMessage(@PathVariable("requestId") int requestId, @PathVariable("userId") int user2Id, String message, Model model)
    {
        // requires two users (from and to)
        boolean success;
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = userService.getByName(username);
        User userTo = userService.getByID(user2Id);
        Request request = requestService.getByID(requestId, user);
        Chat chat = null;
        if(request.getFromUserFk() == user.getPk())
        {
            success = chatService.addChatMessage(user, userTo, requestId, message);
            model.addAttribute("user", user);
            model.addAttribute("user2", userTo);
            model.addAttribute("userId", userTo.getPk()+"");
            chat = chatService.getByUsersAndRequestID(userTo, requestId);
        }
        else
        {
            userTo = userService.getByID(request.getFromUserFk());
            success = chatService.addChatMessage(user, userTo, requestId, message);
            model.addAttribute("user", user);
            model.addAttribute("user2", userTo);
            model.addAttribute("userId", user.getPk()+"");
            chat = chatService.getByUsersAndRequestID(user, requestId);
        }
        if (chat == null)
        {
            chat = new Chat(requestId, new int[]{user.getPk(), userTo.getPk()});
        }
        model.addAttribute("success", success);
        model.addAttribute("chat", chat);
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
