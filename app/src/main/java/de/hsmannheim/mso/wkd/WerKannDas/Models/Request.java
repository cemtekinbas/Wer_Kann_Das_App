package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.RequestService;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Request {
    private int pk;
    private int fromUserFk;
    private String title;
    private String message;
    private boolean isPremium;
    private Date createDate;
    private RequestState state;

    public Request(int pk, int fromUserFk, String title, String message, boolean isPremium, Date createDate, RequestState state) {
        this.pk = pk;
        this.fromUserFk = fromUserFk;
        this.title = title;
        this.message = message;
        this.isPremium = isPremium;
        this.createDate = createDate;
        this.state = state;
    }

    public Request (ResultSet results) throws SQLException {
        this.pk = results.getInt(RequestService.colPk);
        this.fromUserFk = results.getInt(RequestService.colFromUserFk);
        this.title = results.getString(RequestService.colTitle);
        this.message = results.getString(RequestService.colMessage);
        this.isPremium = results.getBoolean(RequestService.colIsPremium);
        this.createDate = results.getDate(RequestService.colCreateDate);
        this.state = RequestState.getById(results.getInt(RequestService.colState));
    }

    public int getPk() {
        return pk;
    }

    public int getFromUserFk() {
        return fromUserFk;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public RequestState getState() {
        return state;
    }
}