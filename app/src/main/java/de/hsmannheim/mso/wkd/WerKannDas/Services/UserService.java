package de.hsmannheim.mso.wkd.WerKannDas.Services;


import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class UserService {

    public static String table = "user";
    public static String colPk = "pk";
    public static String colMail = "mail";
    public static String colUserName = "username";
    public static String colPlz = "plz";
    public static String colPassword = "password";
    public static String colForename = "forename";
    public static String colSurname = "surname";
    public static String colVillage = "village";
    public static String colStreetWithNumber = "street_with_number";
    public static String colBirthday = "birthday";
    public static String colEnabled = "enabled";


    public static String schema = "CREATE TABLE " + table + " ( " +
            colPk + " INTEGER IDENTITY PRIMARY KEY, " +
            colMail + " VARCHAR(250) NOT NULL, " +
            colUserName + " VARCHAR(50) NOT NULL UNIQUE, " +
            colPlz + " VARCHAR(15) NOT NULL, " +
            colPassword + " VARCHAR(60) NOT NULL, " +
            colForename + " VARCHAR(50), " +
            colSurname + " VARCHAR(50), " +
            colVillage + " VARCHAR(250), " +
            colStreetWithNumber + " VARCHAR(250), " +
            colBirthday + " DATE, " +
            colEnabled + " BOOLEAN " +
            ");";

    public static String schemaAuthorities = "create table user_roles (" +
            "username varchar(255) not null," +
            "role varchar(255) not null, " +
            "constraint fk_authorities_users foreign key(username) references " + table + "(" + colUserName + ") );";



    public static String combinedCols = colPk + ", " + colMail + ", " + colUserName + ", " + colPlz + ", " +
            colPassword + ", " + colForename + ", " + colSurname + ", " + colVillage + ", " +
            colStreetWithNumber + ", " + colBirthday;

    public static String queryLogin = "SELECT " + colUserName + "," + colPassword + "," + colEnabled + " FROM " + table + " WHERE " + colUserName + " = ?";
    public static String queryByID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colPk + " = ?";
    public static String queryByUserName = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colUserName + " = ?";
    public static String queryAdd = "INSERT INTO " + table + "(" + colMail + ", " + colUserName +", " + colPassword +", " +
            colForename +  ", " + colSurname +  ", " + colBirthday +  ", " + colPlz +  ", " + colVillage +  ", " +
            colStreetWithNumber + ", " + colEnabled + ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Autowired
    private DataSource ds;

    @Autowired
    private PasswordEncoder bcryptPasswordEncoder;

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

    public User getByName(String name) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByUserName);
            pstmt.setString(1, name);
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

    public User save(User user) {
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryAdd, Statement.RETURN_GENERATED_KEYS);
            String mail = user.getMail();
            String userName = user.getUser_name();
            String password = user.getPassword();
            if(user.getPasswordClear() != "") {
                password = bcryptPasswordEncoder.encode(user.getPasswordClear());
            }
            String forename = user.getForename();
            String surname = user.getSurname();
            Date birthday = user.getBirthday();
            String plz = user.getPlz();
            String village = user.getVillage();
            String streetWithNumber = user.getStreet_with_number();
            pstmt.setString(1, mail);
            pstmt.setString(2, userName);
            pstmt.setString(3, password);
            pstmt.setString(4, forename);
            pstmt.setString(5, surname);
            pstmt.setDate(6, birthday);
            pstmt.setString(7, plz);
            pstmt.setString(8, village);
            pstmt.setString(9, streetWithNumber);
            pstmt.setBoolean(10, true);
            pstmt.executeUpdate();
            pstmt = ds.getConnection().prepareStatement("insert into user_roles values(?,?)");
            pstmt.setString(0, userName);
            pstmt.setString(1, "USER");
            pstmt.execute();
            ResultSet results = pstmt.getGeneratedKeys();
            if(results.next()) {
                int id = results.getInt(colPk);
                return getByID(id);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
