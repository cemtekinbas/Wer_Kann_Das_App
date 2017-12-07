package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.RequestResponseService;
import de.hsmannheim.mso.wkd.WerKannDas.Services.RequestTagService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestTag {
    private int pk;
    private String tag;

    public RequestTag(int pk, String tag) {
        this.pk = pk;
        this.tag = tag;
    }

    public RequestTag(ResultSet results) throws SQLException {
        this.pk = results.getInt(RequestTagService.colPk);
        this.tag = results.getString(RequestTagService.colTag);
    }

    public int getPk() {
        return pk;
    }

    public String getTag() {
        return tag;
    }
}
