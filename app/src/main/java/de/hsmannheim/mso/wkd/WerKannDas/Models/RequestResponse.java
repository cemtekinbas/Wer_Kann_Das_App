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
    private boolean success;

    public int getRequestFk() {
        return requestFk;
    }

    public int getUserFk() {
        return userFk;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public RequestResponse(int requestFk, int userFk, Date responseDate, boolean can, boolean success) {
        this.requestFk = requestFk;
        this.userFk = userFk;
        this.responseDate = responseDate;
        this.can = can;
        this.success = success;
    }

    public RequestResponse(ResultSet results) throws SQLException {
        this.requestFk = results.getInt(RequestResponseService.colRequestFk);
        this.userFk = results.getInt(RequestResponseService.colUserFk);
        this.responseDate = results.getDate(RequestResponseService.colResponseDate);
        this.can = results.getBoolean(RequestResponseService.colResponseCan);
        this.success = results.getBoolean(RequestResponseService.colResponseSuccess);
    }

    public boolean isCan() {
        return can;
    }

    public boolean isSuccess() {
        return success;
    }
}
