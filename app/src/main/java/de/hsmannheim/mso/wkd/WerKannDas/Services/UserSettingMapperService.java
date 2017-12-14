package de.hsmannheim.mso.wkd.WerKannDas.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class UserSettingMapperService {

    public static String table = "user_has_setting";
    public static String colUserFk = "user_fk";
    public static String colSettingFk = "setting_fk";
    public static String colSettingValue = "setting_value";

    public static String schema = "CREATE TABLE user_has_setting (" +
            colUserFk + " INT(11), " +
            colSettingFk + " INT(11), " +
            colSettingValue + " VARCHAR(250), " +
            "  PRIMARY KEY (" + colUserFk + ", " + colSettingFk + ")," +
            "  CONSTRAINT user_has_setting_user_fk FOREIGN KEY (" + colUserFk + ") REFERENCES " +
            UserService.table + " (" + UserService.colPk + ")" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE," +
            "  CONSTRAINT user_has_setting_setting_fk FOREIGN KEY (" + colSettingFk + ") REFERENCES " +
            SettingService.table + " (" + SettingService.colPk + ")" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE" +
            ");";

    private String combinedCols = colUserFk + ", " + colSettingFk + ", " + colSettingValue;

    @Autowired
    private DataSource ds;
}
