package de.hsmannheim.mso.wkd.WerKannDas.Services;

import de.hsmannheim.mso.wkd.WerKannDas.Models.Achievement;
import de.hsmannheim.mso.wkd.WerKannDas.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAchievementMapperService {
    public static String table = "user_has_achievement";
    public static String colUserFk = "user_fk";
    public static String colAchievementFk = "achievement_fk";

    public static String schema = "CREATE TABLE IF NOT EXISTS " + table + " (" +
            colUserFk + " INTEGER NOT NULL, " +
            colAchievementFk + " INTEGER NOT NULL, " +
            "  PRIMARY KEY (" + colUserFk + ", " + colAchievementFk + ")," +
            "  CONSTRAINT user_has_achievment_user_fk FOREIGN KEY (" + colUserFk + ") REFERENCES " +
            UserService.table + " (" + UserService.colPk + ")" +
            " ON DELETE CASCADE" +
            " ON UPDATE CASCADE," +
            " CONSTRAINT user_has_achievment_achievment_fk FOREIGN KEY (" + colAchievementFk + ") REFERENCES " +
            AchievementService.table + " (" + AchievementService.colPk + ")" +
            " ON DELETE CASCADE" +
            " ON UPDATE CASCADE" +
            " );";

    private String combinedCols = colUserFk + ", " + colAchievementFk;

    private String queryByUserID = "SELECT " + combinedCols + " FROM " + table + " WHERE " + colUserFk + " = ?";

    @Autowired
    private DataSource ds;
    @Autowired
    private AchievementService as;


    public List<Achievement> getByID(User user) {        //Prepared Statements in allen Service Klassen
        List<Achievement> list = new ArrayList<Achievement>();
        try {
            PreparedStatement pstmt = ds.getConnection().prepareStatement(queryByUserID);
            pstmt.setInt(1, user.getPk());
            ResultSet results = pstmt.executeQuery();
            while (results.next()) {
                Achievement achievement = as.getByID(results.getInt(colUserFk));
                list.add(achievement);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public boolean save(User user, Achievement achievement){
        return true;
    }

}
