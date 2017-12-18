package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.ChatService;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatMessage {
    private int pk;
    private int from_user_fk;
    private int to_user_fk;
    private String message;
    private Date sent_date;
    private Date read_date;

    public ChatMessage(int pk, int from_user_fk, int to_user_fk, String message, Date sent_date, Date read_date) {
        this.pk = pk;
        this.from_user_fk = from_user_fk;
        this.to_user_fk = to_user_fk;
        this.message = message;
        this.sent_date = sent_date;
        this.read_date = read_date;
    }

    public ChatMessage (ResultSet results) throws SQLException {
        this.pk = results.getInt(ChatService.colPk);
        this.from_user_fk = results.getInt(ChatService.colFromUserFk);
        this.to_user_fk = results.getInt(ChatService.colToUserFk);
        this.message = results.getString(ChatService.colMessage);
        this.sent_date = results.getDate(ChatService.colSentDate);
        this.read_date = results.getDate(ChatService.colReadDate);
    }

    public int getPk() {
        return pk;
    }

    public int getFrom_user_fk() {
        return from_user_fk;
    }

    public int getTo_user_fk() {
        return to_user_fk;
    }

    public String getMessage() {
        return message;
    }

    public Date getSent_date() {
        return sent_date;
    }

    public Date getRead_date() {
        return read_date;
    }
}
