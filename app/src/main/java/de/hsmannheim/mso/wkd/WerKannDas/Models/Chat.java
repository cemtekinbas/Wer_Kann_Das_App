package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.ChatService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Chat {
    private int[] userFks;
    private int request_fk;
    private List<ChatMessage> chatMessages;

    public Chat(int request_fk, int[] userFks) {
        this.userFks = userFks;
        this.request_fk = request_fk;
    }

    public Chat(ResultSet results) throws SQLException {
        int userFk1 = results.getInt(ChatService.colFromUserFk);
        int userFk2 = results.getInt(ChatService.colToUserFk);
        this.userFks = new int[]{userFk1, userFk2};
        this.request_fk = results.getInt(ChatService.colRequestFk);
    }

    public int[] getUserFks() {
        return userFks;
    }

    public int getRequest_fk() {
        return request_fk;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void addChatMessage(ChatMessage chatMessage) {

        if (this.chatMessages == null) {
            this.chatMessages = new ArrayList<ChatMessage>();
        }
        this.chatMessages.add(chatMessage);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Chat) {
            Chat c = (Chat) o;
            for (int userFK : c.getUserFks()) {
                if (userFK != this.userFks[0] && userFK != this.userFks[1]) {
                    return false;
                }
            }
            return (c.getRequest_fk() == this.request_fk);
        }
        return false;
    }
}
