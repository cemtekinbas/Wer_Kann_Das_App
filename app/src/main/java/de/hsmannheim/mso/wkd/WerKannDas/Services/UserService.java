package de.hsmannheim.mso.wkd.WerKannDas.Services;


import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserService {

    public static String table = "user";
    public static String colPk = "pk";
    public static String colMail = "mail";
    public static String colUserName = "user_name";
    public static String colPlz = "plz";
    public static String colPassword = "password";
    public static String colForename = "forename";
    public static String colSurname = "surname";
    public static String colVillage = "village";
    public static String colStreetWithNumber = "street_with_number";
    public static String colBirthday = "birthday";


    public static String schema = "CREATE TABLE user ( " +
            colPk + " INT(11)      NOT NULL AUTO_INCREMENT, " +
            colMail + " VARCHAR(250) NOT NULL, " +
            colUserName + " VARCHAR(50)  NOT NULL, " +
            colPlz + " VARCHAR(15)  NOT NULL, " +
            colPassword + " VARCHAR(50)  NOT NULL, " +
            colForename + " VARCHAR(50), " +
            colSurname + " VARCHAR(50), " +
            colVillage + " VARCHAR(250), " +
            colStreetWithNumber + " VARCHAR(250), " +
            colBirthday + " DATE, " +
            "  PRIMARY KEY (" + colPk + ") " +
            ");";


    private String combinedCols = colPk + ", " + colMail + ", " + colUserName + ", " + colPlz + ", " +
            colPassword + ", " + colForename + ", " + colSurname + ", " + colVillage + ", " +
            colStreetWithNumber + ", " + colBirthday;
    private String queryByID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colPk + " = ?";

    @Autowired
    private DataSource ds;

    public User getByID(int userPk) {        //Prepared Statements in allen Service Klassen
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByID);
            pstmt.setInt(1, userPk);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                User user = new User(results);
                return user;
                //PersonalData personalData = personalDataService.getByID(personalDataID);
                //Account account = new Account(id, personalData);
                //account.setBalance(balance);
                //List<Transaction> transactions = transactionService.getByAccount(account, null);
                //account.getTransactions().addAll(transactions);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public User update(User user){
        return null;
    }

    public User save(User user){
        return null;
    }

}
