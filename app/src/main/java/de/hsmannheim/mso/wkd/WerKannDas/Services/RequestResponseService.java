package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.RequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class RequestResponseService {


    public static String table = "request_response";
    public static String colRequestFk = "request_fk";
    public static String colUserFk = "user_fk";
    public static String colResponseDate = "response_date";

    public static String schema = "CREATE TABLE " + table + " ( " +
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

    public static String combinedCols = colRequestFk + ", " + colUserFk+ ", " + colResponseDate;
    //private String queryByID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colPk + " = ?";

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

    public RequestResponse update(RequestResponse rr) {
        return null;
    }

    public RequestResponse save(RequestResponse rr) {
        return null;
    }

}
