package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Chat;
import de.hsmannheim.mso.wkd.WerKannDas.Models.ChatMessage;
import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
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
    private String queryByRequestID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colRequestFk + " = ?";
    private String queryByOtherUserAndRequestID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colRequestFk + " = ? AND (" + colFromUserFk + " = ? OR " + colToUserFk + " = ?)";
    private String queryAdd = "INSERT INTO " + table + " (" + colFromUserFk + ", " + colToUserFk + ", " + colMessage +
            ", " + colRequestFk + ") VALUES (?, ?, ?, ?)";
    private String queryUpdate = "UPDATE " + table +
            " SET (" + colFromUserFk + ", " + colToUserFk + ", " + colMessage + ", " + colSentDate + ", " +
            colReadDate + ") = (?,?,?,?,?) " +
            "WHERE " + colPk + " = ?";
    private String queryUnread = "SELECT COUNT(*) FROM " + table + " WHERE " + colReadDate + " IS NULL AND "
            + colToUserFk + " = ? AND " + colRequestFk + " = ?";
    private String queryUnreadChat = "SELECT COUNT(*) FROM " + table + " WHERE " + colReadDate + " IS NULL AND "
            + colToUserFk + " = ? AND " + colFromUserFk + " = ? AND " + colRequestFk + " = ?";

    @Autowired
    private DataSource ds;

    public int getUnreadCount(int userId, int requestId)
    {
        try
        {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryUnread);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, requestId);
            ResultSet results = pstmt.executeQuery();
            if(results.next()) {
                int ret = results.getInt(1);
                return ret;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public int getUnreadCount(int toUserId, int fromUserId, int requestId)
    {
        try
        {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryUnreadChat);
            pstmt.setInt(1, toUserId);
            pstmt.setInt(2, fromUserId);
            pstmt.setInt(3, requestId);
            ResultSet results = pstmt.executeQuery();
            if(results.next()) {
                int ret = results.getInt(1);
                return ret;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

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

    public Chat update(ChatMessage chatMessage) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryUpdate, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, chatMessage.getFrom_user_fk());
            pstmt.setInt(2, chatMessage.getTo_user_fk());
            pstmt.setString(3, chatMessage.getMessage());
            pstmt.setDate(4, chatMessage.getSent_date());
            pstmt.setDate(5, chatMessage.getRead_date());
            pstmt.setInt(6, chatMessage.getPk());
            pstmt.executeUpdate();
            ResultSet results = pstmt.getGeneratedKeys();
            if (results.next()) {
                int id = results.getInt(colPk);
                return getByID(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public List<Chat> getByRequestID(int requestId) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByRequestID);
            pstmt.setInt(1, requestId);
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

    public Chat getByUsersAndRequestID(User to, int requestId) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByOtherUserAndRequestID);
            int requestIdInt = requestId;
            pstmt.setInt(1, requestIdInt);
            pstmt.setInt(2, to.getPk());
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


    public boolean addChatMessage(User from, User to, int requestId, String message) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryAdd, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, from.getPk());
            pstmt.setInt(2, to.getPk());
            pstmt.setString(3, message);
            pstmt.setInt(4, requestId);
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
