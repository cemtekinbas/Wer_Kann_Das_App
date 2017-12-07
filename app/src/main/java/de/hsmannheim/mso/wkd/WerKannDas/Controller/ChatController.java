package de.hsmannheim.mso.wkd.WerKannDas.Controller;

import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
public class ChatController {

    public class ChatMessage
    {
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        private String message;
        private String requestId;
        private Date date;

        public ChatMessage(String message, String requestId, Date date)
        {
            this.message = message;
            this.date = date;
            this.requestId = requestId;
        }

    }

    @Autowired
    private UserService userService;

    //@Autowired
    //private ChatService chatService;


    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String viewChat(Model model)
    {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userService.getByName(username);
        //List<Chat> chats = chatService.getByUser(user);
        //model.AddAttribute("chats", chats);
        return "chat";
    }

    @RequestMapping(value = "/chat/{requestId}", method = RequestMethod.GET)
    public String openChat(@PathVariable("requestId") String requestId, Model model)
    {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userService.getByName(username);
        //Chat chat = chatService.getByRequestID(user, requestId);
        //model.AddAttribute("chat", chat);
        return "chat";
    }

    @RequestMapping(value = "/chat/{requestId}", method = RequestMethod.POST, params = {"message"})
    public String sendChatMessage(@PathVariable("requestId") String requestId, @RequestParam("message") String message, Model model)
    {
        String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        //User user = userService.getByName(username);
        //Chat chat = chatService.getByRequestID(user, requestId);
        //boolean success = chatService.addChatMessage(chat, chatMessage);
        //model.AddAttribute("chat", chat);
        //model.AddAttribute("success", success);
        return "chat";
    }

    @RequestMapping(value = "/chat/{requestId}/{skip}", method = RequestMethod.GET)
    @ResponseBody
    public List<ChatMessage> newMessages(@PathVariable("requestId") String requestId, @PathVariable("skip") String skip, Model model)
    {

        return null;
    }
}
