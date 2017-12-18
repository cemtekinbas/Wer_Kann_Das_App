package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Achievement;
import de.hsmannheim.mso.wkd.WerKannDas.Models.Request;
import de.hsmannheim.mso.wkd.WerKannDas.Models.RequestState;
import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {
    public static String table = "request";
    public static String colPk = "pk";
    public static String colFromUserFk = "from_user_fk";
    public static String colTitle = "title";
    public static String colMessage = "message";
    public static String colIsPremium = "is_premium";
    public static String colCreateDate = "create_date";
    public static String colState = "state";


    public static String schema = "CREATE TABLE " + table + " (" +
            colPk + " INT      NOT NULL AUTO_INCREMENT," +
            colFromUserFk + " INT," +
            colTitle + " VARCHAR(250) NOT NULL, -- limit size!?" +
            colMessage + " VARCHAR(250) NOT NULL, -- limit size!?" +
            colIsPremium + " BIT(1), -- validate datatype bit, replace with TINYINT(1) if invalid" +
            colCreateDate + " DATETIME     NOT NULL DEFAULT now(), -- validate now(), remove if invalid" +
            colState + " INT      NOT NULL DEFAULT 1," +
            "  PRIMARY KEY (" + colPk + ")," +
            "  CONSTRAINT request_from_user_fk FOREIGN KEY (" + colFromUserFk + ") REFERENCES "
            + UserService.table + " (" + UserService.colPk + ")" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE" +
            ");";

    private String combinedCols = colPk + ", " + colFromUserFk + ", " + colTitle + ", " + colMessage + ", " +
            colIsPremium + ", " + colCreateDate + ", " + colState;

    private String queryByID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colPk + " = ?";

    @Autowired
    private DataSource ds;


    public Request getByID(int pk) {        //Prepared Statements in allen Service Klassen
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByID);
            pstmt.setInt(1, pk);
            ResultSet results = pstmt.executeQuery();
            if (results.next()) {
                Request request = new Request(results);
                return request;
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

    public Request save(Request request) { return null; }

    public List<Request> getByUser(User user) { return null; }

    public List<Request> getList() {
        List<Request> requestList = new ArrayList<Request>(3);
        Request r = new Request(0, 0, "Titel1", "Message1", false, Date.valueOf(LocalDate.now()), RequestState.OPEN);
        requestList.add(r);
        r = new Request(1, 1, "Titel2", "Message2", true, Date.valueOf(LocalDate.now()), RequestState.OPEN);
        requestList.add(r);
        r = new Request(2, 1, "Titel3", "Message3", false, Date.valueOf(LocalDate.now()), RequestState.OPEN);
        requestList.add(r);
        return requestList;
    }

    public List<Request> getList(String search) {
        return null;
    }
}
