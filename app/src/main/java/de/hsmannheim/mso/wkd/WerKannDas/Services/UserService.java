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
    public static String schemaUsers = "CREATE TABLE user (" +
            "  pk                 INT(11)      NOT NULL AUTO_INCREMENT," +
            "  mail               VARCHAR(250) NOT NULL," +
            "  user_name          VARCHAR(50)  NOT NULL," +
            "  plz                VARCHAR(15)  NOT NULL," +
            "  password           VARCHAR(50)  NOT NULL," +
            "  forename           VARCHAR(50)," +
            "  surename           VARCHAR(50)," +
            "  village            VARCHAR(250)," +
            "  street_with_number VARCHAR(250)," +
            "  birthdate          DATE," +
            "  PRIMARY KEY (pk)" +
            ");";
    private String queryByID = "SELECT pk, mail, user_name, plz, password, forename, surename, village, street_with_number, birthdate FROM user WHERE pk = ?";

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
}
