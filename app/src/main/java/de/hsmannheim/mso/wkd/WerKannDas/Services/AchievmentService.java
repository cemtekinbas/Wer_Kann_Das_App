package de.hsmannheim.mso.wkd.WerKannDas.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class AchievmentService {
    public static String schemaAchievments = "CREATE TABLE achievment ( " +
    "pk INT(11) NOT NULL AUTO_INCREMENT, "+
    "name VARCHAR(250), " +
    "description VARCHAR(250), " +
    "icon_path VARCHAR(250), " +
    "unlock_condition VARCHAR(500), " +
    "PRIMARY KEY(pk) );";

    //private String queryByID = "SELECT pk, mail, user_name, plz, password, forename, surename, village, street_with_number, birthdate FROM user WHERE pk = ?";

    @Autowired
    private DataSource ds;
}
