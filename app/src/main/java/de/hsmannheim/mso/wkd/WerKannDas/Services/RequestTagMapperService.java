package de.hsmannheim.mso.wkd.WerKannDas.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class RequestTagMapperService {

    public static String table = "request_has_tag";
    public static String colRequestFk = "request_fk";
    public static String colTagFk = "tag_fk";

    public static String schema = "CREATE TABLE " + table + " (" +
            colRequestFk + " INT NOT NULL, " +
            colTagFk + " INT NOT NULL, " +
            "  PRIMARY KEY (" + colRequestFk + ", " + colTagFk + ")," +
            "  CONSTRAINT request_has_tag_request_fk FOREIGN KEY (" + colRequestFk + ") REFERENCES " +
            RequestService.table + " (" + RequestService.colPk + ")" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE," +
            "  CONSTRAINT request_has_tag_tag_fk FOREIGN KEY (" + colTagFk + ") REFERENCES " +
            RequestTagService.table + " (" + RequestTagService.colPk + ")" +
            "    ON DELETE CASCADE" +
            "    ON UPDATE CASCADE" +
            ");";

    private String combinedCols = colRequestFk + ", " + colTagFk;

    @Autowired
    private DataSource ds;
}
