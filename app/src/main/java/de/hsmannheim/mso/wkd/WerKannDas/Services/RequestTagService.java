package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.RequestTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Service
public class RequestTagService {

    public static String table = "request_tag";
    public static String colPk = "pk";
    public static String colTag = "tag";

    public static String schema = "CREATE TABLE IF NOT EXISTS " + table + " (" +
            colPk + " INTEGER IDENTITY PRIMARY KEY, " +
            colTag + " VARCHAR(50) NOT NULL " +
            ");";

    private String combinedCols = colPk + ", " + colTag;

    private String queryByID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colPk + " = ?";
    private String queryAdd = "INSERT INTO " + table + " (" + colTag + ") VALUES (?)";

    @Autowired
    private DataSource ds;


    public RequestTag getByID(int pk) {        //Prepared Statements in allen Service Klassen
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByID);
            pstmt.setInt(1, pk);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                return new RequestTag(results);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public RequestTag save(RequestTag requestTag) {
        PreparedStatement pstmt;
        try {
            pstmt = ds.getConnection().prepareStatement(queryAdd, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, requestTag.getTag());
            pstmt.executeQuery();
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
}
