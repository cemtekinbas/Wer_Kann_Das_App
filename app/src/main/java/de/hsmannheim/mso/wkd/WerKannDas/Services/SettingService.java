package de.hsmannheim.mso.wkd.WerKannDas.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class SettingService {

    public static String table = "setting";
    public static String colPk = "pk";
    public static String colName = "name";

    public static String schema = "CREATE TABLE IF NOT EXISTS " + table + " (" +
            colPk + " INTEGER IDENTITY PRIMARY KEY, " +
            colName + " VARCHAR(250) " +
            ");";

    private String combinedCols = colPk + ", " + colName;

    @Autowired
    private DataSource ds;
}
