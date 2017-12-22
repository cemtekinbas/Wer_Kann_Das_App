package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Request;
import de.hsmannheim.mso.wkd.WerKannDas.Models.RequestResponse;
import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class RequestResponseService {


    public static String table = "request_response";
    public static String colRequestFk = "request_fk";
    public static String colUserFk = "user_fk";
    public static String colResponseDate = "response_date";

    public static String schema = "CREATE TABLE IF NOT EXISTS " + table + " ( " +
            colRequestFk + " INTEGER NOT NULL, " +
            colUserFk + " INTEGER NOT NULL, " +
            colResponseDate + " TIMESTAMP DEFAULT NOW, " +
            " PRIMARY KEY (" + colRequestFk + ", " + colUserFk + ")," +
            " CONSTRAINT request_response_user_fk FOREIGN KEY (" + colUserFk + ") REFERENCES " +
            UserService.table + " (" + UserService.colPk + ")" +
            " ON DELETE CASCADE" +
            " ON UPDATE CASCADE," +
            " CONSTRAINT request_response_request_fk FOREIGN KEY (" + colRequestFk + ") REFERENCES " +
            RequestService.table + " (" + RequestService.colPk + ")" +
            " ON DELETE CASCADE" +
            " ON UPDATE CASCADE" +
            ");";

    public static String combinedCols = colRequestFk + ", " + colUserFk + ", " + colResponseDate;
    private String queryByUserAndRequest = "SELECT " + combinedCols + " FROM " + table + " WHERE " +
            colRequestFk + " = ? AND " + colUserFk + " = ?";
    private String queryAdd = "INSERT INTO " + table +
            "(" + colRequestFk + ", " + colUserFk + ", " + colResponseDate + ") VALUES (?,?,?)";

    @Autowired
    private DataSource ds;

    public RequestResponse update(RequestResponse rr) {
        return null;
    }

    public RequestResponse getByUserIDAndRequestId(int userPk, int requestPk) {        //Prepared Statements in allen Service Klassen
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByUserAndRequest);
            pstmt.setInt(1, userPk);
            pstmt.setInt(2, requestPk);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                return new RequestResponse(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public RequestResponse getByUserAndRequest(User user, Request request) {
        return getByUserIDAndRequestId(user.getPk(), request.getPk());
    }


    public RequestResponse save(RequestResponse requestResponse) {
        PreparedStatement pstmt;
        try {
            pstmt = ds.getConnection().prepareStatement(queryAdd, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, requestResponse.getRequestFk());
            pstmt.setInt(2, requestResponse.getUserFk());
            pstmt.setDate(3, requestResponse.getResponseDate());
            pstmt.executeQuery();
            ResultSet results = pstmt.getGeneratedKeys();
            if (results.next()) {
                return requestResponse;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
