package de.hsmannheim.mso.wkd.WerKannDas.Models;

import de.hsmannheim.mso.wkd.WerKannDas.Services.UserService;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int pk;
    private String mail;
    private String user_name;
    private String plz;
    private String password;
    private String passwordClear;
    private String forename;
    private String surname;
    private String village;
    private String street_with_number;
    private Date birthday;

    public User(int pk, String mail, String user_name, String plz, String password, String passwordClear, String forename, String surname, String village, String street_with_number, Date birthday) {
        this.pk = pk;
        this.mail = mail;
        this.user_name = user_name;
        this.plz = plz;
        this.password = password;
        this.passwordClear = passwordClear;
        this.forename = forename;
        this.surname = surname;
        this.village = village;
        this.street_with_number = street_with_number;
        this.birthday = birthday;
    }

    public User(ResultSet results) throws SQLException {
        this.pk = results.getInt(UserService.colPk);
        this.mail = results.getString(UserService.colMail);
        this.user_name = results.getString(UserService.colUserName);
        this.plz = results.getString(UserService.colPlz);
        this.password = results.getString(UserService.colPassword);
        this.passwordClear = "";
        this.forename = results.getString(UserService.colForename);
        this.surname = results.getString(UserService.colSurname);
        this.village = results.getString(UserService.colVillage);
        this.street_with_number = results.getString(UserService.colStreetWithNumber);
        this.birthday = results.getDate(UserService.colBirthday);
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getStreet_with_number() {
        return street_with_number;
    }

    public void setStreet_with_number(String street_with_number) {
        this.street_with_number = street_with_number;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPasswordClear() {
        return passwordClear;
    }

    public void setPasswordClear(String passwordClear) {
        this.passwordClear = passwordClear;
    }
}
