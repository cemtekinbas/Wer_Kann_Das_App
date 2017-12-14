package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.ChatService;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Chat {
    private int from_user_fk;
    private int to_user_fk;
    private int request_fk;
    private List<ChatMessage> chatMessages;

    public Chat(int pk, String message, int from_user_fk, int to_user_fk, int request_fk, Date sent_date, Date read_date) {
        this.from_user_fk = from_user_fk;
        this.to_user_fk = to_user_fk;
        this.request_fk = request_fk;
    }

    public Chat(ResultSet results) throws SQLException {
        this.from_user_fk = results.getInt(ChatService.colFromUserFk);
        this.to_user_fk = results.getInt(ChatService.colToUserFk);
        this.request_fk = results.getInt(ChatService.colRequestFk);
    }

    public int getFrom_user_fk() {
        return from_user_fk;
    }

    public int getTo_user_fk() {
        return to_user_fk;
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
}
