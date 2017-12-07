package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Chat;
import de.hsmannheim.mso.wkd.WerKannDas.Models.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    public static String table = "chat";
    public static String colPk = "pk";
    public static String colMessage = "message";
    public static String colFromUserFk = "from_user_fk";
    public static String colToUserFk = "to_user_fk";
    public static String colRequestFk = "request_fk";
    public static String colSentDate = "sent_date";
    public static String colReadDate = "read_date";

    public static String schema = "CREATE TABLE " + table + " ( " +
            colPk + " INT(11)  NOT NULL AUTO_INCREMENT, " +
            colMessage + " VARCHAR(250) NOT NULL, " +
            colFromUserFk + " INT(11), " +
            colToUserFk + " INT(11), " +
            colRequestFk + " INT(11), " +
            colSentDate + " DATETIME NOT NULL DEFAULT now(), " +
            colReadDate + " DATETIME DEFAULT NULL, " +
            "PRIMARY KEY (" + colPk + "), " +
            "CONSTRAINT chat_from_user_fk FOREIGN KEY (" + colFromUserFk + ") REFERENCES " +
            UserService.table + " (" + UserService.colPk + ")" +
            "ON DELETE CASCADE " +
            "ON UPDATE CASCADE, " +
            "CONSTRAINT chat_request_fk FOREIGN KEY (" + colRequestFk + ") REFERENCES " +
            RequestService.table + " (" + RequestService.colPk + ")" +
            "ON DELETE CASCADE " +
            "ON UPDATE CASCADE, " +
            "CONSTRAINT chat_to_user_fk FOREIGN KEY (" + colToUserFk + ") REFERENCES " +
            UserService.table + " (" + UserService.colPk + ")" +
            "ON DELETE CASCADE " +
            "ON UPDATE CASCADE " +
            ");";

    public static String combinedCols = colPk + ", " +
            colMessage + ", " +
            colFromUserFk + ", " +
            colToUserFk + ", " +
            colRequestFk + ", " +
            colSentDate + ", " +
            colReadDate;

    private String queryByID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colPk + " = ?";

    @Autowired
    private DataSource ds;

    public Chat getByID(int pk) {        //Prepared Statements in allen Service Klassen
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByID);
            pstmt.setInt(1, pk);
            ResultSet results = pstmt.executeQuery();
            List<ChatMessage> chatMessages = new ArrayList<ChatMessage>();
            Chat chat = null;
            while (results.next()) {
                if (chat == null) {
                    chat = new Chat(results);
                }
                chatMessages.add(new ChatMessage(results));
            }
            assert chat != null;
            chat.setChatMessages(chatMessages);
            return chat;
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


}
