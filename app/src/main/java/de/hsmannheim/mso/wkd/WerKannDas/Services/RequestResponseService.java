package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Chat;
import de.hsmannheim.mso.wkd.WerKannDas.Models.RequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class RequestResponseService {


    public static String table = "";
    public static String colPk = "pk";

    public static String schema = "";
            /*
            "CREATE TABLE " + table + " ( " +
            colPk + " INT(11)  NOT NULL AUTO_INCREMENT, " +
            colMessage + " VARCHAR(250) NOT NULL, " +
            colFromUserFk + " INT(11), " +
            colToUserFk + " INT(11), " +
            colSentDate + " DATETIME NOT NULL DEFAULT now(), " +
            colReadDate + " DATETIME DEFAULT NULL, " +
            "PRIMARY KEY (" + colPk + "), " +
            "CONSTRAINT chat_from_user_fk FOREIGN KEY (" + colFromUserFk + ") REFERENCES " +
            UserService.table + " (" + UserService.colPk + ")" +
            "ON DELETE CASCADE " +
            "ON UPDATE CASCADE, " +
            "CONSTRAINT chat_to_user_fk FOREIGN KEY (" + colToUserFk + ") REFERENCES " +
            UserService.table + " (" + UserService.colPk + ")" +
            "ON DELETE CASCADE " +
            "ON UPDATE CASCADE " +
            ");";*/

    public static String combinedCols = colPk + ", ";
    private String queryByID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colPk + " = ?";

    @Autowired
    private DataSource ds;

    public RequestResponse getByID(int pk) {        //Prepared Statements in allen Service Klassen
        /*
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByID);
            pstmt.setInt(1, pk);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                RequestResponse requestResponse = new RequestResponse(results);
                return requestResponse;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        return null;

    }

    public RequestResponse update(RequestResponse rr){
        return null;
    }

    public RequestResponse save(RequestResponse rr){
        return null;
    }

}
