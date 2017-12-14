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
public class ChatService {

    public static String table = "chat";
    public static String colPk = "pk";
    public static String colFromUserFk = "from_user_fk";
    public static String colToUserFk = "to_user_fk";
    public static String colRequestFk = "request_fk";

    public static String schema = "CREATE TABLE " + table + " ( " +
            colPk + " INT(11)  NOT NULL AUTO_INCREMENT, " +
            colFromUserFk + " INT(11), " +
            colToUserFk + " INT(11), " +
            colRequestFk + " INT(11), " +
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
            colFromUserFk + ", " +
            colToUserFk + ", " +
            colRequestFk;

    private String queryByID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colPk + " = ?";
    private String queryByUserID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colFromUserFk + " = ? OR " + colToUserFk + " = ?";
    private String queryByRequestID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colRequestFk + " = ? AND (" + colFromUserFk + " = ? OR " + colToUserFk + " = ?)";


    @Autowired
    private ChatMessageService cms;
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


    public List<Chat> getByUser(User user) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByUserID);
            pstmt.setInt(1, user.getPk());
            pstmt.setInt(2, user.getPk());
            ResultSet results = pstmt.executeQuery();
            List<Chat> chats = new ArrayList<Chat>();
            while (results.next()) {
                Chat chat = new Chat(results);
                chat.setChatMessages(cms.getByChatID(chat.getPk()));
                chats.add(chat);
            }
            return chats;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public Chat getByRequestID(User user, String requestId) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByRequestID);
            int requestIdInt = Integer.parseInt(requestId);
            pstmt.setInt(1, requestIdInt);
            pstmt.setInt(2, user.getPk());
            pstmt.setInt(3, user.getPk());
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                Chat chat = new Chat(results);
                chat.setChatMessages(cms.getByChatID(chat.getPk()));
                return chat;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
