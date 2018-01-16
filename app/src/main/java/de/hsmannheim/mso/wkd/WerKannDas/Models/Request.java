package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.RequestService;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

public class Request {
    private int pk;
    private int fromUserFk;
    private String title;
    private String message;
    private boolean isPremium;
    private Date createDate;
    private RequestState state;
    private int unreadCount;

    public Request()
    {

    }

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

    public int getDaysSinceCreation() {
        Date now = Date.valueOf(LocalDate.now());

        return (int) ChronoUnit.DAYS.between(LocalDate.parse(createDate.toString()),LocalDate.parse(now.toString()));
    }

    public RequestState getState() {
        return state;
    }

    public int getUnreadCount() { return unreadCount; }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public void setFromUserFk(int fromUserFk) {
        this.fromUserFk = fromUserFk;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public void setUnreadCount(int unreadCount) { this.unreadCount = unreadCount; }
}
