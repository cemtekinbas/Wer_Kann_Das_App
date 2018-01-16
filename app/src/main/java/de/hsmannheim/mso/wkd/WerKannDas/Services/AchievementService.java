package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Achievement;
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
public class AchievementService {

    public static String table = "achievement";
    public static String colPk = "pk";
    public static String colName = "name";
    public static String colDescription = "description";
    public static String colIconPath = "icon_path";
    public static String colUnlockCondition = "unlock_condition";


    public static String schema = "CREATE TABLE IF NOT EXISTS " + table + " ( " +
            colPk + " INTEGER IDENTITY PRIMARY KEY, " +
            colName + " VARCHAR(250), " +
            colDescription + " VARCHAR(250), " +
            colIconPath + " VARCHAR(250), " +
            colUnlockCondition + " VARCHAR(500) " +
            ");";

    private String combinedCols = colPk + ", " + colName + ", " + colDescription + ", " + colIconPath + ", " + colUnlockCondition;

    private String queryByID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colPk + " = ?";
    private String queryAdd = "INSERT INTO " + table + " (" +
            colName + ", " + colDescription + ", " +
            colIconPath + ", " + colUnlockCondition + ") VALUES (?,?,?,?)";
    private String queryAll = "SELECT " + combinedCols + " FROM " + table;

    @Autowired
    private DataSource ds;


    public Achievement getByID(int userPk) {        //Prepared Statements in allen Service Klassen
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByID);
            pstmt.setInt(1, userPk);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                Achievement achievement = new Achievement(results);
                return achievement;
                //PersonalData personalData = personalDataService.getByID(personalDataID);
                //Account account = new Account(id, personalData);
                //account.setBalance(balance);
                //List<Transaction> transactions = transactionService.getByAccount(account, null);
                //account.getTransactions().addAll(transactions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Achievement> getAll() {        //Prepared Statements in allen Service Klassen
        List<Achievement> list = new ArrayList<Achievement>();
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryAll);
            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                Achievement achievement = new Achievement(results);
                list.add(achievement);
                //PersonalData personalData = personalDataService.getByID(personalDataID);
                //Account account = new Account(id, personalData);
                //account.setBalance(balance);
                //List<Transaction> transactions = transactionService.getByAccount(account, null);
                //account.getTransactions().addAll(transactions);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Achievement update(Achievement achievement) {
        return null;
    }

    public Achievement save(Achievement achievement) {
        PreparedStatement pstmt = null;
        try {
            pstmt = ds.getConnection().prepareStatement(queryAdd, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, achievement.getName());
            pstmt.setString(2, achievement.getDescription());
            pstmt.setString(3, achievement.getIcon_path());
            pstmt.setString(4, achievement.getUnlock_condition());
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
