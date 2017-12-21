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
    public static String colMessage = "message";
    public static String colSentDate = "sent_date";
    public static String colReadDate = "read_date";

    public static String schema = "CREATE TABLE IF NOT EXISTS " + table + " ( " +
            colPk + " INTEGER IDENTITY PRIMARY KEY, " +
            colFromUserFk + " INT NOT NULL, " +
            colToUserFk + " INT NOT NULL, " +
            colRequestFk + " INT, " +
            colMessage + " VARCHAR(250) NOT NULL, " +
            colSentDate + " TIMESTAMP DEFAULT NOW, " +
            colReadDate + " TIMESTAMP DEFAULT NULL, " +
            " CONSTRAINT chat_from_user_fk FOREIGN KEY (" + colFromUserFk + ") REFERENCES " +
            UserService.table + " (" + UserService.colPk + ")" +
            " ON DELETE CASCADE " +
            " ON UPDATE CASCADE, " +
            " CONSTRAINT chat_request_fk FOREIGN KEY (" + colRequestFk + ") REFERENCES " +
            RequestService.table + " (" + RequestService.colPk + ")" +
            " ON DELETE CASCADE " +
            " ON UPDATE CASCADE, " +
            " CONSTRAINT chat_to_user_fk FOREIGN KEY (" + colToUserFk + ") REFERENCES " +
            UserService.table + " (" + UserService.colPk + ")" +
            " ON DELETE CASCADE " +
            " ON UPDATE CASCADE " +
            ");";

    public static String combinedCols = colPk + ", " +
            colFromUserFk + ", " +
            colToUserFk + ", " +
            colMessage + ", " +
            colSentDate + ", " +
            colReadDate + ", " +
            colRequestFk;

    private String queryByID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colPk + " = ?";
    private String queryByUserID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colFromUserFk + " = ? OR " + colToUserFk + " = ?";
    private String queryByRequestID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colRequestFk + " = ? AND (" + colFromUserFk + " = ? OR " + colToUserFk + " = ?)";
    private String queryByOtherUserAndRequestID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colRequestFk + " = ? AND " + colFromUserFk + " = ? AND " + colToUserFk + " = ?";
    private String queryAdd = "INSERT INTO " + table + " (" + colFromUserFk + ", " + colToUserFk + ", " + colMessage +
            ", " + colRequestFk + ") VALUE (?, ?, ?, ?)";

    @Autowired
    private DataSource ds;

    public Chat getByID(int pk) {        //Prepared Statements in allen Service Klassen
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByID);
            pstmt.setInt(1, pk);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                Chat chat = new Chat(results);
                ChatMessage message = new ChatMessage(results);
                chat.addChatMessage(message);
                return chat;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Chat update(Chat chat) {
        return null;
    }

    public List<Chat> getByUser(User user) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByUserID);
            pstmt.setInt(1, user.getPk());
            pstmt.setInt(2, user.getPk());
            ResultSet results = pstmt.executeQuery();
            return parseChatListFromResult(results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Chat> getByRequestID(User user, String requestId) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByRequestID);
            int requestIdInt = Integer.parseInt(requestId);
            pstmt.setInt(1, requestIdInt);
            pstmt.setInt(2, user.getPk());
            pstmt.setInt(3, user.getPk());
            ResultSet results = pstmt.executeQuery();
            return parseChatListFromResult(results);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Chat> parseChatListFromResult(ResultSet results) throws SQLException {
        List<Chat> chats = new ArrayList<Chat>();
        while (results.next()) {
            Chat newChat = new Chat(results);
            ChatMessage message = new ChatMessage(results);
            boolean add = true;
            for (Chat chat : chats) {
                if (chat.equals(newChat)) {
                    chat.addChatMessage(message);
                    add = false;
                }
            }
            if (add) {
                newChat.addChatMessage(message);
                chats.add(newChat);
            }
        }
        return chats;
    }

    public Chat getByUsersAndRequestID(User to, User from, String requestId) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByOtherUserAndRequestID);
            int requestIdInt = Integer.parseInt(requestId);
            pstmt.setInt(1, requestIdInt);
            pstmt.setInt(2, from.getPk());
            pstmt.setInt(3, to.getPk());
            ResultSet results = pstmt.executeQuery();
            Chat chat = null;
            while (results.next()) {
                if (chat == null) {
                    chat = new Chat(results);
                } else if (!chat.equals(new Chat(results))) {
                    throw new SQLException("Only one chat allowed");
                }
                chat.addChatMessage(new ChatMessage(results));
            }
            return chat;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean addChatMessage(User to, User from, String requestId, String message) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryAdd, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, from.getPk());
            pstmt.setInt(2, to.getPk());
            pstmt.setString(3, message);
            pstmt.setInt(4, Integer.getInteger(requestId));
            pstmt.executeUpdate();
            ResultSet results = pstmt.getGeneratedKeys();
            if (results.next()) {
                int id = results.getInt(colPk);
                return true;
            }
        } catch (SQLException | NullPointerException | SecurityException e) {
            e.printStackTrace();
        }
        return false;
    }
}
