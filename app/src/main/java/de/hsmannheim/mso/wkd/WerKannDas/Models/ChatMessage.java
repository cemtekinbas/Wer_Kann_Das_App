package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.ChatService;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChatMessage {
    private int pk;
    private String message;
    private Date sent_date;
    private Date read_date;

    public ChatMessage(int pk, String message, Date sent_date, Date read_date) {
        this.pk = pk;
        this.message = message;
        this.sent_date = sent_date;
        this.read_date = read_date;
    }
    public ChatMessage (ResultSet results) throws SQLException {
        this.pk = results.getInt(ChatService.colPk);
        this.message = results.getString(ChatService.colMessage);
        this.sent_date = results.getDate(ChatService.colSentDate);
        this.read_date = results.getDate(ChatService.colReadDate);
    }

    public Date getSent_date() {
        return sent_date;
    }

    public String getMessage() {
        return message;
    }

    public int getPk() {
        return pk;
    }

    public Date getRead_date() {
        return read_date;
    }
}
