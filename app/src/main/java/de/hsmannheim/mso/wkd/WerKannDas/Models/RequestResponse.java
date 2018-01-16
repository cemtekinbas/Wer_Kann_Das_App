package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.RequestResponseService;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestResponse {
    private int requestFk;
    private int userFk;
    private Date responseDate;
    private boolean can;

    public int getRequestFk() {
        return requestFk;
    }

    public int getUserFk() {
        return userFk;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public RequestResponse(int requestFk, int userFk, Date responseDate, boolean can) {
        this.requestFk = requestFk;
        this.userFk = userFk;
        this.responseDate = responseDate;
        this.can = can;
    }

    public RequestResponse(ResultSet results) throws SQLException {
        this.requestFk = results.getInt(RequestResponseService.colRequestFk);
        this.userFk = results.getInt(RequestResponseService.colUserFk);
        this.responseDate = results.getDate(RequestResponseService.colResponseDate);
        this.can = results.getBoolean(RequestResponseService.colResponseCan);
    }

    public boolean isCan() {
        return can;
    }
}
