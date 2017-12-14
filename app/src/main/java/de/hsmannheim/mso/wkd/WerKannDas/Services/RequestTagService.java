package de.hsmannheim.mso.wkd.WerKannDas.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class RequestTagService {

    public static String table = "request_tag";
    public static String colPk = "pk";
    public static String colTag = "tag";

    public static String schema = "CREATE TABLE " + table + " (" +
            colPk + " INT(11)     NOT NULL AUTO_INCREMENT, " +
            colTag + " VARCHAR(50) NOT NULL, " +
            "PRIMARY KEY (" + colPk + ")" +
            ");";

    private String combinedCols = colPk + ", " + colTag;

    @Autowired
    private DataSource ds;

}
