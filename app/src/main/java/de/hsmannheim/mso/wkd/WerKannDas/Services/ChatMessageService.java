package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Chat;
import de.hsmannheim.mso.wkd.WerKannDas.Models.ChatMessage;
import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageService {

    public static String table = "chat_message";
    public static String colPk = "pk";
    public static String colMessage = "message";
    public static String colChatFk = "chat_fk";
    public static String colSentDate = "sent_date";
    public static String colReadDate = "read_date";

    public static String schema = "CREATE TABLE " + table + " ( " +
            colPk + " INT(11)  NOT NULL AUTO_INCREMENT, " +
            colMessage + " VARCHAR(250) NOT NULL, " +
            colChatFk + " INT(11), " +
            colSentDate + " DATETIME NOT NULL DEFAULT now(), " +
            colReadDate + " DATETIME DEFAULT NULL, " +
            "PRIMARY KEY (" + colPk + "), " +
            "CONSTRAINT chat_to_user_fk FOREIGN KEY (" + colChatFk + ") REFERENCES " +
            ChatService.table + " (" + ChatService.colPk + ")" +
            "ON DELETE CASCADE " +
            "ON UPDATE CASCADE " +
            ");";

    public static String combinedCols = colPk + ", " +
            colMessage + ", " +
            colChatFk + ", " +
            colSentDate + ", " +
            colReadDate;

    private String queryByChatID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colChatFk + " = ?";
    private String queryAddMessage = "INSERT INTO " + table + " (" + colMessage + "," + colChatFk + ") VALUE (?, ?)";

    @Autowired
    private DataSource ds;

    public List<ChatMessage> getByChatID(int chatFk) {        //Prepared Statements in allen Service Klassen
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByChatID);
            pstmt.setInt(1, chatFk);
            ResultSet results = pstmt.executeQuery();
            List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
            while (results.next()) {
                chatMessages.add(new ChatMessage(results));
            }
            return chatMessages;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Chat update(Chat chat) {
        return null;
    }

    public Chat save(Chat chat) {
        return null;
    }

    public boolean addChatMessage(Chat chat, String message) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryAddMessage, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, message);
            pstmt.setInt(2, chat.getPk());
            pstmt.executeUpdate();
            ResultSet results = pstmt.getGeneratedKeys();
            if(results.next()) {
                int id = results.getInt(colPk);
                return true;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
