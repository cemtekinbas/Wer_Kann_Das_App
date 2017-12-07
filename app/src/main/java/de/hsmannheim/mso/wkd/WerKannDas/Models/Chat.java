package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.ChatService;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Chat {
    private int pk;
    private String message;
    private int from_user_fk;
    private int to_user_fk;
    private Date sent_date;
    private Date read_date;

    public Chat(int pk, String message, int from_user_fk, int to_user_fk, Date sent_date, Date read_date) {
        this.pk = pk;
        this.message = message;
        this.from_user_fk = from_user_fk;
        this.to_user_fk = to_user_fk;
        this.sent_date = sent_date;
        this.read_date = read_date;
    }

    public Chat (ResultSet results) throws SQLException {
        this.pk = results.getInt(ChatService.colPk);
        this.message = results.getString(ChatService.colMessage);
        this.from_user_fk = results.getInt(ChatService.colFromUserFk);
        this.to_user_fk = results.getInt(ChatService.colToUserFk);
        this.sent_date = results.getDate(ChatService.colSentDate);
        this.read_date = results.getDate(ChatService.colReadDate);
    }

    public int getPk() {
        return pk;
    }

    public String getMessage() {
        return message;
    }

    public int getFrom_user_fk() {
        return from_user_fk;
    }

    public int getTo_user_fk() {
        return to_user_fk;
    }

    public Date getSent_date() {
        return sent_date;
    }

    public Date getRead_date() {
        return read_date;
    }
}
